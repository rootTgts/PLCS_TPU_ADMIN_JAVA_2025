package com.wissen.tpu.dto;

public class MatchedDetails {

	private String pensionerId;
	private String ppoId;
	private String pensionerName;
	private String DOB;
	private String pensionerType;
	private String mobile;
	private String dgStatus;
	private String photoMatchStatus;
	private String photoMatchScore;
	private String status;
	private String inputPhoto;
	private String departmentPhoto;
	private String departmentName;
	private String unitName;
	private String unitCode;
	private String submittedDate;

	public MatchedDetails() {

	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPensionerId() {
		return pensionerId;
	}

	public String getInputPhoto() {
		return inputPhoto;
	}

	public void setInputPhoto(String inputPhoto) {
		this.inputPhoto = inputPhoto;
	}

	public String getDepartmentPhoto() {
		return departmentPhoto;
	}

	public void setDepartmentPhoto(String departmentPhoto) {
		this.departmentPhoto = departmentPhoto;
	}

	public void setPensionerId(String pensionerId) {
		this.pensionerId = pensionerId;
	}

	public String getPpoId() {
		return ppoId;
	}

	public void setPpoId(String ppoId) {
		this.ppoId = ppoId;
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

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getPensionerType() {
		return pensionerType;
	}

	public void setPensionerType(String pensionerType) {
		this.pensionerType = pensionerType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public MatchedDetails(String pensionerId, String ppoId, String pensionerName, String dOB, String pensionerType,
			String mobile, String dgStatus, String photoMatchStatus, String photoMatchScore, String status) {
		super();
		this.pensionerId = pensionerId;
		this.ppoId = ppoId;
		this.pensionerName = pensionerName;
		DOB = dOB;
		this.pensionerType = pensionerType;
		this.mobile = mobile;
		this.dgStatus = dgStatus;
		this.photoMatchStatus = photoMatchStatus;
		this.photoMatchScore = photoMatchScore;
		this.status = status;

	}

	public MatchedDetails(String pensionerId, String ppoId, String pensionerName, String dOB, String pensionerType,
			String mobile, String dgStatus, String photoMatchStatus, String photoMatchScore, String status,
			String inputPhoto, String departmentPhoto) {
		super();
		this.pensionerId = pensionerId;
		this.ppoId = ppoId;
		this.pensionerName = pensionerName;
		DOB = dOB;
		this.pensionerType = pensionerType;
		this.mobile = mobile;
		this.dgStatus = dgStatus;
		this.photoMatchStatus = photoMatchStatus;
		this.photoMatchScore = photoMatchScore;
		this.status = status;
		this.inputPhoto = inputPhoto;
		this.departmentPhoto = departmentPhoto;
	}

}
