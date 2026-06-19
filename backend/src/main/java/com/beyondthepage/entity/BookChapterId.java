package com.beyondthepage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookChapterId implements Serializable {

	@Column(name = "book_name")
	private String bookName;

	@Column(name = "chapter_number")
	private int chapterNumber;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BookChapterId that)) {
			return false;
		}
		return chapterNumber == that.chapterNumber && Objects.equals(bookName, that.bookName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookName, chapterNumber);
	}
}
