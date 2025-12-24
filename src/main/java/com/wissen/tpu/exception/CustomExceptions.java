package com.wissen.tpu.exception;

public class CustomExceptions extends RuntimeException {
	private String message;

	public CustomExceptions(String message) {
		this.message = message;
	}
}
