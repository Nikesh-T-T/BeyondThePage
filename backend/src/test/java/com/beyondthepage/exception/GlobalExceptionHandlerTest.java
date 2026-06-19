package com.beyondthepage.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.beyondthepage.dto.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler globalExceptionHandler;

	@BeforeEach
	void setUp() {
		globalExceptionHandler = new GlobalExceptionHandler();
	}

	@Test
	void shouldReturn400ForMethodArgumentNotValidException() {
		BindingResult bindingResult = mock(BindingResult.class);
		FieldError fieldError = new FieldError("createBookRequest", "bookName", "must not be blank");
		when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
		MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
		when(ex.getBindingResult()).thenReturn(bindingResult);

		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleValidationErrors(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("ERROR", response.getBody().getStatus());
		assertEquals("Validation failed", response.getBody().getMessage());
		assertEquals(List.of("bookName: must not be blank"), response.getBody().getErrors());
	}

	@Test
	void shouldReturn400ForConstraintViolationException() {
		ConstraintViolation<?> violation = mock(ConstraintViolation.class);
		jakarta.validation.Path path = mock(jakarta.validation.Path.class);
		when(path.toString()).thenReturn("updateProgress.completedPages");
		when(violation.getPropertyPath()).thenReturn(path);
		when(violation.getMessage()).thenReturn("must be >= 0");
		ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));

		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleConstraintViolation(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("ERROR", response.getBody().getStatus());
		assertEquals("Validation failed", response.getBody().getMessage());
	}

	@Test
	void shouldReturn404ForBookNotFoundException() {
		BookNotFoundException ex = new BookNotFoundException("Clean Code");

		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleBookNotFound(ex);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("ERROR", response.getBody().getStatus());
		assertEquals("Book not found: Clean Code", response.getBody().getMessage());
	}

	@Test
	void shouldReturn409ForBookAlreadyExistsException() {
		BookAlreadyExistsException ex = new BookAlreadyExistsException("Clean Code");

		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleBookAlreadyExists(ex);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("ERROR", response.getBody().getStatus());
		assertEquals("Book already exists: Clean Code", response.getBody().getMessage());
	}

	@Test
	void shouldReturn400ForInvalidChapterDataException() {
		InvalidChapterDataException ex = new InvalidChapterDataException("First chapter must start at page 1");

		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleInvalidChapterData(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("ERROR", response.getBody().getStatus());
		assertEquals("First chapter must start at page 1", response.getBody().getMessage());
	}

	@Test
	void shouldReturn400ForMethodArgumentTypeMismatchException() {
		MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
		when(ex.getValue()).thenReturn("not-a-date");
		when(ex.getName()).thenReturn("date");

		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleTypeMismatch(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("ERROR", response.getBody().getStatus());
		assertEquals("Invalid request parameter", response.getBody().getMessage());
	}

	@Test
	void shouldReturn500ForUnexpectedException() {
		Exception ex = new RuntimeException("Something went wrong");

		ResponseEntity<ApiErrorResponse> response = globalExceptionHandler.handleAll(ex);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("ERROR", response.getBody().getStatus());
		assertEquals("An unexpected error occurred", response.getBody().getMessage());
	}
}
