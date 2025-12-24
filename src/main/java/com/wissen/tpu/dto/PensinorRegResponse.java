package com.wissen.tpu.dto;

public class PensinorRegResponse {

	private String photoMatchStatus;
	private String pensionerId;
	private String inputPhoto;
	private String departmentPhoto;
	private String dgMatchStatus;

	public String getPhotoMatchStatus() {
		return photoMatchStatus;
	}

	public void setPhotoMatchStatus(String photoMatchStatus) {
		this.photoMatchStatus = photoMatchStatus;
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

	public String getDepartmentPhoto() {
		return departmentPhoto;
	}

	public void setDepartmentPhoto(String departmentPhoto) {
		this.departmentPhoto = departmentPhoto;
	}

	public String getDgMatchStatus() {
		return dgMatchStatus;
	}

	public void setDgMatchStatus(String dgMatchStatus) {
		this.dgMatchStatus = dgMatchStatus;
	}

}
