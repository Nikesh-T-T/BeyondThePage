package com.beyondthepage.exception;

public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException(String bookName) {
		super("Book not found: " + bookName);
	}
}
