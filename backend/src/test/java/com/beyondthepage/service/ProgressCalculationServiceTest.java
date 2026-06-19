package com.beyondthepage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.beyondthepage.entity.Book;
import com.beyondthepage.entity.BookChapter;
import com.beyondthepage.entity.BookChapterId;
import com.beyondthepage.enums.BookStatus;
import com.beyondthepage.enums.ChapterStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProgressCalculationServiceTest {

	private ProgressCalculationService progressCalculationService;

	@BeforeEach
	void setUp() {
		progressCalculationService = new ProgressCalculationService();
	}

	@Test
	void shouldComputeTargetEndDate() {
		LocalDate startDate = LocalDate.of(2026, 7, 1);

		LocalDate targetEndDate = progressCalculationService.computeTargetEndDate(startDate, 60);

		assertEquals(LocalDate.of(2026, 8, 30), targetEndDate);
	}

	@Test
	void shouldComputeDaysElapsedWhenReferenceIsAfterStart() {
		LocalDate startDate = LocalDate.of(2026, 7, 1);
		LocalDate referenceDate = LocalDate.of(2026, 7, 11);

		long daysElapsed = progressCalculationService.computeDaysElapsed(startDate, referenceDate);

		assertEquals(10, daysElapsed);
	}

	@Test
	void shouldReturnZeroDaysElapsedWhenReferenceIsBeforeStart() {
		LocalDate startDate = LocalDate.of(2026, 7, 10);
		LocalDate referenceDate = LocalDate.of(2026, 7, 1);

		long daysElapsed = progressCalculationService.computeDaysElapsed(startDate, referenceDate);

		assertEquals(0, daysElapsed);
	}

	@Test
	void shouldComputeDaysRemainingWhenTargetIsInFuture() {
		LocalDate targetEndDate = LocalDate.of(2026, 8, 30);
		LocalDate referenceDate = LocalDate.of(2026, 7, 1);

		long daysRemaining = progressCalculationService.computeDaysRemaining(targetEndDate, referenceDate);

		assertEquals(60, daysRemaining);
	}

	@Test
	void shouldReturnZeroDaysRemainingWhenTargetIsPast() {
		LocalDate targetEndDate = LocalDate.of(2026, 7, 1);
		LocalDate referenceDate = LocalDate.of(2026, 7, 10);

		long daysRemaining = progressCalculationService.computeDaysRemaining(targetEndDate, referenceDate);

		assertEquals(0, daysRemaining);
	}

	@Test
	void shouldComputePlannedDailyPages() {
		double plannedDailyPages = progressCalculationService.computePlannedDailyPages(300, 30);

		assertEquals(10.0, plannedDailyPages);
	}

	@Test
	void shouldComputeExpectedPagesByDate() {
		Book book = createBook(300, 30, LocalDate.of(2026, 7, 1));
		LocalDate referenceDate = LocalDate.of(2026, 7, 11);

		double expected = progressCalculationService.computeExpectedPagesByDate(book, referenceDate);

		assertEquals(100.0, expected);
	}

	@Test
	void shouldCapExpectedPagesAtTotalPages() {
		Book book = createBook(100, 10, LocalDate.of(2026, 7, 1));
		LocalDate referenceDate = LocalDate.of(2026, 9, 1);

		double expected = progressCalculationService.computeExpectedPagesByDate(book, referenceDate);

		assertEquals(100.0, expected);
	}

	@Test
	void shouldResolveBookStatusAsNotStarted() {
		LocalDate today = LocalDate.of(2026, 7, 10);
		LocalDate targetEndDate = LocalDate.of(2026, 8, 30);

		BookStatus status = progressCalculationService.resolveBookStatus(0, 300, 100.0, targetEndDate, today);

		assertEquals(BookStatus.NOT_STARTED, status);
	}

	@Test
	void shouldResolveBookStatusAsCompleted() {
		LocalDate today = LocalDate.of(2026, 7, 10);
		LocalDate targetEndDate = LocalDate.of(2026, 8, 30);

		BookStatus status = progressCalculationService.resolveBookStatus(300, 300, 100.0, targetEndDate, today);

		assertEquals(BookStatus.COMPLETED, status);
	}

	@Test
	void shouldResolveBookStatusAsOverdue() {
		LocalDate today = LocalDate.of(2026, 9, 1);
		LocalDate targetEndDate = LocalDate.of(2026, 8, 30);

		BookStatus status = progressCalculationService.resolveBookStatus(100, 300, 300.0, targetEndDate, today);

		assertEquals(BookStatus.OVERDUE, status);
	}

	@Test
	void shouldResolveBookStatusAsOnTrack() {
		LocalDate today = LocalDate.of(2026, 7, 11);
		LocalDate targetEndDate = LocalDate.of(2026, 8, 30);

		BookStatus status = progressCalculationService.resolveBookStatus(120, 300, 100.0, targetEndDate, today);

		assertEquals(BookStatus.ON_TRACK, status);
	}

	@Test
	void shouldResolveBookStatusAsAtRisk() {
		LocalDate today = LocalDate.of(2026, 7, 11);
		LocalDate targetEndDate = LocalDate.of(2026, 8, 30);

		BookStatus status = progressCalculationService.resolveBookStatus(50, 300, 100.0, targetEndDate, today);

		assertEquals(BookStatus.AT_RISK, status);
	}

	@Test
	void shouldResolveChapterStatusAsCompleted() {
		BookChapter chapter = createChapter(1, 100);

		ChapterStatus status = progressCalculationService.resolveChapterStatus(100, chapter, 80.0);

		assertEquals(ChapterStatus.COMPLETED, status);
	}

	@Test
	void shouldResolveChapterStatusAsInProgress() {
		BookChapter chapter = createChapter(1, 100);

		ChapterStatus status = progressCalculationService.resolveChapterStatus(50, chapter, 80.0);

		assertEquals(ChapterStatus.IN_PROGRESS, status);
	}

	@Test
	void shouldResolveChapterStatusAsOverdue() {
		BookChapter chapter = createChapter(1, 100);

		ChapterStatus status = progressCalculationService.resolveChapterStatus(0, chapter, 110.0);

		assertEquals(ChapterStatus.OVERDUE, status);
	}

	@Test
	void shouldResolveChapterStatusAsNotStarted() {
		BookChapter chapter = createChapter(51, 100);

		ChapterStatus status = progressCalculationService.resolveChapterStatus(30, chapter, 40.0);

		assertEquals(ChapterStatus.NOT_STARTED, status);
	}

	@ParameterizedTest
	@CsvSource({
		"0,  300, 0",
		"10, 300, 10",
		"30, 300, 30"
	})
	void shouldComputeDaysElapsedForVariousScenarios(int startDayOffset, int totalDays, long expectedElapsed) {
		LocalDate startDate = LocalDate.of(2026, 7, 1);
		LocalDate referenceDate = startDate.plusDays(startDayOffset);
		Book book = createBook(totalDays, 30, startDate);

		long daysElapsed = progressCalculationService.computeDaysElapsed(book.getStartDate(), referenceDate);

		assertEquals(expectedElapsed, daysElapsed);
	}

	private Book createBook(int totalPages, int plannedDays, LocalDate startDate) {
		return new Book("Test Book", totalPages, plannedDays, startDate);
	}

	private BookChapter createChapter(int startPage, int endPage) {
		Book book = createBook(200, 20, LocalDate.of(2026, 7, 1));
		BookChapter chapter = new BookChapter();
		chapter.setChapterId(new BookChapterId("Test Book", 1));
		chapter.setBook(book);
		chapter.setChapterTitle("Test Chapter");
		chapter.setStartPage(startPage);
		chapter.setEndPage(endPage);
		return chapter;
	}
}
