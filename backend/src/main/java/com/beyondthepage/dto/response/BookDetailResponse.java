package com.beyondthepage.dto.response;

import com.beyondthepage.enums.BookStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDetailResponse {

	private String bookName;
	private String category;
	private int totalPages;
	private int plannedDays;
	private LocalDate startDate;
	private LocalDate targetEndDate;
	private int completedPages;
	private int remainingPages;
	private double completionPercentage;
	private double plannedDailyPages;
	private double expectedPagesByToday;
	private double variancePages;
	private BookStatus currentStatus;
	private long daysElapsed;
	private long daysRemaining;
	private int completedChapters;
	private int pendingChapters;
	private int overdueChapters;
	private List<ChapterDetailResponse> chapters;
	private boolean hasCoverImage;
}
