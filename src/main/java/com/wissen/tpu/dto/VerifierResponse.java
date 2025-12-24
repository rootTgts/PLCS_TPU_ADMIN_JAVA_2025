package com.wissen.tpu.dto;

public class VerifierResponse {
	 private String name;
	    private String department;

	    public VerifierResponse(String name, String department) {
	        this.name = name;
	        this.department = department;
	    }

	    // Getters
	    public String getName() {
	        return name;
	    }

	    public String getDepartment() {
	        return department;
	    }
}
