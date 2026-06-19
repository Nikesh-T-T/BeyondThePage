package com.beyondthepage.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyDashboardResponse {

	private String selectedMonth;
	private int booksPlannedToFinish;
	private int booksCompleted;
	private int booksOverdue;
	private int booksNotStarted;
	private int booksOnTrack;
}
