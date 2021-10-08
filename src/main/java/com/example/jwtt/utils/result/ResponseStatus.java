package com.example.jwtt.utils.result;

import lombok.Data;

@Data
public class ResponseStatus {
	private Integer code = 200;
	private String message = "success";

	public ResponseStatus() {
	}

	public ResponseStatus(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
