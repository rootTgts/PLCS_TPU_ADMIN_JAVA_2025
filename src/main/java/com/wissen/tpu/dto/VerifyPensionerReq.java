package com.wissen.tpu.dto;

import lombok.Data;

@Data
public class VerifyPensionerReq {
	private String requestId;
	private String pensionerPpoNo;
	private String pensionerBankNo;
	private String inputPhoto;
	private String dgStatus;
	private String mobileNo;
}
