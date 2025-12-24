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

import com.wissen.tpu.dto.GetMatchedDetails;
import com.wissen.tpu.dto.LoginResponse;
import com.wissen.tpu.dto.MasterDetails;
import com.wissen.tpu.dto.MatchedDetails;
import com.wissen.tpu.dto.RetypePasswod;
import com.wissen.tpu.dto.VerifierAuditRequest;
import com.wissen.tpu.entity.LoginRequest;
import com.wissen.tpu.entity.VerifierUser;
import com.wissen.tpu.exception.InvalidCredentialsException;
import com.wissen.tpu.security.JwtTokenProvider;
import com.wissen.tpu.service.VerifierService;

/*@CrossOrigin(origins = "*")*/
@RestController
@RequestMapping("/plcstpu/verifier")
public class VerifierController {

	private static final Log logger = LogFactory.getLog(VerifierController.class);
	@Autowired
	VerifierService service;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginRequest) {

	    // If credentials are wrong, this will THROW InvalidCredentialsException
	    LoginResponse verifier = service.authenticateVerifier(
	            loginRequest.getCode(),
	            loginRequest.getPassword()
	    );

	    // If we reach here, auth succeeded
	    String token = jwtTokenProvider.createToken(verifier);
	    verifier.setToken(token);

	    return ResponseEntity.ok(verifier);
	}


	@GetMapping("/view")
	public ResponseEntity<List<GetMatchedDetails>> getPensionersByVerifyingOfficerCode(
			@RequestParam("dept") String dept, @RequestParam("verifierCode") String verifyingOfficerCode) {

		logger.info("request from t-app log ==>> verifier is " + verifyingOfficerCode + " department is " + dept);
		List<GetMatchedDetails> pensioners = service.getPensionersByVerifyingOfficerCode(verifyingOfficerCode, dept);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/display")
	public ResponseEntity<List<MatchedDetails>> getPensionersByPensionerId(@RequestParam("dept") String dept,
			@RequestParam("pensionerId") String pensionerId,
			@RequestParam("verifierCode") String verifyingOfficerCode) {
		logger.info("request from t-app log ==>> pensioner id is " + pensionerId + " department is " + dept
				+ "verifyingOfficerCode is  " + verifyingOfficerCode);
		List<MatchedDetails> pensioners = service.getPensionersByPensionerId(pensionerId, dept, verifyingOfficerCode);
		return ResponseEntity.ok(pensioners);
	}

	@PostMapping("/SaveAuditAction")
	public ResponseEntity<String> saveVerifierDetails(@RequestBody VerifierAuditRequest request) {
		service.saveVerifierLog(request);
		return ResponseEntity.ok("Succesfully");
	}

	@PostMapping("/password-change")
	public ResponseEntity<String> changePassword(@RequestBody RetypePasswod request) {
		// Validate if new password matches confirm password
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			return ResponseEntity.badRequest().body("New password and confirm password do not match.");
		}
 
		// Call service method to handle password change
		VerifierUser response = service.reTypePassword(request);

		// If response is not null, password change is successful
		if (response != null) {
			return ResponseEntity.ok(response.getCode());
		}

		// If the response is null, return an error
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Failed to change password. Please try again later.");
	}

	@GetMapping("/View-Verified")
	public ResponseEntity<List<GetMatchedDetails>> getPensionersByVerifyingOfficerCodeByVerified(
			@RequestParam("dept") String dept, @RequestParam("verifierCode") String verifyingOfficerCode) {

		logger.info("request from t-app log ==>> verifier is " + verifyingOfficerCode + " department is " + dept);
		List<GetMatchedDetails> pensioners = service.getPensionersByVerifyingOfficerCodeByVerified(verifyingOfficerCode,
				dept);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/View-Rejected")
	public ResponseEntity<List<GetMatchedDetails>> getPensionersByVerifyingOfficerCodeByRejected(
			@RequestParam("dept") String dept, @RequestParam("verifierCode") String verifyingOfficerCode) {

		logger.info("request from t-app log ==>> verifier is " + verifyingOfficerCode + " department is " + dept);
		List<GetMatchedDetails> pensioners = service
				.getPensionersByVerifyingOfficerCodeByVerifierRejected(verifyingOfficerCode, dept);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/Display-Verified")
	public ResponseEntity<List<MatchedDetails>> getPensionersByPensionerIdByVerified(@RequestParam("dept") String dept,
			@RequestParam("pensionerId") String pensionerId,
			@RequestParam("verifierCode") String verifyingOfficerCode) {

		logger.info("Inside getPensionersByPensionerIdByVerified method  ");
		logger.info("request from t-app log ==>> pensioner id is " + pensionerId + " department is " + dept
				+ " verifyingOfficerCode is  " + verifyingOfficerCode);
		List<MatchedDetails> pensioners = service.getPensionersByPensionerIdByVerifiedDisplay(pensionerId, dept,
				verifyingOfficerCode);
		return ResponseEntity.ok(pensioners);
	}

	@GetMapping("/Display-Rejected")
	public ResponseEntity<List<MatchedDetails>> getPensionersByPensionerIdByVerifierRejected(
			@RequestParam("dept") String dept, @RequestParam("pensionerId") String pensionerId,
			@RequestParam("verifierCode") String verifyingOfficerCode) {

		logger.info("Inside getPensionersByPensionerIdByVerified method  ");
		logger.info("request from t-app log ==>> pensioner id is " + pensionerId + " department is " + dept
				+ " verifyingOfficerCode is  " + verifyingOfficerCode);
		List<MatchedDetails> pensioners = service.getPensionersByPensionerIdByVerifierRejectedDisplay(pensionerId, dept,
				verifyingOfficerCode);
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
