package com.beyondthepage.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProgressRequest {

	@NotNull(message = "completedPages is required")
	@Min(value = 0, message = "completedPages must be >= 0")
	private Integer completedPages;
}
