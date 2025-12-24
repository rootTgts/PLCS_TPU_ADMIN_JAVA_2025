package com.wissen.tpu.entity;

import javax.persistence.*;

@Entity
@Table(name = "admin_user")
public class AdminUser {
	

	@Id
    @Column(name = "username")
    private String code;

    @Column(name = "password")
    private String password;

    @Column(name = "department")
    private String department;

    @Column(name = "role")
    private String role;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public AdminUser(String code, String password, String department, String role) {
		super();
		this.code = code;
		this.password = password;
		this.department = department;
		this.role = role;
	}

	public AdminUser() {
		super();
		// TODO Auto-generated constructor stub
	}

}
