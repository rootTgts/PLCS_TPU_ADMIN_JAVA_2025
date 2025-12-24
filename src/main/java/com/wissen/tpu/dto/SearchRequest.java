package com.wissen.tpu.dto;

import javax.validation.constraints.NotBlank;

public class SearchRequest {

	// private String ppoNo;
	private String pensionerId;
	@NotBlank(message = "The requestId is required.")
	private String requestId;

	private String panNo;

	private String pensionerName;

	@NotBlank(message = "The deptcode is required.")
	private String deptcode;

	public String getPensionerId() {
		return pensionerId;
	}

	public void setPensionerId(String pensionerId) {
		this.pensionerId = pensionerId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public SearchRequest(String pensionerId, String requestId, String panNo, String pensionerName, String deptcode) {
		super();
		this.pensionerId = pensionerId;
		this.requestId = requestId;
		this.panNo = panNo;
		this.pensionerName = pensionerName;
		this.deptcode = deptcode;
	}

	@Override
	public String toString() {
		return "SearchRequest [pensionerId=" + pensionerId + ", requestId=" + requestId + ", panNo=" + panNo
				+ ", pensionerName=" + pensionerName + ", deptcode=" + deptcode + "]";
	}

}
