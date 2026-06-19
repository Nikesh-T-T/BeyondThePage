package com.beyondthepage.service;

import com.beyondthepage.entity.Book;
import com.beyondthepage.entity.BookChapter;
import com.beyondthepage.enums.BookStatus;
import com.beyondthepage.enums.ChapterStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

@Service
public class ProgressCalculationService {

	public LocalDate computeTargetEndDate(LocalDate startDate, int plannedDays) {
		return startDate.plusDays(plannedDays);
	}

	public long computeDaysElapsed(LocalDate startDate, LocalDate referenceDate) {
		return Math.max(0, ChronoUnit.DAYS.between(startDate, referenceDate));
	}

	public long computeDaysRemaining(LocalDate targetEndDate, LocalDate referenceDate) {
		return Math.max(0, ChronoUnit.DAYS.between(referenceDate, targetEndDate));
	}

	public double computePlannedDailyPages(int totalPages, int plannedDays) {
		return totalPages / (double) plannedDays;
	}

	public double computeExpectedPagesByDate(Book book, LocalDate referenceDate) {
		if (referenceDate.isBefore(book.getStartDate())) {
			return 0;
		}
		long daysElapsed = ChronoUnit.DAYS.between(book.getStartDate(), referenceDate);
		double plannedDailyPages = computePlannedDailyPages(book.getTotalPages(), book.getPlannedDays());
		return Math.min(plannedDailyPages * (daysElapsed + 1), book.getTotalPages());
	}

	public BookStatus resolveBookStatus(int completedPages, int totalPages,
			double expectedPagesByToday, LocalDate targetEndDate, LocalDate today) {
		if (completedPages == 0) {
			return BookStatus.NOT_STARTED;
		}
		if (completedPages >= totalPages) {
			return BookStatus.COMPLETED;
		}
		if (today.isAfter(targetEndDate)) {
			return BookStatus.OVERDUE;
		}
		if (completedPages >= expectedPagesByToday) {
			return BookStatus.ON_TRACK;
		}
		return BookStatus.AT_RISK;
	}

	public ChapterStatus resolveChapterStatus(int completedPages, BookChapter chapter,
			double expectedPagesByToday) {
		if (completedPages >= chapter.getEndPage()) {
			return ChapterStatus.COMPLETED;
		}
		if (completedPages >= chapter.getStartPage()) {
			return ChapterStatus.IN_PROGRESS;
		}
		if (expectedPagesByToday > chapter.getEndPage()) {
			return ChapterStatus.OVERDUE;
		}
		return ChapterStatus.NOT_STARTED;
	}
}
