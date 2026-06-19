package com.beyondthepage.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookCreatedResponse {

	private String bookName;
	private int totalPages;
	private int plannedDays;
	private LocalDate startDate;
	private int completedPages;
	private boolean hasCoverImage;
}
