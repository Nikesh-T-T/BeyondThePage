package com.beyondthepage.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

	@Id
	@Column(name = "book_name", nullable = false)
	private String bookName;

	@Column(name = "total_pages", nullable = false)
	private int totalPages;

	@Column(name = "planned_days", nullable = false)
	private int plannedDays;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "category", length = 100)
	private String category;

	@Column(name = "cover_image", columnDefinition = "bytea")
	private byte[] coverImage;

	@Column(name = "cover_image_type", length = 50)
	private String coverImageType;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy("chapterId.chapterNumber ASC")
	private List<BookChapter> chapters = new ArrayList<>();

	@OneToOne(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private ReadingProgress readingProgress;

	public Book(String bookName, int totalPages, int plannedDays, LocalDate startDate) {
		this.bookName = bookName;
		this.totalPages = totalPages;
		this.plannedDays = plannedDays;
		this.startDate = startDate;
	}
}
