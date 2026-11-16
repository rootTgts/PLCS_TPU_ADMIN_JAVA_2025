package com.wissen.tpu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wissen.tpu.dto.GetMatchedDetails;
import com.wissen.tpu.dto.LoginResponse;
import com.wissen.tpu.dto.MasterDetails;
import com.wissen.tpu.dto.MatchedDetails;
import com.wissen.tpu.dto.RetypePasswod;
import com.wissen.tpu.dto.VerifierAuditRequest;
import com.wissen.tpu.entity.VerifierAudit;
import com.wissen.tpu.entity.VerifierUser;
import com.wissen.tpu.exception.InvalidCredentialsException;
import com.wissen.tpu.repository.VerifierAuditRepository;
import com.wissen.tpu.repository.VerifierRepository;
import com.wissen.tpu.utils.Utils;

@Service
public class VerifierService {

	private static final Log logger = LogFactory.getLog(VerifierService.class);
	@Autowired
	private VerifierRepository repository;
	@Autowired
	VerifierAuditRepository auditRepo;
	@Autowired
	Utils utils;
	@Autowired
	PasswordEncoder passwordEncoder;

	public List<GetMatchedDetails> getPensionersByVerifyingOfficerCode(String verifyingOfficerCode, String dept) {
		List<Object[]> result = repository.findPensionersByVerifyingOfficerCode(verifyingOfficerCode, dept);

		List<GetMatchedDetails> detailsList = new ArrayList<>();

		for (Object[] row : result) {
			GetMatchedDetails details = new GetMatchedDetails();
			details.setPensionerId(row[0] != null ? row[0].toString() : null);

			details.setPensionerName(row[1] != null ? row[1].toString() : null);

			details.setPensionerType(row[2] != null ? row[2].toString() : null);

			details.setStatus(row[3] != null ? row[3].toString() : null);
			details.setSubmittedDate(row[4] != null ? row[4].toString() : null);

			detailsList.add(details);
		}

		return detailsList;
	}

	public List<MatchedDetails> getPensionersByPensionerId(String pensionerId, String dept,
			String verifyingOfficerCode) {
		List<Object[]> result = repository.findPensionersByPensionerIdAndStatus(pensionerId, dept,
				verifyingOfficerCode);

		List<MatchedDetails> detailsList = new ArrayList<>();

		for (Object[] row : result) {
			MatchedDetails details = new MatchedDetails();
			String penId = row[0] != null ? row[0].toString() : null;
			String depCode = row[9] != null ? row[9].toString() : null;
			String uuid = row[8] != null ? row[8].toString() : null;

			String departmentPhoto = utils.getPensionerImageAsBase64PNG(depCode, "0" + pensionerId);
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64PNG(depCode, pensionerId);
			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(depCode, "0" + pensionerId);

			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(depCode, pensionerId);

			}

			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, "0" + pensionerId);

			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, pensionerId);

			}

			details.setDepartmentPhoto(departmentPhoto);
			String inputPhoto = null;
			try {

				inputPhoto = utils.getImageByUuid(uuid, penId);
			} catch (Exception e) {
			}
			details.setPensionerId(row[0] != null ? row[0].toString() : null);
			details.setPpoId(row[1] != null ? row[1].toString() : null);
			details.setPensionerName(row[2] != null ? row[2].toString() : null);
			details.setMobile(row[3] != null ? row[3].toString() : null);

			details.setPhotoMatchStatus(row[4] != null ? row[4].toString() : null);
			details.setPhotoMatchScore(row[5] != null ? row[5].toString() : null);

			details.setDgStatus(row[6] != null ? row[6].toString() : null);

			details.setStatus(row[7] != null ? row[7].toString() : null);
			// details.setInputPhoto(row[7] != null ? row[7].toString() : null);
			details.setInputPhoto(inputPhoto);
			details.setDepartmentName(row[9] != null ? row[9].toString() : null);

			details.setPensionerType(row[10] != null ? row[10].toString() : null);
			details.setUnitCode(row[11] != null ? row[11].toString() : null);

			details.setSubmittedDate(row[12] != null ? row[12].toString() : null);
			details.setRe_marriage(row[13] != null ? row[13].toString() : null);
			detailsList.add(details);
		}

		return detailsList;
	}

	public void saveVerifierLog(VerifierAuditRequest request) {
		VerifierAudit entity = new VerifierAudit();
		BeanUtils.copyProperties(request, entity);

		String verifierStatus = request.getVerifierStatus();

		int n = repository.updateVerifierStatusByPensionerId(verifierStatus, request.getReMark(),
				request.getPensionerId(), request.getDepartment());
		entity.setIsUpdatedReg(n);
		auditRepo.save(entity);

	}

	public LoginResponse authenticateVerifier(String code, String password)throws InvalidCredentialsException {

	    VerifierUser user = repository.findByCode(code);

	    if (user == null) {
	        throw new InvalidCredentialsException("Invalid code or password");
	    }

	    // ✅ THIS is where password vs DB column is checked
	    if (!password.equals(user.getPassword())) {
	        throw new InvalidCredentialsException("Invalid code or password");
	    }

	    LoginResponse loginResponse = new LoginResponse();
	    BeanUtils.copyProperties(user, loginResponse);

	    return loginResponse;
	}


//	public VerifierUser authenticateVerifierOTP(String otp) {
//		logger.info("Authenticating OTP: " + otp);
//
//		VerifierUser user = repository.findByOtp(otp);
//		if (user == null) {
//			logger.warn("No verifier found for OTP: " + otp);
//			return null;
//		}
//
//		logger.info("OTP verified successfully for verifier: " + user.getCode());
//		return user;
//	}

	public VerifierUser reTypePassword(RetypePasswod request) {

		// 1. Load by code only
		VerifierUser user = repository.findByPasswordAndCode(request.getOldPassword(),request.getCode());
		if (user == null) {
			return null; // user not found
		}

//		// 2. Verify old password using encoder
//		if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
//			return null; // old password incorrect
//		}

		// 3. Encode new password
	//	String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
		user.setPassword(request.getNewPassword());
		user.setPasswordChanged("true");

		return repository.save(user);
	}

	public List<GetMatchedDetails> getPensionersByVerifyingOfficerCodeByVerified(String verifyingOfficerCode,
			String dept) {
		List<Object[]> result = repository.findPensionersByVerifyingOfficerCodeVerified(verifyingOfficerCode, dept);

		List<GetMatchedDetails> detailsList = new ArrayList<>();
		int count = 1;
		for (Object[] row : result) {
			GetMatchedDetails details = new GetMatchedDetails();
			details.setPensionerId(row[0] != null ? row[0].toString() : null);

			details.setPensionerName(row[1] != null ? row[1].toString() : null);

			details.setPensionerType(row[2] != null ? row[2].toString() : null);

			details.setStatus(row[3] != null ? row[3].toString() : null);
			details.setSubmittedDate(row[4] != null ? row[4].toString() : null);

			details.setSLNo(count++);
			detailsList.add(details);
		}

		return detailsList;
	}

	public List<GetMatchedDetails> getPensionersByVerifyingOfficerCodeByVerifierRejected(String verifyingOfficerCode,
			String dept) {
		List<Object[]> result = repository.findPensionersByVerifyingOfficerCodeVerifierRejected(verifyingOfficerCode,
				dept);

		List<GetMatchedDetails> detailsList = new ArrayList<>();
		int count = 1;
		for (Object[] row : result) {
			GetMatchedDetails details = new GetMatchedDetails();
			details.setPensionerId(row[0] != null ? row[0].toString() : null);

			details.setPensionerName(row[1] != null ? row[1].toString() : null);

			details.setPensionerType(row[2] != null ? row[2].toString() : null);

			details.setStatus(row[3] != null ? row[3].toString() : null);
			details.setSubmittedDate(row[4] != null ? row[4].toString() : null);

			details.setSLNo(count++);
			detailsList.add(details);
		}

		return detailsList;
	}

	public List<MatchedDetails> getPensionersByPensionerIdByVerifiedDisplay(String pensionerId, String dept,
			String verifyingOfficerCode) {
		List<Object[]> result = repository.findPensionersByPensionerIdAndStatusVerified(pensionerId, dept,
				verifyingOfficerCode);

		List<MatchedDetails> detailsList = new ArrayList<>();

		for (Object[] row : result) {
			MatchedDetails details = new MatchedDetails();
			String penId = row[0] != null ? row[0].toString() : null;
			String depCode = row[9] != null ? row[9].toString() : null;
			String uuid = row[8] != null ? row[8].toString() : null;

			String departmentPhoto = utils.getPensionerImageAsBase64PNG(depCode, "0" + pensionerId);
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64PNG(depCode, pensionerId);
			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(depCode, "0" + pensionerId);

			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(depCode, pensionerId);

			}

			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, "0" + pensionerId);

			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, pensionerId);

			}

			details.setDepartmentPhoto(departmentPhoto);
			String inputPhoto = null;
			try {

				inputPhoto = utils.getImageByUuid(uuid, penId);
			} catch (Exception e) {
			}
			details.setPensionerId(row[0] != null ? row[0].toString() : null);
			details.setPpoId(row[1] != null ? row[1].toString() : null);
			details.setPensionerName(row[2] != null ? row[2].toString() : null);
			details.setMobile(row[3] != null ? row[3].toString() : null);

			details.setPhotoMatchStatus(row[4] != null ? row[4].toString() : null);
			details.setPhotoMatchScore(row[5] != null ? row[5].toString() : null);

			details.setDgStatus(row[6] != null ? row[6].toString() : null);

			details.setStatus(row[7] != null ? row[7].toString() : null);
			// details.setInputPhoto(row[7] != null ? row[7].toString() : null);
			details.setInputPhoto(inputPhoto);
			details.setDepartmentName(row[9] != null ? row[9].toString() : null);

			details.setPensionerType(row[10] != null ? row[10].toString() : null);
			details.setUnitCode(row[11] != null ? row[11].toString() : null);
			details.setSubmittedDate(row[12] != null ? row[12].toString() : null);
			details.setRe_marriage(row[13] != null ? row[13].toString() : null);
			detailsList.add(details);
		}

		return detailsList;
	}

	public List<MatchedDetails> getPensionersByPensionerIdByVerifierRejectedDisplay(String pensionerId, String dept,
			String verifyingOfficerCode) {
		List<Object[]> result = repository.findPensionersByPensionerIdAndStatusVerifierRejectedDisplay(pensionerId,
				dept, verifyingOfficerCode);

		List<MatchedDetails> detailsList = new ArrayList<>();

		for (Object[] row : result) {
			MatchedDetails details = new MatchedDetails();
			String penId = row[0] != null ? row[0].toString() : null;
			String depCode = row[9] != null ? row[9].toString() : null;
			String uuid = row[8] != null ? row[8].toString() : null;

			String departmentPhoto = utils.getPensionerImageAsBase64PNG(depCode, "0" + pensionerId);
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64PNG(depCode, pensionerId);
			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(depCode, "0" + pensionerId);

			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(depCode, pensionerId);

			}

			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, "0" + pensionerId);

			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, pensionerId);

			}

			details.setDepartmentPhoto(departmentPhoto);
			String inputPhoto = null;
			try {

				inputPhoto = utils.getImageByUuid(uuid, penId);
			} catch (Exception e) {
			}
			details.setPensionerId(row[0] != null ? row[0].toString() : null);
			details.setPpoId(row[1] != null ? row[1].toString() : null);
			details.setPensionerName(row[2] != null ? row[2].toString() : null);
			details.setMobile(row[3] != null ? row[3].toString() : null);

			details.setPhotoMatchStatus(row[4] != null ? row[4].toString() : null);
			details.setPhotoMatchScore(row[5] != null ? row[5].toString() : null);

			details.setDgStatus(row[6] != null ? row[6].toString() : null);

			details.setStatus(row[7] != null ? row[7].toString() : null);
			// details.setInputPhoto(row[7] != null ? row[7].toString() : null);
			details.setInputPhoto(inputPhoto);
			details.setDepartmentName(row[9] != null ? row[9].toString() : null);

			details.setPensionerType(row[10] != null ? row[10].toString() : null);
			details.setUnitCode(row[11] != null ? row[11].toString() : null);
			details.setSubmittedDate(row[12] != null ? row[12].toString() : null);
			details.setRe_marriage(row[13] != null ? row[13].toString() : null);
			detailsList.add(details);
		}

		return detailsList;
	}

	
	public MasterDetails getMasterDetails(String pensionerId, String adminDept) {

		List<Object[]> result = repository.getMasterDetails(pensionerId, adminDept);

		List<MasterDetails> listOfPensioners = result.stream().map(data -> {
			MasterDetails details = new MasterDetails();

			details.setPensionerId(data[0] != null ? data[0].toString() : null);
			details.setPpoNo(data[1] != null ? data[1].toString() : null);
			details.setPensionerName(data[2] != null ? data[2].toString() : null);
			details.setDob(data[3] != null ? data[3].toString() : null);
			details.setPensionerType(data[4] != null ? data[4].toString() : null);
			details.setPanNo(data[5] != null ? data[5].toString() : null);
			details.setMobile(data[6] != null ? data[6].toString() : null);
			details.setVerifierOfficerCode(data[7] != null ? data[7].toString() : null);
			details.setVerifierOfficerName(data[8] != null ? data[8].toString() : null);
			details.setVerifierOfficerMobile(data[9] != null ? data[9].toString() : null);
			details.setApproverOfficerCode(data[10] != null ? data[10].toString() : null);
			details.setApproverOfficerName(data[11] != null ? data[11].toString() : null);
			details.setApproverOfficerMobile(data[12] != null ? data[12].toString() : null);
			details.setDept(data[13] != null ? data[13].toString() : null);

			String departmentPhoto = utils.getPensionerImageAsBase64PNG(adminDept, "0" + pensionerId);
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64PNG(adminDept, pensionerId);
			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(adminDept, "0" + pensionerId);

			}
			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPG(adminDept, pensionerId);

			}

			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(adminDept, pensionerId);

			}

			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(adminDept, "0" + pensionerId);

			}
			details.setDepartmentPhoto(departmentPhoto);
			return details;
		}).collect(Collectors.toList());
		return listOfPensioners.get(0);
	}
}
