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

@Entity
@Table(name = "verifier_audit")
public class VerifierAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestId;
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

	private int isUpdatedReg;

	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	@UpdateTimestamp
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	public int getIsUpdatedReg() {
		return isUpdatedReg;
	}

	public void setIsUpdatedReg(int isUpdatedReg) {
		this.isUpdatedReg = isUpdatedReg;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "VerifierAudit [requestId=" + requestId + ", pensionerId=" + pensionerId + ", panNo=" + panNo
				+ ", pensionerName=" + pensionerName + ", dgStatus=" + dgStatus + ", photoMatchStatus="
				+ photoMatchStatus + ", photoMatchScore=" + photoMatchScore + ", status=" + status + ", verifierCode="
				+ verifierCode + ", verifierStatus=" + verifierStatus + ", verifierName=" + verifierName
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
	}

}
