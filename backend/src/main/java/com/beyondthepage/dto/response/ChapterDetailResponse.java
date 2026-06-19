package com.beyondthepage.dto.response;

import com.beyondthepage.enums.ChapterStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChapterDetailResponse {

	private int chapterNumber;
	private String chapterTitle;
	private int startPage;
	private int endPage;
	private ChapterStatus status;
}
