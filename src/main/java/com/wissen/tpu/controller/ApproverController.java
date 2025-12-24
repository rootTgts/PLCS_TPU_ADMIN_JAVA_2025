package com.wissen.tpu.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.tpu.dto.ApproverAuditRequest;
import com.wissen.tpu.dto.GetMatchedDetails;
import com.wissen.tpu.dto.LoginResponse;
import com.wissen.tpu.dto.MasterDetails;
import com.wissen.tpu.dto.MatchedDetails;
import com.wissen.tpu.dto.RetypePasswod;
import com.wissen.tpu.entity.ApproverUser;
import com.wissen.tpu.entity.LoginRequest;
import com.wissen.tpu.entity.VerifierUser;
import com.wissen.tpu.security.JwtTokenProvider;
import com.wissen.tpu.service.ApproverService;
import com.wissen.tpu.service.VerifierService;

@RestController
@RequestMapping("/plcstpu/approver")
public class ApproverController {
	@Autowired
	ApproverService service;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	private static final Log logger = LogFactory.getLog(VerifierController.class);

	@PostMapping("/signin")
	public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginRequest) {
		LoginResponse approver = service.authenticateApprover(loginRequest.getCode(), loginRequest.getPassword());
		String token = jwtTokenProvider.createToken(approver);
		approver.setToken(token);

		return ResponseEntity.ok(approver);
	}

	@GetMapping("/authOTP/{otp}")
	public ResponseEntity<String> authenticateApproverOTP(@PathVariable("otp") String otp) {
		ApproverUser verifier = service.authenticateApproverOTP(otp);
		if (verifier != null) {
			return ResponseEntity.status(HttpStatus.OK).body(verifier.getCode());// Sign-in successful, return true
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid OTP"); // Sign-in failed, return false
		}
	}

	@GetMapping("/view")
	public ResponseEntity<List<GetMatchedDetails>> getPensionersByApproverOfficerCode(@RequestParam("dept") String dept,
			@RequestParam("approverCode") String approverOfficerCode) {

		logger.info("request from t-app log ==>> Approver is " + approverOfficerCode + " department is " + dept);
		List<GetMatchedDetails> pensioners = service.getPensionersByApproverOfficerCode(approverOfficerCode, dept);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/display")
	public ResponseEntity<List<MatchedDetails>> getPensionersByPensionerId(
			@RequestParam("pensionerId") String pensionerId, @RequestParam("dept") String dept) {
		logger.info("request from t-app log ==>> pensionerId is " + pensionerId + " department is " + dept);
		List<MatchedDetails> pensioners = service.getPensionersByPensionerId(pensionerId, dept);
		return ResponseEntity.ok(pensioners);
	}

	@PostMapping("/SaveAuditAction")
	public ResponseEntity<String> saveApproverDetails(@RequestBody ApproverAuditRequest request) {
		service.saveApproverLog(request);
		return ResponseEntity.ok("Succesfully");

	}

	@PostMapping("/password-change")
	public ResponseEntity<String> changePassword(@RequestBody RetypePasswod request) {

		// Validate if new password matches confirm password
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			return ResponseEntity.badRequest().body("New password and confirm password do not match.");
		}

		// Call service method to handle password change
		ApproverUser response = service.reTypePassword(request);

		// If response is not null, password change is successful
		if (response != null) {
			return ResponseEntity.ok(response.getCode());
		}

		// If the response is null, return an error
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Failed to change password. Please try again later.");
	}

	@GetMapping("/approved-view")
	public ResponseEntity<List<GetMatchedDetails>> getPensionersByApproverOfficerCodeByApproved(
			@RequestParam("dept") String dept, @RequestParam("approverCode") String approverOfficerCode) {
		logger.info("request from t-app log ==>> Approver is " + approverOfficerCode + " department is " + dept);
		List<GetMatchedDetails> pensioners = service.getPensionersByApproverOfficerCodeByApproved(approverOfficerCode,
				dept);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/rejected-view")
	public ResponseEntity<List<GetMatchedDetails>> getPensionersByApproverOfficerCodeByRejected(
			@RequestParam("dept") String dept, @RequestParam("approverCode") String approverOfficerCode) {
		logger.info("request from t-app log ==>> Approver is " + approverOfficerCode + " department is " + dept);
		List<GetMatchedDetails> pensioners = service.getPensionersByApproverOfficerCodeByRejected(approverOfficerCode,
				dept);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/approved-display")
	public ResponseEntity<List<MatchedDetails>> getPensionersByPensionerIdByApproved(
			@RequestParam("pensionerId") String pensionerId, @RequestParam("dept") String dept) {
		logger.info("request from t-app log ==>> Approver is " + pensionerId + " department is " + dept);
		List<MatchedDetails> pensioners = service.getPensionersByPensionerIdByApproved(pensionerId, dept);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/rejected-display")
	public ResponseEntity<List<MatchedDetails>> getPensionersByPensionerIdByRejected(
			@RequestParam("pensionerId") String pensionerId, @RequestParam("dept") String dept) {
		logger.info("request from t-app log ==>> Approver is " + pensionerId + " department is " + dept);
		List<MatchedDetails> pensioners = service.getPensionersByPensionerIdByRejected(pensionerId, dept);
		return ResponseEntity.ok(pensioners);
	}

	@PostMapping("/GetMasterDetails")
	public ResponseEntity<MasterDetails> GetMasterDetails(@RequestBody Map<String, String> request) {

		String pensionerId = request.get("pensionerId");
		String adminDept = request.get("adminDept");

		MasterDetails list = service.getMasterDetails(pensionerId, adminDept);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}
