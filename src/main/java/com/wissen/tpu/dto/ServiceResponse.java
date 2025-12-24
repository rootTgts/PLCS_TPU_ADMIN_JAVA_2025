package com.wissen.tpu.dto;

public class ServiceResponse {

	private String errorCode;
	private String errorMessage;
	private PensionerDTO pensioner;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public PensionerDTO getPensioner() {
		return pensioner;
	}

	public void setPensioner(PensionerDTO pensioner) {
		this.pensioner = pensioner;
	}

	@Override
	public String toString() {
		return "ServiceResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", pensioner=" + pensioner
				+ "]";
	}

}
