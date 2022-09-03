package com.fvthree.eshop.exceptions;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {

	private static final long serialVersionUID = 5610591093535726890L;
	
	private HttpStatus status;
	private String message;
	
	public APIException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public APIException(String message, HttpStatus status, String message1) {
		super(message);
		this.status = status;
		this.message = message1;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
