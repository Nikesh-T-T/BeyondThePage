package com.beyondthepage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_chapters")
@Getter
@Setter
@NoArgsConstructor
public class BookChapter {

	@EmbeddedId
	private BookChapterId chapterId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("bookName")
	@JoinColumn(name = "book_name")
	private Book book;

	@Column(name = "chapter_title", nullable = false)
	private String chapterTitle;

	@Column(name = "start_page", nullable = false)
	private int startPage;

	@Column(name = "end_page", nullable = false)
	private int endPage;

	public BookChapter(Book book, int chapterNumber, String chapterTitle, int startPage, int endPage) {
		this.chapterId = new BookChapterId(book.getBookName(), chapterNumber);
		this.book = book;
		this.chapterTitle = chapterTitle;
		this.startPage = startPage;
		this.endPage = endPage;
	}
}
