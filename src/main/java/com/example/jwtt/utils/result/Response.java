package com.example.jwtt.utils.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {
	private ResponseStatus status;

	private Map<String, Object> data;

	public Response() {
		this.data = new HashMap<>();
		this.status = new ResponseStatus();
	}

	public Response(ResponseStatus status) {
		this.data = new HashMap<>();
		this.status = status;
	}

	public Response(Map<String, Object> data) {
		this.data = data;
		this.status = new ResponseStatus();
	}

	public Response(Map<String, Object> data, ResponseStatus status) {
		this.data = data;
		this.status = status;
	}
}
