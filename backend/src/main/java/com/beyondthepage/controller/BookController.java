package com.beyondthepage.controller;

import com.beyondthepage.dto.request.CreateBookRequest;
import com.beyondthepage.dto.request.UpdateProgressRequest;
import com.beyondthepage.dto.response.ApiResponse;
import com.beyondthepage.dto.response.BookCreatedResponse;
import com.beyondthepage.dto.response.BookDetailResponse;
import com.beyondthepage.dto.response.BookSummaryResponse;
import com.beyondthepage.entity.Book;
import com.beyondthepage.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity<ApiResponse<List<BookSummaryResponse>>> getAllBooks(
			@RequestParam(required = false) String q) {
		List<BookSummaryResponse> books = bookService.getAllBooks(q);
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

	@PutMapping(value = "/{bookName}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<Void>> uploadCover(
			@PathVariable String bookName,
			@RequestParam("file") MultipartFile file) {
		bookService.uploadCover(bookName, file);
		return ResponseEntity.ok(ApiResponse.success("Cover uploaded successfully", null));
	}

	@GetMapping("/{bookName}/cover")
	public ResponseEntity<byte[]> getCover(@PathVariable String bookName) {
		Book book = bookService.getBookWithCover(bookName);
		if (book.getCoverImage() == null) {
			return ResponseEntity.notFound().build();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(book.getCoverImageType()));
		headers.setCacheControl("max-age=86400");
		return ResponseEntity.ok()
				.headers(headers)
				.body(book.getCoverImage());
	}
}
