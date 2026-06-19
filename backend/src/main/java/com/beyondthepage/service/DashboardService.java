package com.beyondthepage.service;

import com.beyondthepage.dto.response.DailyBookProgressResponse;
import com.beyondthepage.dto.response.DashboardSummaryResponse;
import com.beyondthepage.dto.response.MonthlyDashboardResponse;
import com.beyondthepage.dto.response.WeeklyDashboardResponse;
import com.beyondthepage.entity.Book;
import com.beyondthepage.entity.BookChapter;
import com.beyondthepage.enums.BookStatus;
import com.beyondthepage.enums.ChapterStatus;
import com.beyondthepage.repository.BookRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

	private final BookRepository bookRepository;
	private final ProgressCalculationService progressCalculationService;

	public DashboardService(BookRepository bookRepository,
			ProgressCalculationService progressCalculationService) {
		this.bookRepository = bookRepository;
		this.progressCalculationService = progressCalculationService;
	}

	@Transactional(readOnly = true)
	public DashboardSummaryResponse getSummary() {
		List<Book> books = bookRepository.findAllWithProgress();
		LocalDate today = LocalDate.now();

		int notStarted = 0;
		int inProgress = 0;
		int completed = 0;
		int overdue = 0;

		for (Book book : books) {
			BookStatus status = resolveStatus(book, today);
			if (status == BookStatus.NOT_STARTED) {
				notStarted++;
			} else if (status == BookStatus.COMPLETED) {
				completed++;
			} else if (status == BookStatus.OVERDUE) {
				overdue++;
			} else {
				inProgress++;
			}
		}

		return DashboardSummaryResponse.builder()
				.totalBooks(books.size())
				.notStartedBooks(notStarted)
				.inProgressBooks(inProgress)
				.completedBooks(completed)
				.overdueBooks(overdue)
				.build();
	}

	@Transactional(readOnly = true)
	public MonthlyDashboardResponse getMonthlyDashboard(YearMonth yearMonth) {
		List<Book> books = bookRepository.findAllWithProgress();
		LocalDate today = LocalDate.now();
		LocalDate monthStart = yearMonth.atDay(1);
		LocalDate monthEnd = yearMonth.atEndOfMonth();

		int plannedToFinish = 0;
		int booksCompleted = 0;
		int booksOverdue = 0;
		int booksNotStarted = 0;
		int booksOnTrack = 0;

		for (Book book : books) {
			LocalDate targetEndDate = progressCalculationService.computeTargetEndDate(
					book.getStartDate(), book.getPlannedDays());
			BookStatus status = resolveStatus(book, today);

			if (!targetEndDate.isBefore(monthStart) && !targetEndDate.isAfter(monthEnd)) {
				plannedToFinish++;
			}
			if (status == BookStatus.COMPLETED) {
				booksCompleted++;
			} else if (status == BookStatus.OVERDUE) {
				booksOverdue++;
			} else if (status == BookStatus.NOT_STARTED) {
				booksNotStarted++;
			} else if (status == BookStatus.ON_TRACK) {
				booksOnTrack++;
			}
		}

		return MonthlyDashboardResponse.builder()
				.selectedMonth(yearMonth.toString())
				.booksPlannedToFinish(plannedToFinish)
				.booksCompleted(booksCompleted)
				.booksOverdue(booksOverdue)
				.booksNotStarted(booksNotStarted)
				.booksOnTrack(booksOnTrack)
				.build();
	}

	@Transactional(readOnly = true)
	public WeeklyDashboardResponse getWeeklyDashboard(LocalDate date) {
		LocalDate weekStart = date.with(DayOfWeek.MONDAY);
		LocalDate weekEnd = date.with(DayOfWeek.SUNDAY);
		LocalDate today = LocalDate.now();

		List<Book> books = bookRepository.findAllWithProgress();

		int chaptersPlanned = 0;
		int chaptersCompleted = 0;
		int chaptersOverdue = 0;
		int chaptersNotStarted = 0;
		int totalPlannedPages = 0;
		int totalCompletedPages = 0;

		for (Book book : books) {
			int completedPages = book.getReadingProgress().getCompletedPages();
			double plannedDailyPages = progressCalculationService.computePlannedDailyPages(
					book.getTotalPages(), book.getPlannedDays());
			double expectedPagesByToday = progressCalculationService.computeExpectedPagesByDate(book, today);

			double expectedAtWeekEnd = progressCalculationService.computeExpectedPagesByDate(book, weekEnd);
			double expectedAtWeekStart = progressCalculationService.computeExpectedPagesByDate(
					book, weekStart.minusDays(1));
			totalPlannedPages += (int) Math.max(0, expectedAtWeekEnd - expectedAtWeekStart);
			totalCompletedPages += completedPages;

			for (BookChapter chapter : book.getChapters()) {
				long dayChapterShouldEnd = (long) Math.ceil(chapter.getEndPage() / plannedDailyPages);
				LocalDate chapterPlanEndDate = book.getStartDate().plusDays(dayChapterShouldEnd);

				long dayChapterShouldStart = (long) Math.ceil(
						(chapter.getStartPage() - 1) / plannedDailyPages);
				LocalDate chapterPlanStartDate = book.getStartDate().plusDays(dayChapterShouldStart);

				if (!chapterPlanEndDate.isBefore(weekStart) && !chapterPlanStartDate.isAfter(weekEnd)) {
					chaptersPlanned++;
					ChapterStatus chapterStatus = progressCalculationService.resolveChapterStatus(
							completedPages, chapter, expectedPagesByToday);
					if (chapterStatus == ChapterStatus.COMPLETED) {
						chaptersCompleted++;
					} else if (chapterStatus == ChapterStatus.OVERDUE) {
						chaptersOverdue++;
					} else if (chapterStatus == ChapterStatus.NOT_STARTED) {
						chaptersNotStarted++;
					}
				}
			}
		}

		return WeeklyDashboardResponse.builder()
				.weekStartDate(weekStart)
				.weekEndDate(weekEnd)
				.chaptersPlanned(chaptersPlanned)
				.chaptersCompleted(chaptersCompleted)
				.chaptersOverdue(chaptersOverdue)
				.chaptersNotStarted(chaptersNotStarted)
				.totalPlannedPages(totalPlannedPages)
				.totalCompletedPages(totalCompletedPages)
				.build();
	}

	@Transactional(readOnly = true)
	public List<DailyBookProgressResponse> getDailyDashboard(LocalDate date) {
		List<Book> books = bookRepository.findAllWithProgress();

		return books.stream()
				.map(book -> {
					int completedPages = book.getReadingProgress().getCompletedPages();
					LocalDate targetEndDate = progressCalculationService.computeTargetEndDate(
							book.getStartDate(), book.getPlannedDays());
					double expectedByDate = progressCalculationService.computeExpectedPagesByDate(book, date);
					double variance = completedPages - expectedByDate;
					BookStatus status = progressCalculationService.resolveBookStatus(completedPages,
							book.getTotalPages(), expectedByDate, targetEndDate, date);

					return DailyBookProgressResponse.builder()
							.bookName(book.getBookName())
							.plannedPagesByDate(expectedByDate)
							.completedPages(completedPages)
							.variancePages(variance)
							.status(status)
							.hasCoverImage(book.getCoverImage() != null)
							.build();
				})
				.toList();
	}

	private BookStatus resolveStatus(Book book, LocalDate today) {
		int completedPages = book.getReadingProgress().getCompletedPages();
		LocalDate targetEndDate = progressCalculationService.computeTargetEndDate(
				book.getStartDate(), book.getPlannedDays());
		double expectedPagesByToday = progressCalculationService.computeExpectedPagesByDate(book, today);
		return progressCalculationService.resolveBookStatus(completedPages,
				book.getTotalPages(), expectedPagesByToday, targetEndDate, today);
	}
}
