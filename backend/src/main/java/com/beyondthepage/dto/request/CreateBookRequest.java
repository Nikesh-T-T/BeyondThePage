package com.beyondthepage.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateBookRequest {

	@NotBlank(message = "bookName is required")
	private String bookName;

	@Positive(message = "totalPages must be greater than 0")
	private int totalPages;

	@Positive(message = "plannedDays must be greater than 0")
	private int plannedDays;

	@NotNull(message = "startDate is required")
	private LocalDate startDate;

	@NotEmpty(message = "At least one chapter is required")
	@Valid
	private List<ChapterRequest> chapters;
}
