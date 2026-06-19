package com.beyondthepage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

	private String status;
	private String message;
	private T data;

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>("SUCCESS", message, data);
	}
}
