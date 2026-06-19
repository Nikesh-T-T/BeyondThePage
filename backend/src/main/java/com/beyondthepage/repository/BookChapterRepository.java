package com.beyondthepage.repository;

import com.beyondthepage.entity.BookChapter;
import com.beyondthepage.entity.BookChapterId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookChapterRepository extends JpaRepository<BookChapter, BookChapterId> {
}
