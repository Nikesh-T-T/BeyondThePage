package com.beyondthepage.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChapterRequest {

	@NotNull(message = "chapterNumber is required")
	private Integer chapterNumber;

	@NotBlank(message = "chapterTitle is required")
	private String chapterTitle;

	@Positive(message = "startPage must be greater than 0")
	private int startPage;

	@Positive(message = "endPage must be greater than 0")
	private int endPage;
}
