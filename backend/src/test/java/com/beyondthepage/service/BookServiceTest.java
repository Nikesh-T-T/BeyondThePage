package com.beyondthepage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.beyondthepage.dto.request.ChapterRequest;
import com.beyondthepage.dto.request.CreateBookRequest;
import com.beyondthepage.dto.request.UpdateProgressRequest;
import com.beyondthepage.dto.response.BookCreatedResponse;
import com.beyondthepage.dto.response.BookDetailResponse;
import com.beyondthepage.dto.response.BookSummaryResponse;
import com.beyondthepage.entity.Book;
import com.beyondthepage.entity.BookChapter;
import com.beyondthepage.entity.BookChapterId;
import com.beyondthepage.entity.ReadingProgress;
import com.beyondthepage.enums.BookStatus;
import com.beyondthepage.enums.ChapterStatus;
import com.beyondthepage.exception.BookAlreadyExistsException;
import com.beyondthepage.exception.BookNotFoundException;
import com.beyondthepage.exception.InvalidChapterDataException;
import com.beyondthepage.repository.BookRepository;
import com.beyondthepage.repository.ReadingProgressRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private ReadingProgressRepository readingProgressRepository;

	@Mock
	private ProgressCalculationService progressCalculationService;

	private BookService bookService;

	private static final LocalDate START_DATE = LocalDate.of(2026, 7, 1);
	private static final LocalDate TARGET_END_DATE = LocalDate.of(2026, 8, 30);

	@BeforeEach
	void setUp() {
		bookService = new BookService(bookRepository, readingProgressRepository, progressCalculationService);
	}

	@Test
	void shouldCreateBookSuccessfully() {
		CreateBookRequest request = createBookRequestWithTwoChapters();
		Book savedBook = createBook(100, 30, START_DATE);
		savedBook.setCategory("Fiction");
		savedBook.setAuthor("Test Author");
		when(bookRepository.existsByBookName("Test Book")).thenReturn(false);
		when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
		when(readingProgressRepository.save(any(ReadingProgress.class))).thenReturn(new ReadingProgress(savedBook));

		BookCreatedResponse response = bookService.createBook(request);

		assertEquals("Test Book", response.getBookName());
		assertEquals(100, response.getTotalPages());
		assertEquals(30, response.getPlannedDays());
		assertEquals(START_DATE, response.getStartDate());
		assertEquals(0, response.getCompletedPages());
		assertEquals("Fiction", response.getCategory());
		assertEquals("Test Author", response.getAuthor());
		verify(bookRepository).save(any(Book.class));
		verify(readingProgressRepository).save(any(ReadingProgress.class));
	}

	@Test
	void shouldCreateBookWithCategorySuccessfully() {
		CreateBookRequest request = createBookRequestWithTwoChapters();
		setField(request, "category", "Technology");
		Book savedBook = createBook(100, 30, START_DATE);
		savedBook.setCategory("Technology");
		savedBook.setAuthor("Test Author");
		when(bookRepository.existsByBookName("Test Book")).thenReturn(false);
		when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
		when(readingProgressRepository.save(any(ReadingProgress.class))).thenReturn(new ReadingProgress(savedBook));

		BookCreatedResponse response = bookService.createBook(request);

		assertEquals("Technology", response.getCategory());
	}

	@Test
	void shouldCreateBookWithAuthorSuccessfully() {
		CreateBookRequest request = createBookRequestWithTwoChapters();
		setField(request, "author", "Robert C. Martin");
		Book savedBook = createBook(100, 30, START_DATE);
		savedBook.setCategory("Fiction");
		savedBook.setAuthor("Robert C. Martin");
		when(bookRepository.existsByBookName("Test Book")).thenReturn(false);
		when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
		when(readingProgressRepository.save(any(ReadingProgress.class))).thenReturn(new ReadingProgress(savedBook));

		BookCreatedResponse response = bookService.createBook(request);

		assertEquals("Robert C. Martin", response.getAuthor());
	}
	@Test
	void shouldThrowBookAlreadyExistsExceptionWhenBookNameIsDuplicate() {
		CreateBookRequest request = createBookRequestWithTwoChapters();
		when(bookRepository.existsByBookName("Test Book")).thenReturn(true);

		assertThrows(BookAlreadyExistsException.class, () -> bookService.createBook(request));

		verify(bookRepository, never()).save(any());
	}

	@Test
	void shouldThrowInvalidChapterDataExceptionWhenFirstChapterDoesNotStartAtPageOne() {
		CreateBookRequest request = createBookRequestWithChapters(List.of(
				createChapterRequest(1, "Chapter 1", 5, 50),
				createChapterRequest(2, "Chapter 2", 51, 100)
		));
		when(bookRepository.existsByBookName(anyString())).thenReturn(false);

		assertThrows(InvalidChapterDataException.class, () -> bookService.createBook(request));
	}

	@Test
	void shouldThrowInvalidChapterDataExceptionWhenLastChapterDoesNotEndAtTotalPages() {
		CreateBookRequest request = createBookRequestWithChapters(List.of(
				createChapterRequest(1, "Chapter 1", 1, 50),
				createChapterRequest(2, "Chapter 2", 51, 90)
		));
		when(bookRepository.existsByBookName(anyString())).thenReturn(false);

		assertThrows(InvalidChapterDataException.class, () -> bookService.createBook(request));
	}

	@Test
	void shouldThrowInvalidChapterDataExceptionWhenGapBetweenChapters() {
		CreateBookRequest request = createBookRequestWithChapters(List.of(
				createChapterRequest(1, "Chapter 1", 1, 40),
				createChapterRequest(2, "Chapter 2", 50, 100)
		));
		when(bookRepository.existsByBookName(anyString())).thenReturn(false);

		assertThrows(InvalidChapterDataException.class, () -> bookService.createBook(request));
	}

	@Test
	void shouldThrowInvalidChapterDataExceptionWhenChapterStartPageExceedsEndPage() {
		CreateBookRequest request = createBookRequestWithChapters(List.of(
				createChapterRequest(1, "Chapter 1", 1, 40),
				createChapterRequest(2, "Chapter 2", 100, 41)
		));
		when(bookRepository.existsByBookName(anyString())).thenReturn(false);

		assertThrows(InvalidChapterDataException.class, () -> bookService.createBook(request));
	}

	@Test
	void shouldReturnAllBooksWithDerivedFields() {
		Book book = createBookWithProgress(200, 20, START_DATE, 40);
		book.setCategory("Science");
		book.setAuthor("Test Author");
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		stubProgressCalculations(book, TARGET_END_DATE, 10L, 50L, 100.0, 10.0, BookStatus.ON_TRACK);

		List<BookSummaryResponse> responses = bookService.getAllBooks(null);

		assertEquals(1, responses.size());
		assertEquals("Test Book", responses.get(0).getBookName());
		assertEquals("Science", responses.get(0).getCategory());
		assertEquals("Test Author", responses.get(0).getAuthor());
		assertEquals(BookStatus.ON_TRACK, responses.get(0).getCurrentStatus());
	}

	@Test
	void shouldReturnBookDetailWithChapterStatuses() {
		Book book = createBookWithProgressAndChapters(200, 20, START_DATE, 50);
		book.setCategory("Science");
		book.setAuthor("Test Author");
		when(bookRepository.findByBookNameWithDetails("Test Book")).thenReturn(Optional.of(book));
		stubProgressCalculations(book, TARGET_END_DATE, 10L, 50L, 40.0, 10.0, BookStatus.ON_TRACK);

		BookDetailResponse response = bookService.getBookDetail("Test Book");

		assertEquals("Test Book", response.getBookName());
		assertEquals("Science", response.getCategory());
		assertEquals("Test Author", response.getAuthor());
		assertNotNull(response.getChapters());
		assertEquals(2, response.getChapters().size());
	}

	@Test
	void shouldSetChapterStatusesCorrectlyInBookDetail() {
		Book book = createBookWithProgressAndChapters(200, 20, START_DATE, 60);
		when(bookRepository.findByBookNameWithDetails("Test Book")).thenReturn(Optional.of(book));
		stubProgressCalculations(book, TARGET_END_DATE, 10L, 50L, 50.0, 10.0, BookStatus.ON_TRACK);
		when(progressCalculationService.resolveChapterStatus(60, book.getChapters().get(0), 50.0))
				.thenReturn(ChapterStatus.COMPLETED);
		when(progressCalculationService.resolveChapterStatus(60, book.getChapters().get(1), 50.0))
				.thenReturn(ChapterStatus.IN_PROGRESS);

		BookDetailResponse response = bookService.getBookDetail("Test Book");

		assertEquals(ChapterStatus.COMPLETED, response.getChapters().get(0).getStatus());
		assertEquals(ChapterStatus.IN_PROGRESS, response.getChapters().get(1).getStatus());
		assertEquals(1, response.getCompletedChapters());
		assertEquals(1, response.getPendingChapters());
	}

	@Test
	void shouldThrowBookNotFoundExceptionWhenBookDoesNotExistInGetDetail() {
		when(bookRepository.findByBookNameWithDetails("Unknown")).thenReturn(Optional.empty());

		assertThrows(BookNotFoundException.class, () -> bookService.getBookDetail("Unknown"));
	}

	@Test
	void shouldUpdateProgressSuccessfully() {
		Book book = createBook(200, 20, START_DATE);
		ReadingProgress readingProgress = new ReadingProgress(book);
		when(bookRepository.findById("Test Book")).thenReturn(Optional.of(book));
		when(readingProgressRepository.findById("Test Book")).thenReturn(Optional.of(readingProgress));

		UpdateProgressRequest request = createUpdateProgressRequest(150);

		bookService.updateProgress("Test Book", request);

		verify(readingProgressRepository).save(readingProgress);
		assertEquals(150, readingProgress.getCompletedPages());
	}

	@Test
	void shouldAllowProgressToBeReducedToLowerValue() {
		Book book = createBook(200, 20, START_DATE);
		ReadingProgress readingProgress = new ReadingProgress(book);
		readingProgress.setCompletedPages(150);
		when(bookRepository.findById("Test Book")).thenReturn(Optional.of(book));
		when(readingProgressRepository.findById("Test Book")).thenReturn(Optional.of(readingProgress));

		bookService.updateProgress("Test Book", createUpdateProgressRequest(50));

		assertEquals(50, readingProgress.getCompletedPages());
	}

	@Test
	void shouldAllowProgressToBeSetToZero() {
		Book book = createBook(200, 20, START_DATE);
		ReadingProgress readingProgress = new ReadingProgress(book);
		readingProgress.setCompletedPages(100);
		when(bookRepository.findById("Test Book")).thenReturn(Optional.of(book));
		when(readingProgressRepository.findById("Test Book")).thenReturn(Optional.of(readingProgress));

		bookService.updateProgress("Test Book", createUpdateProgressRequest(0));

		assertEquals(0, readingProgress.getCompletedPages());
	}

	@Test
	void shouldThrowInvalidChapterDataExceptionWhenCompletedPagesExceedsTotalPages() {
		Book book = createBook(200, 20, START_DATE);
		when(bookRepository.findById("Test Book")).thenReturn(Optional.of(book));

		UpdateProgressRequest request = createUpdateProgressRequest(999);

		assertThrows(InvalidChapterDataException.class, () -> bookService.updateProgress("Test Book", request));

		verify(readingProgressRepository, never()).save(any());
	}

	@Test
	void shouldThrowBookNotFoundExceptionWhenBookDoesNotExistInUpdateProgress() {
		when(bookRepository.findById("Unknown")).thenReturn(Optional.empty());

		assertThrows(BookNotFoundException.class,
				() -> bookService.updateProgress("Unknown", createUpdateProgressRequest(10)));
	}

	@ParameterizedTest
	@CsvSource({
		"0,   NOT_STARTED",
		"200, COMPLETED"
	})
	void shouldResolveCorrectBookStatusAfterProgressUpdate(int completedPages, BookStatus expectedStatus) {
		Book book = createBook(200, 20, START_DATE);
		ReadingProgress readingProgress = new ReadingProgress(book);
		readingProgress.setCompletedPages(completedPages);
		book.setReadingProgress(readingProgress);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		stubProgressCalculations(book, TARGET_END_DATE, 10L, 50L, 100.0, 10.0, expectedStatus);

		List<BookSummaryResponse> responses = bookService.getAllBooks(null);

		assertEquals(expectedStatus, responses.get(0).getCurrentStatus());
	}

	private Book createBook(int totalPages, int plannedDays, LocalDate startDate) {
		return new Book("Test Book", totalPages, plannedDays, startDate);
	}

	private Book createBookWithProgress(int totalPages, int plannedDays, LocalDate startDate, int completedPages) {
		Book book = new Book("Test Book", totalPages, plannedDays, startDate);
		ReadingProgress progress = new ReadingProgress(book);
		progress.setCompletedPages(completedPages);
		book.setReadingProgress(progress);
		return book;
	}

	private Book createBookWithProgressAndChapters(int totalPages, int plannedDays, LocalDate startDate,
			int completedPages) {
		Book book = new Book("Test Book", totalPages, plannedDays, startDate);

		BookChapter chapter1 = new BookChapter();
		chapter1.setChapterId(new BookChapterId("Test Book", 1));
		chapter1.setBook(book);
		chapter1.setChapterTitle("Chapter 1");
		chapter1.setStartPage(1);
		chapter1.setEndPage(50);

		BookChapter chapter2 = new BookChapter();
		chapter2.setChapterId(new BookChapterId("Test Book", 2));
		chapter2.setBook(book);
		chapter2.setChapterTitle("Chapter 2");
		chapter2.setStartPage(51);
		chapter2.setEndPage(totalPages);

		List<BookChapter> chapters = new ArrayList<>();
		chapters.add(chapter1);
		chapters.add(chapter2);
		book.setChapters(chapters);

		ReadingProgress progress = new ReadingProgress(book);
		progress.setCompletedPages(completedPages);
		book.setReadingProgress(progress);

		return book;
	}

	private CreateBookRequest createBookRequestWithTwoChapters() {
		return createBookRequestWithChapters(List.of(
				createChapterRequest(1, "Chapter 1", 1, 50),
				createChapterRequest(2, "Chapter 2", 51, 100)
		));
	}

	private CreateBookRequest createBookRequestWithChapters(List<ChapterRequest> chapters) {
		CreateBookRequest request = new CreateBookRequest();
		setField(request, "bookName", "Test Book");
		setField(request, "totalPages", 100);
		setField(request, "plannedDays", 30);
		setField(request, "startDate", START_DATE);
		setField(request, "category", "Fiction");
		setField(request, "author", "Test Author");
		setField(request, "chapters", chapters);
		return request;
	}

	private ChapterRequest createChapterRequest(int number, String title, int startPage, int endPage) {
		ChapterRequest chapter = new ChapterRequest();
		setField(chapter, "chapterNumber", number);
		setField(chapter, "chapterTitle", title);
		setField(chapter, "startPage", startPage);
		setField(chapter, "endPage", endPage);
		return chapter;
	}

	private UpdateProgressRequest createUpdateProgressRequest(int completedPages) {
		UpdateProgressRequest request = new UpdateProgressRequest();
		setField(request, "completedPages", completedPages);
		return request;
	}

	private void stubProgressCalculations(Book book, LocalDate targetEndDate, long daysElapsed,
			long daysRemaining, double expectedPages, double plannedDailyPages, BookStatus status) {
		when(progressCalculationService.computeTargetEndDate(book.getStartDate(), book.getPlannedDays()))
				.thenReturn(targetEndDate);
		when(progressCalculationService.computeDaysElapsed(eq(book.getStartDate()), any()))
				.thenReturn(daysElapsed);
		when(progressCalculationService.computeDaysRemaining(any(), any()))
				.thenReturn(daysRemaining);
		when(progressCalculationService.computeExpectedPagesByDate(any(), any()))
				.thenReturn(expectedPages);
		when(progressCalculationService.computePlannedDailyPages(eq(book.getTotalPages()), eq(book.getPlannedDays())))
				.thenReturn(plannedDailyPages);
		when(progressCalculationService.resolveBookStatus(any(Integer.class), any(Integer.class),
				any(Double.class), any(), any()))
				.thenReturn(status);
	}

	private void setField(Object target, String fieldName, Object value) {
		try {
			java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, value);
		} catch (Exception e) {
			throw new RuntimeException("Failed to set field: " + fieldName, e);
		}
	}
}
