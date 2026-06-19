package com.beyondthepage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reading_progress")
@Getter
@Setter
@NoArgsConstructor
public class ReadingProgress {

	@Id
	@Column(name = "book_name")
	private String bookName;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "book_name")
	private Book book;

	@Column(name = "completed_pages", nullable = false)
	private int completedPages;

	public ReadingProgress(Book book) {
		this.book = book;
		this.completedPages = 0;
	}
}
