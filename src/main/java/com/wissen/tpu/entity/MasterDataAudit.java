package com.wissen.tpu.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "master_data_audit")
@Entity
public class MasterDataAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String pensionerId;
	private String ppoNo;
	private String pensionerName;
	private String dob;
	private String pensionerType;
	private String panNo;
	private String mobile;
	private String verifierOfficerCode;
	private String verifierOfficerName;
	private String verifierOfficerMobile;
	private String approverOfficerCode;
	private String approverOfficerName;
	private String approverOfficerMobile;
	private String dept;
	private String departmentPhoto;
	private String admin;
	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	@UpdateTimestamp
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getVerifierOfficerCode() {
		return verifierOfficerCode;
	}
	public void setVerifierOfficerCode(String verifierOfficerCode) {
		this.verifierOfficerCode = verifierOfficerCode;
	}
	public String getVerifierOfficerName() {
		return verifierOfficerName;
	}
	public void setVerifierOfficerName(String verifierOfficerName) {
		this.verifierOfficerName = verifierOfficerName;
	}
	public String getVerifierOfficerMobile() {
		return verifierOfficerMobile;
	}
	public void setVerifierOfficerMobile(String verifierOfficerMobile) {
		this.verifierOfficerMobile = verifierOfficerMobile;
	}
	public String getApproverOfficerCode() {
		return approverOfficerCode;
	}
	public void setApproverOfficerCode(String approverOfficerCode) {
		this.approverOfficerCode = approverOfficerCode;
	}
	public String getApproverOfficerName() {
		return approverOfficerName;
	}
	public void setApproverOfficerName(String approverOfficerName) {
		this.approverOfficerName = approverOfficerName;
	}
	public String getApproverOfficerMobile() {
		return approverOfficerMobile;
	}
	public void setApproverOfficerMobile(String approverOfficerMobile) {
		this.approverOfficerMobile = approverOfficerMobile;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDepartmentPhoto() {
		return departmentPhoto;
	}
	public void setDepartmentPhoto(String departmentPhoto) {
		this.departmentPhoto = departmentPhoto;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Override
	public String toString() {
		return "MasterDataAudit [id=" + id + ", pensionerId=" + pensionerId + ", ppoNo=" + ppoNo + ", pensionerName="
				+ pensionerName + ", dob=" + dob + ", pensionerType=" + pensionerType + ", panNo=" + panNo + ", mobile="
				+ mobile + ", verifierOfficerCode=" + verifierOfficerCode + ", verifierOfficerName="
				+ verifierOfficerName + ", verifierOfficerMobile=" + verifierOfficerMobile + ", approverOfficerCode="
				+ approverOfficerCode + ", approverOfficerName=" + approverOfficerName + ", approverOfficerMobile="
				+ approverOfficerMobile + ", dept=" + dept + ", departmentPhoto=" + departmentPhoto + ", admin=" + admin
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
	}
	public MasterDataAudit(Long id, String pensionerId, String ppoNo, String pensionerName, String dob,
			String pensionerType, String panNo, String mobile, String verifierOfficerCode, String verifierOfficerName,
			String verifierOfficerMobile, String approverOfficerCode, String approverOfficerName,
			String approverOfficerMobile, String dept, String departmentPhoto, String admin, LocalDateTime createdDate,
			LocalDateTime modifiedDate) {
		super();
		this.id = id;
		this.pensionerId = pensionerId;
		this.ppoNo = ppoNo;
		this.pensionerName = pensionerName;
		this.dob = dob;
		this.pensionerType = pensionerType;
		this.panNo = panNo;
		this.mobile = mobile;
		this.verifierOfficerCode = verifierOfficerCode;
		this.verifierOfficerName = verifierOfficerName;
		this.verifierOfficerMobile = verifierOfficerMobile;
		this.approverOfficerCode = approverOfficerCode;
		this.approverOfficerName = approverOfficerName;
		this.approverOfficerMobile = approverOfficerMobile;
		this.dept = dept;
		this.departmentPhoto = departmentPhoto;
		this.admin = admin;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
	public MasterDataAudit() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
