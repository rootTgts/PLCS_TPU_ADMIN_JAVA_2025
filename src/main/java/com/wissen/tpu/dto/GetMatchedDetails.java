package com.wissen.tpu.dto;

public class GetMatchedDetails {
	private Integer SLNo;
	private String pensionerId;
	private String pensionerName;
	private String pensionerType;
	private String status;
	private String submittedDate;
	private String approvedDate;

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

	public String getPensionerType() {
		return pensionerType;
	}

	public void setPensionerType(String pensionerType) {
		this.pensionerType = pensionerType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSLNo() {
		return SLNo;
	}

	public void setSLNo(Integer sLNo) {
		SLNo = sLNo;
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

	@Override
	public String toString() {
		return "GetMatchedDetails [SLNo=" + SLNo + ", pensionerId=" + pensionerId + ", pensionerName=" + pensionerName
				+ ", pensionerType=" + pensionerType + ", status=" + status + ", submittedDate=" + submittedDate
				+ ", approvedDate=" + approvedDate + "]";
	}

}
