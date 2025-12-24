package com.wissen.tpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "verifier_user")
public class VerifierUser {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "code", length = 50)
	private String code;

	@Column(name = "department", length = 100)
	private String department;

	@Column(name = "password", length = 255)
	private String password;

	@Column(name = "mobile")
	private String mobile;

	private String passwordChanged;

	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(String passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "VerifierUser [id=" + id + ", code=" + code + ", department=" + department + ", password=" + password
				+ ", mobile=" + mobile + ", passwordChanged=" + passwordChanged + ", role=" + role + "]";
	}

}
