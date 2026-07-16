package com.beyondthepage.dto.response;

import com.beyondthepage.enums.ChapterStatus;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeeklyBookChapterResponse {

	private String bookName;
	private String category;
	private List<ChapterEntry> chapters;

	@Getter
	@Builder
	public static class ChapterEntry {

		private int chapterNumber;
		private String chapterTitle;
		private int startPage;
		private int endPage;
		private ChapterStatus status;
	}
}
