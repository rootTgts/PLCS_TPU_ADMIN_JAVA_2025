package com.wissen.tpu.dto;

public class ApiResponse<T> {
	private String requestId;
	private String status;
	private String message;
	private T data;

	public ApiResponse(String status, String message, T data) {

		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(String requestId, String status, String message, T data) {
		super();
		this.requestId = requestId;
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ApiResponse() {
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ApiResponse [requestId=" + requestId + ", status=" + status + ", message=" + message + ", data=" + data
				+ "]";
	}

}
