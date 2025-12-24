package com.wissen.tpu.dto;

import javax.validation.constraints.NotBlank;

public class PensinorRegRequest {
	@NotBlank(message = "The requestId is required.")
	private String requestId;
	private String ppoNo;
	private String pensionerId;
	@NotBlank(message = "The inputPhoto is required.")
	private String inputPhoto;

	@NotBlank(message = "The dgStatus is required.")
	private String dgStatus;
	private String mobileNo;
	private String aadhaarNumber;
	private String depCode;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(String ppoNo) {
		this.ppoNo = ppoNo;
	}

	public String getPensionerId() {
		return pensionerId;
	}

	public void setPensionerId(String pensionerId) {
		this.pensionerId = pensionerId;
	}

	public String getInputPhoto() {
		return inputPhoto;
	}

	public void setInputPhoto(String inputPhoto) {
		this.inputPhoto = inputPhoto;
	}

	public String getDgStatus() {
		return dgStatus;
	}

	public void setDgStatus(String dgStatus) {
		this.dgStatus = dgStatus;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	@Override
	public String toString() {
		return "PensinorRegRequest [requestId=" + requestId + ", ppoNo=" + ppoNo + ", pensionerId=" + pensionerId
				+ ", inputPhoto=" + inputPhoto + ", dgStatus=" + dgStatus + ", mobileNo=" + mobileNo
				+ ", aadhaarNumber=" + aadhaarNumber + ", depCode=" + depCode + "]";
	}

}
