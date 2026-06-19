package com.beyondthepage.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeeklyDashboardResponse {

	private LocalDate weekStartDate;
	private LocalDate weekEndDate;
	private int chaptersPlanned;
	private int chaptersCompleted;
	private int chaptersOverdue;
	private int chaptersNotStarted;
	private int totalPlannedPages;
	private int totalCompletedPages;
}
