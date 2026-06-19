package com.beyondthepage.service;

import com.beyondthepage.dto.request.ChapterRequest;
import com.beyondthepage.dto.request.CreateBookRequest;
import com.beyondthepage.dto.request.UpdateProgressRequest;
import com.beyondthepage.dto.response.BookCreatedResponse;
import com.beyondthepage.dto.response.BookDetailResponse;
import com.beyondthepage.dto.response.BookSummaryResponse;
import com.beyondthepage.dto.response.ChapterDetailResponse;
import com.beyondthepage.entity.Book;
import com.beyondthepage.entity.BookChapter;
import com.beyondthepage.entity.ReadingProgress;
import com.beyondthepage.enums.BookStatus;
import com.beyondthepage.enums.ChapterStatus;
import com.beyondthepage.exception.BookAlreadyExistsException;
import com.beyondthepage.exception.BookNotFoundException;
import com.beyondthepage.exception.InvalidChapterDataException;
import com.beyondthepage.repository.BookRepository;
import com.beyondthepage.repository.ReadingProgressRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookService {

	private static final Set<String> ALLOWED_IMAGE_TYPES = Set.of("image/jpeg", "image/png", "image/webp");
	private static final long MAX_COVER_BYTES = 5 * 1024 * 1024L;

	private final BookRepository bookRepository;
	private final ReadingProgressRepository readingProgressRepository;
	private final ProgressCalculationService progressCalculationService;

	public BookService(BookRepository bookRepository,
			ReadingProgressRepository readingProgressRepository,
			ProgressCalculationService progressCalculationService) {
		this.bookRepository = bookRepository;
		this.readingProgressRepository = readingProgressRepository;
		this.progressCalculationService = progressCalculationService;
	}

	@Transactional
	public BookCreatedResponse createBook(CreateBookRequest request) {
		if (bookRepository.existsByBookName(request.getBookName())) {
			throw new BookAlreadyExistsException(request.getBookName());
		}
		validateChapters(request.getChapters(), request.getTotalPages());

		Book book = new Book(request.getBookName(), request.getTotalPages(),
				request.getPlannedDays(), request.getStartDate());
		book.setCategory(request.getCategory());

		List<ChapterRequest> sortedChapters = request.getChapters()
				.stream()
				.sorted(Comparator.comparingInt(ChapterRequest::getChapterNumber))
				.toList();

		for (ChapterRequest chapterRequest : sortedChapters) {
			BookChapter chapter = new BookChapter(book, chapterRequest.getChapterNumber(),
					chapterRequest.getChapterTitle(), chapterRequest.getStartPage(),
					chapterRequest.getEndPage());
			book.getChapters().add(chapter);
		}

		Book saved = bookRepository.save(book);
		readingProgressRepository.save(new ReadingProgress(saved));

		return BookCreatedResponse.builder()
				.bookName(saved.getBookName())
				.category(saved.getCategory())
				.totalPages(saved.getTotalPages())
				.plannedDays(saved.getPlannedDays())
				.startDate(saved.getStartDate())
				.completedPages(0)
				.hasCoverImage(false)
				.build();
	}

	@Transactional(readOnly = true)
	public List<BookSummaryResponse> getAllBooks(String query) {
		LocalDate today = LocalDate.now();
		List<Book> books = (query == null || query.isBlank())
				? bookRepository.findAllWithProgress()
				: bookRepository.findAllWithProgressByNameContaining(query);
		return books.stream()
				.map(book -> buildBookSummaryResponse(book, today))
				.toList();
	}

	@Transactional(readOnly = true)
	public BookDetailResponse getBookDetail(String bookName) {
		Book book = bookRepository.findByBookNameWithDetails(bookName)
				.orElseThrow(() -> new BookNotFoundException(bookName));

		LocalDate today = LocalDate.now();
		int completedPages = book.getReadingProgress().getCompletedPages();
		LocalDate targetEndDate = progressCalculationService.computeTargetEndDate(
				book.getStartDate(), book.getPlannedDays());
		long daysElapsed = progressCalculationService.computeDaysElapsed(book.getStartDate(), today);
		long daysRemaining = progressCalculationService.computeDaysRemaining(targetEndDate, today);
		double expectedPagesByToday = progressCalculationService.computeExpectedPagesByDate(book, today);
		double plannedDailyPages = progressCalculationService.computePlannedDailyPages(
				book.getTotalPages(), book.getPlannedDays());
		int remainingPages = book.getTotalPages() - completedPages;
		double completionPercentage = (completedPages / (double) book.getTotalPages()) * 100;
		double variancePages = completedPages - expectedPagesByToday;
		BookStatus status = progressCalculationService.resolveBookStatus(completedPages,
				book.getTotalPages(), expectedPagesByToday, targetEndDate, today);

		List<ChapterDetailResponse> chapterResponses = book.getChapters()
				.stream()
				.map(chapter -> {
					ChapterStatus chapterStatus = progressCalculationService.resolveChapterStatus(
							completedPages, chapter, expectedPagesByToday);
					return ChapterDetailResponse.builder()
							.chapterNumber(chapter.getChapterId().getChapterNumber())
							.chapterTitle(chapter.getChapterTitle())
							.startPage(chapter.getStartPage())
							.endPage(chapter.getEndPage())
							.status(chapterStatus)
							.build();
				})
				.toList();

		int completedChapters = (int) chapterResponses.stream()
				.filter(c -> c.getStatus() == ChapterStatus.COMPLETED)
				.count();
		int overdueChapters = (int) chapterResponses.stream()
				.filter(c -> c.getStatus() == ChapterStatus.OVERDUE)
				.count();
		int pendingChapters = chapterResponses.size() - completedChapters;

		return BookDetailResponse.builder()
				.bookName(book.getBookName())
				.category(book.getCategory())
				.totalPages(book.getTotalPages())
				.plannedDays(book.getPlannedDays())
				.startDate(book.getStartDate())
				.targetEndDate(targetEndDate)
				.completedPages(completedPages)
				.remainingPages(remainingPages)
				.completionPercentage(completionPercentage)
				.plannedDailyPages(plannedDailyPages)
				.expectedPagesByToday(expectedPagesByToday)
				.variancePages(variancePages)
				.currentStatus(status)
				.daysElapsed(daysElapsed)
				.daysRemaining(daysRemaining)
				.completedChapters(completedChapters)
				.pendingChapters(pendingChapters)
				.overdueChapters(overdueChapters)
				.chapters(chapterResponses)
				.hasCoverImage(book.getCoverImage() != null)
				.build();
	}

	@Transactional
	public void updateProgress(String bookName, UpdateProgressRequest request) {
		Book book = bookRepository.findById(bookName)
				.orElseThrow(() -> new BookNotFoundException(bookName));

		if (request.getCompletedPages() > book.getTotalPages()) {
			throw new InvalidChapterDataException(
					"completedPages (" + request.getCompletedPages()
					+ ") cannot exceed totalPages (" + book.getTotalPages() + ")");
		}

		ReadingProgress readingProgress = readingProgressRepository.findById(bookName)
				.orElseThrow(() -> new BookNotFoundException(bookName));
		readingProgress.setCompletedPages(request.getCompletedPages());
		readingProgressRepository.save(readingProgress);
	}

	BookSummaryResponse buildBookSummaryResponse(Book book, LocalDate today) {
		int completedPages = book.getReadingProgress().getCompletedPages();
		LocalDate targetEndDate = progressCalculationService.computeTargetEndDate(
				book.getStartDate(), book.getPlannedDays());
		long daysElapsed = progressCalculationService.computeDaysElapsed(book.getStartDate(), today);
		long daysRemaining = progressCalculationService.computeDaysRemaining(targetEndDate, today);
		double plannedDailyPages = progressCalculationService.computePlannedDailyPages(
				book.getTotalPages(), book.getPlannedDays());
		double expectedPagesByToday = progressCalculationService.computeExpectedPagesByDate(book, today);
		int remainingPages = book.getTotalPages() - completedPages;
		double completionPercentage = (completedPages / (double) book.getTotalPages()) * 100;
		BookStatus status = progressCalculationService.resolveBookStatus(completedPages,
				book.getTotalPages(), expectedPagesByToday, targetEndDate, today);

		return BookSummaryResponse.builder()
				.bookName(book.getBookName())
				.category(book.getCategory())
				.totalPages(book.getTotalPages())
				.plannedDays(book.getPlannedDays())
				.startDate(book.getStartDate())
				.completedPages(completedPages)
				.remainingPages(remainingPages)
				.completionPercentage(completionPercentage)
				.plannedDailyPages(plannedDailyPages)
				.targetEndDate(targetEndDate)
				.currentStatus(status)
				.daysElapsed(daysElapsed)
				.daysRemaining(daysRemaining)
				.hasCoverImage(book.getCoverImage() != null)
				.build();
	}

	@Transactional
	public void uploadCover(String bookName, MultipartFile file) {
		if (file.isEmpty()) {
			throw new InvalidChapterDataException("Cover file must not be empty");
		}
		String contentType = file.getContentType();
		if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType)) {
			throw new InvalidChapterDataException("Unsupported type. Accepted: JPEG, PNG, WEBP");
		}
		if (file.getSize() > MAX_COVER_BYTES) {
			throw new InvalidChapterDataException("Cover image must be 5 MB or smaller");
		}
		Book book = bookRepository.findById(bookName)
				.orElseThrow(() -> new BookNotFoundException(bookName));
		try {
			book.setCoverImage(file.getBytes());
		} catch (IOException e) {
			throw new InvalidChapterDataException("Failed to read cover image");
		}
		book.setCoverImageType(contentType);
		bookRepository.save(book);
	}

	@Transactional(readOnly = true)
	public Book getBookWithCover(String bookName) {
		return bookRepository.findById(bookName)
				.orElseThrow(() -> new BookNotFoundException(bookName));
	}

	private void validateChapters(List<ChapterRequest> chapters, int totalPages) {
		List<ChapterRequest> sorted = chapters.stream()
				.sorted(Comparator.comparingInt(ChapterRequest::getChapterNumber))
				.toList();

		for (ChapterRequest chapter : sorted) {
			if (chapter.getStartPage() > chapter.getEndPage()) {
				throw new InvalidChapterDataException(
						"Chapter " + chapter.getChapterNumber() + ": startPage must be <= endPage");
			}
		}

		if (sorted.get(0).getStartPage() != 1) {
			throw new InvalidChapterDataException("First chapter must start at page 1");
		}
		if (sorted.get(sorted.size() - 1).getEndPage() != totalPages) {
			throw new InvalidChapterDataException("Last chapter must end at page " + totalPages);
		}

		for (int i = 0; i < sorted.size() - 1; i++) {
			ChapterRequest current = sorted.get(i);
			ChapterRequest next = sorted.get(i + 1);
			if (next.getStartPage() != current.getEndPage() + 1) {
				throw new InvalidChapterDataException(
						"Gap or overlap between chapters " + current.getChapterNumber()
						+ " and " + next.getChapterNumber());
			}
		}
	}
}
