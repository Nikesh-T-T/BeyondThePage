package com.beyondthepage.repository;

import com.beyondthepage.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, String> {

	boolean existsByBookName(String bookName);

	@Query("SELECT b FROM Book b JOIN FETCH b.readingProgress ORDER BY b.bookName ASC")
	List<Book> findAllWithProgress();

	@Query("SELECT b FROM Book b JOIN FETCH b.readingProgress LEFT JOIN FETCH b.chapters WHERE b.bookName = :bookName")
	Optional<Book> findByBookNameWithDetails(@Param("bookName") String bookName);
}
