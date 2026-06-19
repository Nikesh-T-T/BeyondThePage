package com.beyondthepage.exception;

public class BookAlreadyExistsException extends RuntimeException {

	public BookAlreadyExistsException(String bookName) {
		super("Book already exists: " + bookName);
	}
}
