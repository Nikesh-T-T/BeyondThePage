package com.beyondthepage.controller;

import com.beyondthepage.dto.request.CreateBookRequest;
import com.beyondthepage.dto.request.UpdateProgressRequest;
import com.beyondthepage.dto.response.ApiResponse;
import com.beyondthepage.dto.response.BookCreatedResponse;
import com.beyondthepage.dto.response.BookDetailResponse;
import com.beyondthepage.dto.response.BookSummaryResponse;
import com.beyondthepage.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<BookCreatedResponse>> createBook(
			@RequestBody @Valid CreateBookRequest request) {
		BookCreatedResponse response = bookService.createBook(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Book created successfully", response));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<BookSummaryResponse>>> getAllBooks() {
		List<BookSummaryResponse> books = bookService.getAllBooks();
		return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", books));
	}

	@GetMapping("/{bookName}")
	public ResponseEntity<ApiResponse<BookDetailResponse>> getBookDetail(
			@PathVariable String bookName) {
		BookDetailResponse detail = bookService.getBookDetail(bookName);
		return ResponseEntity.ok(ApiResponse.success("Book details fetched successfully", detail));
	}

	@PutMapping("/{bookName}/progress")
	public ResponseEntity<Void> updateProgress(
			@PathVariable String bookName,
			@RequestBody @Valid UpdateProgressRequest request) {
		bookService.updateProgress(bookName, request);
		return ResponseEntity.ok().build();
	}
}
