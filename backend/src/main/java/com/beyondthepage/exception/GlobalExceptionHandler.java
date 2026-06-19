package com.beyondthepage.exception;

import com.beyondthepage.dto.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(e -> e.getField() + ": " + e.getDefaultMessage())
				.toList();
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of("Validation failed", errors));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
		List<String> errors = ex.getConstraintViolations()
				.stream()
				.map(v -> v.getPropertyPath() + ": " + v.getMessage())
				.toList();
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of("Validation failed", errors));
	}

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleBookNotFound(BookNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiErrorResponse.of(ex.getMessage(), List.of()));
	}

	@ExceptionHandler(BookAlreadyExistsException.class)
	public ResponseEntity<ApiErrorResponse> handleBookAlreadyExists(BookAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorResponse.of(ex.getMessage(), List.of()));
	}

	@ExceptionHandler(InvalidChapterDataException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidChapterData(InvalidChapterDataException ex) {
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of(ex.getMessage(), List.of(ex.getMessage())));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String error = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
		return ResponseEntity.badRequest()
				.body(ApiErrorResponse.of("Invalid request parameter", List.of(error)));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleAll(Exception ex) {
		return ResponseEntity.internalServerError()
				.body(ApiErrorResponse.of("An unexpected error occurred", List.of()));
	}
}
