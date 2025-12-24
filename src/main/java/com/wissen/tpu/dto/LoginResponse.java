package com.wissen.tpu.dto;

public class LoginResponse {

	private String code;
	private String department;
	private String role;
	private String token;
	private String passwordChanged;

	public LoginResponse() {

	}

	public String getPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(String passwordChanged) {
		this.passwordChanged = passwordChanged;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginResponse(String code, String department, String role, String token) {
		this.code = code;
		this.department = department;
		this.role = role;
		this.token = token;
	}

}
