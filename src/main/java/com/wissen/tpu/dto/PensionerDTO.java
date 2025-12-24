package com.wissen.tpu.dto;

public class PensionerDTO {

	private String id;
	private String pensionerId;
	private String ppoNo;
	private String pensionerName;
	private String dateOfBirth;
	private String pensionerType;
	private String panNo;
	private String mobileNo;
	private String verifyingOfficerCode;
	private String verifyingOfficerName;
	private String verifyingOfficerMobileNo;
	private String approverUnitCode;
	private String approverName;
	private String approverMobile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPensionerId() {
		return pensionerId;
	}

	public void setPensionerId(String pensionerId) {
		this.pensionerId = pensionerId;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPensionerType() {
		return pensionerType;
	}

	public void setPensionerType(String pensionerType) {
		this.pensionerType = pensionerType;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getVerifyingOfficerCode() {
		return verifyingOfficerCode;
	}

	public void setVerifyingOfficerCode(String verifyingOfficerCode) {
		this.verifyingOfficerCode = verifyingOfficerCode;
	}

	public String getVerifyingOfficerName() {
		return verifyingOfficerName;
	}

	public void setVerifyingOfficerName(String verifyingOfficerName) {
		this.verifyingOfficerName = verifyingOfficerName;
	}

	public String getVerifyingOfficerMobileNo() {
		return verifyingOfficerMobileNo;
	}

	public void setVerifyingOfficerMobileNo(String verifyingOfficerMobileNo) {
		this.verifyingOfficerMobileNo = verifyingOfficerMobileNo;
	}

	public String getApproverUnitCode() {
		return approverUnitCode;
	}

	public void setApproverUnitCode(String approverUnitCode) {
		this.approverUnitCode = approverUnitCode;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverMobile() {
		return approverMobile;
	}

	public void setApproverMobile(String approverMobile) {
		this.approverMobile = approverMobile;
	}

	public PensionerDTO(String id, String pensionerId, String ppoNo, String pensionerName, String dateOfBirth,
			String pensionerType, String panNo, String mobileNo, String verifyingOfficerCode,
			String verifyingOfficerName, String verifyingOfficerMobileNo, String approverUnitCode, String approverName,
			String approverMobile) {
		super();
		this.id = id;
		this.pensionerId = pensionerId;
		this.ppoNo = ppoNo;
		this.pensionerName = pensionerName;
		this.dateOfBirth = dateOfBirth;
		this.pensionerType = pensionerType;
		this.panNo = panNo;
		this.mobileNo = mobileNo;
		this.verifyingOfficerCode = verifyingOfficerCode;
		this.verifyingOfficerName = verifyingOfficerName;
		this.verifyingOfficerMobileNo = verifyingOfficerMobileNo;
		this.approverUnitCode = approverUnitCode;
		this.approverName = approverName;
		this.approverMobile = approverMobile;
	}

	@Override
	public String toString() {
		return "PensionerDTO [id=" + id + ", pensionerId=" + pensionerId + ", ppoNo=" + ppoNo + ", pensionerName="
				+ pensionerName + ", dateOfBirth=" + dateOfBirth + ", pensionerType=" + pensionerType + ", panNo="
				+ panNo + ", mobileNo=" + mobileNo + ", verifyingOfficerCode=" + verifyingOfficerCode
				+ ", verifyingOfficerName=" + verifyingOfficerName + ", verifyingOfficerMobileNo="
				+ verifyingOfficerMobileNo + ", approverUnitCode=" + approverUnitCode + ", approverName=" + approverName
				+ ", approverMobile=" + approverMobile + ", photoStatus=]";
	}

}
