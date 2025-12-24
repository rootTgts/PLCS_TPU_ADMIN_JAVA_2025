package com.wissen.tpu.dto;

import java.time.LocalDateTime;

public class PensionerDownloadDTO {
	private String pensionerFpId;
	private String ppoNo;
	private String pensionerName;
	private String mobileNo;
	private String inputPhotoUuid;
	private LocalDateTime submittedTime;
	private LocalDateTime approvedTime;

	// Constructor
	public PensionerDownloadDTO(String pensionerFpId, String ppoNo, String pensionerName, String mobileNo,
			String inputPhotoUuid, LocalDateTime submittedTime, LocalDateTime approvedTime) {
		this.pensionerFpId = pensionerFpId;
		this.ppoNo = ppoNo;
		this.pensionerName = pensionerName;
		this.mobileNo = mobileNo;
		this.inputPhotoUuid = inputPhotoUuid;
		this.submittedTime = submittedTime;
		this.approvedTime = approvedTime;
	}

	public String getPensionerFpId() {
		return pensionerFpId;
	}

	public void setPensionerFpId(String pensionerFpId) {
		this.pensionerFpId = pensionerFpId;
	}

	public String getPpoNo() {
		return ppoNo;
	}

	public void setPpoNo(String ppoNo) {
		this.ppoNo = ppoNo;
	}

	public String getPensionerName() {
		return pensionerName;
	}

	public void setPensionerName(String pensionerName) {
		this.pensionerName = pensionerName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getInputPhotoUuid() {
		return inputPhotoUuid;
	}

	public void setInputPhotoUuid(String inputPhotoUuid) {
		this.inputPhotoUuid = inputPhotoUuid;
	}

	public LocalDateTime getSubmittedTime() {
		return submittedTime;
	}

	public void setSubmittedTime(LocalDateTime submittedTime) {
		this.submittedTime = submittedTime;
	}

	public LocalDateTime getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(LocalDateTime approvedTime) {
		this.approvedTime = approvedTime;
	}

}