package com.wissen.tpu.dto;

public class ApproverAuditRequest {

	private String requestId;
	private String pensionerId;
	private String panNo;
	private String pensionerName;
	private String dgStatus;
	private String photoMatchStatus;
	private String photoMatchScore;
	private String status;
	private String approverCode;
	private String approverStatus;
	private String approverName;
	private String department;
	private String reMark;

	
	
	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public String getApproverCode() {
		return approverCode;
	}

	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	@Override
	public String toString() {
		return "ApproverAuditRequest [requestId=" + requestId + ", pensionerId=" + pensionerId + ", panNo=" + panNo
				+ ", pensionerName=" + pensionerName + ", dgStatus=" + dgStatus + ", photoMatchStatus="
				+ photoMatchStatus + ", photoMatchScore=" + photoMatchScore + ", status=" + status + ", approverCode="
				+ approverCode + ", approverStatus=" + approverStatus + ", approverName=" + approverName + "]";
	}

}
