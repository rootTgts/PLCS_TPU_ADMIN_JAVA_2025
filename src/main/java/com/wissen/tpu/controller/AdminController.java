package com.wissen.tpu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.tpu.dto.AdminDetails;
import com.wissen.tpu.dto.LoginResponse;
import com.wissen.tpu.dto.MasterDetails;
import com.wissen.tpu.entity.LoginRequest;
import com.wissen.tpu.entity.AdminUser;
import com.wissen.tpu.security.JwtTokenProvider;
import com.wissen.tpu.service.AdminService;

@RestController
@RequestMapping("/plcstpu/admin")
// /plcstpu/staging/admin  (for staging)
public class AdminController {

	@Autowired
	AdminService service;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@GetMapping("/GetDetails/{department}")
	ResponseEntity<Map<String, String>> GetApprovedCount(@PathVariable("department") String department) {
		Map<String, String> list = service.getCountDetails(department);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

//	@PostMapping("/signin")
//	public ResponseEntity<String> signIn(@RequestBody LoginRequest loginRequest) {
//		String dept = service.authAdmin(loginRequest.getCode(), loginRequest.getPassword());
//		if (dept != null) {
//			return ResponseEntity.ok(dept);
//		} else {
//			return ResponseEntity.ok(null);
//		}
//	}
	
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse admin = service.authenticateAdmin(
                loginRequest.getCode(),
                loginRequest.getPassword()
        );

        String token = jwtTokenProvider.createToken(admin);
        admin.setToken(token);
        return ResponseEntity.ok(admin);
    }

	@GetMapping("/GetApproverCodes/{department}")
	public ResponseEntity<List<String>> GetApproverCodes(@PathVariable("department") String department) {
		List<String> list = service.getApprovesCodes(department);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/CountByDeptAndApproverCode")
	public ResponseEntity<Map<String, String>> getCountByDeptAndApproverCode(@RequestBody Map<String, String> request) {
		String department = request.get("department");
		String approverCode = request.get("approverCode");

		System.err.println(department + " " + approverCode);
		Map<String, String> list = service.getCountDetailsByDeptAndApproverCode(department, approverCode);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/GetDetailsByDept")
	public ResponseEntity<List<AdminDetails>> getDetailsByDepartment(@RequestBody Map<String, String> request) {
		String department = request.get("department");
		String status = request.get("status");

		List<AdminDetails> list = service.getDetailsByDept(department, status);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/GetDetailsByDeptAndApproverCode")
	public ResponseEntity<List<AdminDetails>> getDetailsByDepartmentAndApproverCode(
			@RequestBody Map<String, String> request) {
		String department = request.get("department");
		String status = request.get("status");
		String approverCode = request.get("approverCode");

		List<AdminDetails> list = service.getDetailsByDeptAndApproverCode(department, status, approverCode);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/GetMasterDetails")
	ResponseEntity<MasterDetails> GetMasterDetails(@RequestBody Map<String, String> request) {

		String pensionerId = request.get("pensionerId");
		String adminDept = request.get("adminDept");

		MasterDetails list = service.getMasterDetails(pensionerId, adminDept);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/UpdateMasterDetails")
	public ResponseEntity<String> updateMasterDetails(@RequestBody MasterDetails request) {

		service.updateMasterData(request);
		return ResponseEntity.ok("SUCCESS");

	}
}
