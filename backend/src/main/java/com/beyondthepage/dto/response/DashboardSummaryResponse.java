package com.beyondthepage.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardSummaryResponse {

	private int totalBooks;
	private int notStartedBooks;
	private int inProgressBooks;
	private int completedBooks;
	private int overdueBooks;
}
