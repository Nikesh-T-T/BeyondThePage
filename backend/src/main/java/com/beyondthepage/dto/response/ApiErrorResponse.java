package com.beyondthepage.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {

	private String status;
	private String message;
	private List<String> errors;

	public static ApiErrorResponse of(String message, List<String> errors) {
		return new ApiErrorResponse("ERROR", message, errors);
	}
}
