package com.wissen.tpu.dto;

public class VerifierAuditRequest {

	private String requestId;
	private String pensionerId;
	private String panNo;
	private String pensionerName;
	private String dgStatus;
	private String photoMatchStatus;
	private String photoMatchScore;
	private String status;
	private String verifierCode;
	private String verifierStatus;
	private String verifierName;
	private String department;
	private String reMark;

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public VerifierAuditRequest() {
	}

	public VerifierAuditRequest(String requestId, String pensionerId, String panNo, String pensionerName,
			String dgStatus, String photoMatchStatus, String photoMatchScore, String status, String verifierCode,
			String verifierStatus, String verifierName) {

		this.requestId = requestId;
		this.pensionerId = pensionerId;
		this.panNo = panNo;
		this.pensionerName = pensionerName;
		this.dgStatus = dgStatus;
		this.photoMatchStatus = photoMatchStatus;
		this.photoMatchScore = photoMatchScore;
		this.status = status;
		this.verifierCode = verifierCode;
		this.verifierStatus = verifierStatus;
		this.verifierName = verifierName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPensionerId() {
		return pensionerId;
	}

	public void setPensionerId(String pensionerId) {
		this.pensionerId = pensionerId;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPensionerName() {
		return pensionerName;
	}

	public void setPensionerName(String pensionerName) {
		this.pensionerName = pensionerName;
	}

	public String getDgStatus() {
		return dgStatus;
	}

	public void setDgStatus(String dgStatus) {
		this.dgStatus = dgStatus;
	}

	public String getPhotoMatchStatus() {
		return photoMatchStatus;
	}

	public void setPhotoMatchStatus(String photoMatchStatus) {
		this.photoMatchStatus = photoMatchStatus;
	}

	public String getPhotoMatchScore() {
		return photoMatchScore;
	}

	public void setPhotoMatchScore(String photoMatchScore) {
		this.photoMatchScore = photoMatchScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVerifierCode() {
		return verifierCode;
	}

	public void setVerifierCode(String verifierCode) {
		this.verifierCode = verifierCode;
	}

	public String getVerifierStatus() {
		return verifierStatus;
	}

	public void setVerifierStatus(String verifierStatus) {
		this.verifierStatus = verifierStatus;
	}

	public String getVerifierName() {
		return verifierName;
	}

	public void setVerifierName(String verifierName) {
		this.verifierName = verifierName;
	}

}
