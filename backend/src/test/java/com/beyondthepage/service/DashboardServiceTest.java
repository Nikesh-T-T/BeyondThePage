package com.beyondthepage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.beyondthepage.dto.response.DailyBookProgressResponse;
import com.beyondthepage.dto.response.DashboardSummaryResponse;
import com.beyondthepage.dto.response.MonthlyDashboardResponse;
import com.beyondthepage.dto.response.WeeklyDashboardResponse;
import com.beyondthepage.entity.Book;
import com.beyondthepage.entity.BookChapter;
import com.beyondthepage.entity.BookChapterId;
import com.beyondthepage.entity.ReadingProgress;
import com.beyondthepage.enums.BookStatus;
import com.beyondthepage.enums.ChapterStatus;
import com.beyondthepage.repository.BookRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private ProgressCalculationService progressCalculationService;

	private DashboardService dashboardService;

	private static final LocalDate START_DATE = LocalDate.of(2026, 7, 1);
	private static final LocalDate TARGET_END_DATE = LocalDate.of(2026, 8, 30);

	@BeforeEach
	void setUp() {
		dashboardService = new DashboardService(bookRepository, progressCalculationService);
	}

	@Test
	void shouldReturnSummaryWithCorrectCountsForMixedStatuses() {
		Book notStartedBook = createBookWithProgress(100, 10, START_DATE, 0);
		Book onTrackBook = createBookWithProgress(100, 10, START_DATE, 50);
		Book atRiskBook = createBookWithProgress(100, 10, START_DATE, 20);
		Book completedBook = createBookWithProgress(100, 10, START_DATE, 100);
		Book overdueBook = createBookWithProgress(100, 10, START_DATE, 10);

		when(bookRepository.findAllWithProgress()).thenReturn(
				List.of(notStartedBook, onTrackBook, atRiskBook, completedBook, overdueBook));
		stubResolveStatus(notStartedBook, BookStatus.NOT_STARTED);
		stubResolveStatus(onTrackBook, BookStatus.ON_TRACK);
		stubResolveStatus(atRiskBook, BookStatus.AT_RISK);
		stubResolveStatus(completedBook, BookStatus.COMPLETED);
		stubResolveStatus(overdueBook, BookStatus.OVERDUE);

		DashboardSummaryResponse response = dashboardService.getSummary();

		assertEquals(5, response.getTotalBooks());
		assertEquals(1, response.getNotStartedBooks());
		assertEquals(2, response.getInProgressBooks());
		assertEquals(1, response.getCompletedBooks());
		assertEquals(1, response.getOverdueBooks());
	}

	@Test
	void shouldReturnZeroCountsWhenNoBooksExist() {
		when(bookRepository.findAllWithProgress()).thenReturn(List.of());

		DashboardSummaryResponse response = dashboardService.getSummary();

		assertEquals(0, response.getTotalBooks());
		assertEquals(0, response.getNotStartedBooks());
		assertEquals(0, response.getInProgressBooks());
		assertEquals(0, response.getCompletedBooks());
		assertEquals(0, response.getOverdueBooks());
	}

	@Test
	void shouldReturnMonthlyDashboardWithBooksPlannedToFinishInSelectedMonth() {
		Book book = createBookWithProgress(200, 60, LocalDate.of(2026, 7, 1), 50);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		stubResolveStatus(book, BookStatus.ON_TRACK);

		MonthlyDashboardResponse response = dashboardService.getMonthlyDashboard(YearMonth.of(2026, 8));

		assertEquals("2026-08", response.getSelectedMonth());
		assertEquals(1, response.getBooksPlannedToFinish());
		assertEquals(0, response.getBooksCompleted());
		assertEquals(1, response.getBooksOnTrack());
	}

	@Test
	void shouldNotCountBookPlannedToFinishWhenTargetEndDateIsOutsideMonth() {
		Book book = createBookWithProgress(200, 60, LocalDate.of(2026, 7, 1), 50);
		LocalDate outsideMonthDate = LocalDate.of(2026, 10, 15);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		when(progressCalculationService.computeTargetEndDate(book.getStartDate(), book.getPlannedDays()))
				.thenReturn(outsideMonthDate);
		when(progressCalculationService.computeExpectedPagesByDate(any(Book.class), any(LocalDate.class)))
				.thenReturn(50.0);
		when(progressCalculationService.resolveBookStatus(
				eq(book.getReadingProgress().getCompletedPages()),
				eq(book.getTotalPages()),
				eq(50.0),
				eq(outsideMonthDate),
				any()))
				.thenReturn(BookStatus.ON_TRACK);

		MonthlyDashboardResponse response = dashboardService.getMonthlyDashboard(YearMonth.of(2026, 8));

		assertEquals(0, response.getBooksPlannedToFinish());
	}

	@Test
	void shouldReturnWeeklyDashboardWithChaptersInPlanWindow() {
		Book book = createBookWithProgressAndChapters(100, 10, LocalDate.of(2026, 7, 1), 0);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		when(progressCalculationService.computePlannedDailyPages(100, 10)).thenReturn(10.0);
		when(progressCalculationService.computeExpectedPagesByDate(any(), any())).thenReturn(50.0);
		when(progressCalculationService.resolveChapterStatus(any(Integer.class), any(), any(Double.class)))
				.thenReturn(ChapterStatus.NOT_STARTED);

		WeeklyDashboardResponse response = dashboardService.getWeeklyDashboard(LocalDate.of(2026, 7, 8));

		assertNotNull(response);
		assertEquals(LocalDate.of(2026, 7, 6), response.getWeekStartDate());
		assertEquals(LocalDate.of(2026, 7, 12), response.getWeekEndDate());
		assertNotNull(response.getBooks());
		assertEquals(1, response.getBooks().size());
		assertEquals("Test Book", response.getBooks().get(0).getBookName());
		assertEquals(2, response.getBooks().get(0).getChapters().size());
	}

	@Test
	void shouldReturnDailyDashboardWithExpectedPagesForRequestedDate() {
		Book book = createBookWithProgress(100, 10, LocalDate.of(2026, 7, 1), 30);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		LocalDate requestDate = LocalDate.of(2026, 7, 6);
		when(progressCalculationService.computeExpectedPagesByDate(book, requestDate)).thenReturn(50.0);
		when(progressCalculationService.computeTargetEndDate(book.getStartDate(), book.getPlannedDays()))
				.thenReturn(TARGET_END_DATE);
		when(progressCalculationService.resolveBookStatus(30, 100, 50.0, TARGET_END_DATE, requestDate))
				.thenReturn(BookStatus.AT_RISK);

		List<DailyBookProgressResponse> responses = dashboardService.getDailyDashboard(requestDate);

		assertEquals(1, responses.size());
		assertEquals("Test Book", responses.get(0).getBookName());
		assertEquals(50.0, responses.get(0).getPlannedPagesByDate());
		assertEquals(30, responses.get(0).getCompletedPages());
		assertEquals(-20.0, responses.get(0).getVariancePages());
		assertEquals(BookStatus.AT_RISK, responses.get(0).getStatus());
		assertEquals(31, responses.get(0).getPlannedPageRangeStart());
		assertEquals(50, responses.get(0).getPlannedPageRangeEnd());
	}

	@Test
	void shouldExcludeBooksNotYetStartedFromDailyDashboard() {
		Book book = createBookWithProgress(100, 10, LocalDate.of(2026, 8, 1), 0);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));

		List<DailyBookProgressResponse> responses = dashboardService.getDailyDashboard(LocalDate.of(2026, 7, 6));

		assertEquals(0, responses.size());
	}

	@Test
	void shouldExcludeNotStartedBooksAfterTargetEndDateFromDailyDashboard() {
		Book book = createBookWithProgress(100, 10, LocalDate.of(2026, 6, 1), 0);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		when(progressCalculationService.computeTargetEndDate(book.getStartDate(), book.getPlannedDays()))
				.thenReturn(LocalDate.of(2026, 6, 11));

		List<DailyBookProgressResponse> responses = dashboardService.getDailyDashboard(LocalDate.of(2026, 7, 6));

		assertEquals(0, responses.size());
	}

	@Test
	void shouldIncludeOverdueBooksInDailyDashboard() {
		Book book = createBookWithProgress(100, 10, LocalDate.of(2026, 6, 1), 80);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));
		LocalDate requestDate = LocalDate.of(2026, 7, 6);
		LocalDate targetEndDate = LocalDate.of(2026, 6, 11);
		when(progressCalculationService.computeTargetEndDate(book.getStartDate(), book.getPlannedDays()))
				.thenReturn(targetEndDate);
		when(progressCalculationService.computeExpectedPagesByDate(book, requestDate)).thenReturn(100.0);
		when(progressCalculationService.resolveBookStatus(80, 100, 100.0, targetEndDate, requestDate))
				.thenReturn(BookStatus.OVERDUE);

		List<DailyBookProgressResponse> responses = dashboardService.getDailyDashboard(requestDate);

		assertEquals(1, responses.size());
		assertEquals(BookStatus.OVERDUE, responses.get(0).getStatus());
	}

	@Test
	void shouldExcludeCompletedBooksFromDailyDashboard() {
		Book book = createBookWithProgress(100, 10, LocalDate.of(2026, 7, 1), 100);
		when(bookRepository.findAllWithProgress()).thenReturn(List.of(book));

		List<DailyBookProgressResponse> responses = dashboardService.getDailyDashboard(LocalDate.of(2026, 7, 6));

		assertEquals(0, responses.size());
	}

	@Test
	void shouldReturnEmptyDailyDashboardWhenNoBooksExist() {
		when(bookRepository.findAllWithProgress()).thenReturn(List.of());

		List<DailyBookProgressResponse> responses = dashboardService.getDailyDashboard(LocalDate.now());

		assertEquals(0, responses.size());
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

	private void stubResolveStatus(Book book, BookStatus status) {
		lenient().when(progressCalculationService.computeTargetEndDate(book.getStartDate(), book.getPlannedDays()))
				.thenReturn(TARGET_END_DATE);
		lenient().when(progressCalculationService.computeExpectedPagesByDate(any(Book.class), any(LocalDate.class)))
				.thenReturn(50.0);
		when(progressCalculationService.resolveBookStatus(
				eq(book.getReadingProgress().getCompletedPages()),
				eq(book.getTotalPages()),
				eq(50.0),
				eq(TARGET_END_DATE),
				any()))
				.thenReturn(status);
	}
}
