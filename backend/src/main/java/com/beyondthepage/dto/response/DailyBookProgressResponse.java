package com.beyondthepage.dto.response;

import com.beyondthepage.enums.BookStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyBookProgressResponse {

	private String bookName;
	private double plannedPagesByDate;
	private int completedPages;
	private double variancePages;
	private BookStatus status;
}
