package com.wissen.tpu.dto;

public class AdminDetails {

	private String pensionerId;
	private String pensionerName;
	private String pensionerType;
	private String approverCode;
	private String submittedDate;
	private String approvedDate;
	private String DLCStatus;
	private String status;
	private String verifierStatus;
	private String approverStatus;
	private int SLNo;

	public AdminDetails() {

	}

	public String getPensionerId() {
		return pensionerId;
	}

	public void setPensionerId(String pensionerId) {
		this.pensionerId = pensionerId;
	}

	public String getPensionerName() {
		return pensionerName;
	}

	public void setPensionerName(String pensionerName) {
		this.pensionerName = pensionerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVerifierStatus() {
		return verifierStatus;
	}

	public void setVerifierStatus(String verifierStatus) {
		this.verifierStatus = verifierStatus;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public String getPensionerType() {
		return pensionerType;
	}

	public void setPensionerType(String pensionerType) {
		this.pensionerType = pensionerType;
	}

	public String getApproverCode() {
		return approverCode;
	}

	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getDLCStatus() {
		return DLCStatus;
	}

	public void setDLCStatus(String dLCStatus) {
		DLCStatus = dLCStatus;
	}

	public int getSLNo() {
		return SLNo;
	}

	public void setSLNo(int sLNo) {
		SLNo = sLNo;
	}

}
