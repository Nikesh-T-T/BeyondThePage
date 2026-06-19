package com.beyondthepage.dto.response;

import com.beyondthepage.enums.BookStatus;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookSummaryResponse {

	private String bookName;
	private int totalPages;
	private int plannedDays;
	private LocalDate startDate;
	private int completedPages;
	private int remainingPages;
	private double completionPercentage;
	private double plannedDailyPages;
	private LocalDate targetEndDate;
	private BookStatus currentStatus;
	private long daysElapsed;
	private long daysRemaining;
	private boolean hasCoverImage;
}
