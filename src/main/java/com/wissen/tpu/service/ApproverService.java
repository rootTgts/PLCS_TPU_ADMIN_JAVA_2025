package com.wissen.tpu.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissen.tpu.dto.ApproverAuditRequest;
import com.wissen.tpu.dto.GetMatchedDetails;
import com.wissen.tpu.dto.LoginResponse;
import com.wissen.tpu.dto.MasterDetails;
import com.wissen.tpu.dto.MatchedDetails;
import com.wissen.tpu.dto.RetypePasswod;
import com.wissen.tpu.entity.ApproverAudit;
import com.wissen.tpu.entity.ApproverUser;
import com.wissen.tpu.exception.InvalidCredentialsException;
import com.wissen.tpu.repository.ApproverAuditRepository;
import com.wissen.tpu.repository.ApproverRepository;
import com.wissen.tpu.utils.Utils;

@Service
public class ApproverService {
	private static final Log logger = LogFactory.getLog(VerifierService.class);
	@Autowired
	private ApproverRepository repository;
	@Autowired
	ApproverAuditRepository auditRepo;
	@Autowired
	Utils utils;
	 
	@Autowired
	ApproverAuditRepository approverAuditRepository;

	public List<GetMatchedDetails> getPensionersByApproverOfficerCode(String approverOfficerCode, String approverDept) {

		List<Object[]> result = repository.findPensionersByApproverOfficerCodeAndDept(approverOfficerCode,
				approverDept);

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

	public List<MatchedDetails> getPensionersByPensionerId(String pensionerId, String dept) {

		List<Object[]> result = repository.findPensionersByPensionerIdAndStatus(pensionerId, dept);

		List<MatchedDetails> detailsList = new ArrayList<>();

		for (Object[] row : result) {

			MatchedDetails details = new MatchedDetails();

			String penId = get(row, 0);
			String depCode = get(row, 9);
			String uuid = get(row, 8);

			// Get department photo (optimized)
			String departmentPhoto = fetchDepartmentPhoto(depCode, pensionerId);
			details.setDepartmentPhoto(departmentPhoto);

			// Get input photo
			String inputPhoto = null;
			try {
				inputPhoto = utils.getImageByUuid(uuid, penId);
			} catch (Exception e) {
				System.err.println("Failed fetching input image: " + e.getMessage());
			}

			// Populate fields
			details.setPensionerId(get(row, 0));
			details.setPpoId(get(row, 1));
			details.setPensionerName(get(row, 2));
			details.setMobile(get(row, 3));
			details.setPhotoMatchStatus(get(row, 4));
			details.setPhotoMatchScore(get(row, 5));
			details.setDgStatus(get(row, 6));
			details.setStatus(get(row, 7));
			details.setInputPhoto(inputPhoto);
			details.setDepartmentName(get(row, 9));
			details.setSubmittedDate(get(row, 12));

			detailsList.add(details);
		}

		return detailsList;
	}

	/**
	 * Safely get a value from an Object[] row.
	 */
	private String get(Object[] row, int index) {
		return row[index] != null ? row[index].toString() : null;
	}

	/**
	 * Optimized department photo fetching for PNG, JPG, JPEG with both "0" prefix
	 * and without prefix.
	 */
	private String fetchDepartmentPhoto(String depCode, String pensionerId) {

		String[] prefixes = { "0", "" };
		String[] formats = { "PNG", "JPG", "JPEG" };

		for (String format : formats) {
			for (String prefix : prefixes) {

				String id = prefix + pensionerId;
				String photo = null;

				switch (format) {
				case "PNG":
					photo = utils.getPensionerImageAsBase64PNG(depCode, id);
					break;

				case "JPG":
					photo = utils.getPensionerImageAsBase64JPG(depCode, id);
					break;

				case "JPEG":
					photo = utils.getPensionerImageAsBase64JPEG(depCode, id);
					break;
				}

				if (photo != null) {
					return photo;
				}
			}
		}

		return null;
	}

	public ApproverUser authenticateApproverOTP(String otp) {

		logger.info("Authenticating OTP: " + otp);

		ApproverUser user = repository.findByOtp(otp);
		if (user == null) {
			logger.warn("No verifier found for OTP: " + otp);
			return null;
		}

		logger.info("OTP verified successfully for verifier: " + user.getCode());
		return user;
	}
	
	public LoginResponse authenticateApprover(String code, String password)throws InvalidCredentialsException {

		ApproverUser user = repository.findByCode(code);

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

//	public ApproverUser authenticateApprover(String code, String password) {
//		logger.info("Authenticating verifier with code: " + code);
//
//		ApproverUser user = repository.findByCodeAndPassword(code, password);
//
//		if (user != null) {
//			logger.info("Verifier authenticated. Generating OTP...");
//			String otp = generateOTP();
//			// otpGenerator.generateOTP(user.getMobile(), otp);
//
//			user.setOtp(otp);
//			repository.save(user);
//
//			logger.info("OTP generated and sent to verifier: " + user.getMobile());
//		} else {
//			logger.warn("Authentication failed for verifier with code: " + code);
//		}
//		return user;
//	}

	private static String generateOTP() {
		SecureRandom secureRandom = new SecureRandom();
		int otp = 100000 + secureRandom.nextInt(900000); // Generates a number between 100000 and 999999
		return String.valueOf(otp);
	}

	public void saveApproverLog(ApproverAuditRequest request) {
		ApproverAudit entity = new ApproverAudit();
		BeanUtils.copyProperties(request, entity);

		String approverStatus = request.getApproverStatus();
		int n = repository.updateApproverStatusByPensionerId(approverStatus, request.getReMark(),
				request.getPensionerId(), request.getDepartment());
		auditRepo.save(entity);
	}

	public ApproverUser reTypePassword(RetypePasswod request) {
		ApproverUser response = repository.findByPasswordAndCode(request.getOldPassword(), request.getCode());
		if (response != null) {
			response.setPassword(request.getNewPassword());
			response.setPasswordChanged("true");
			return repository.save(response);
		}
		return response;
	}

	public List<GetMatchedDetails> getPensionersByApproverOfficerCodeByApproved(String approverOfficerCode,
			String approverDept) {

		List<Object[]> result = repository.findPensionersByApproverOfficerCodeAndDeptByApproved(approverOfficerCode,
				approverDept);

		// String approverdTime=
		// approverAuditRepository.findCreatedDateByPensionerIdAndDepartment(approverOfficerCode,
		// approverDept);

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

	public List<GetMatchedDetails> getPensionersByApproverOfficerCodeByRejected(String approverOfficerCode,
			String approverDept) {

		List<Object[]> result = repository.findPensionersByApproverOfficerCodeAndDeptByRejected(approverOfficerCode,
				approverDept);

		// String approverdTime=
		// approverAuditRepository.findCreatedDateByPensionerIdAndDepartment(approverOfficerCode,
		// approverDept);

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

	public List<MatchedDetails> getPensionersByPensionerIdByApproved(String pensionerId, String dept) {
		List<Object[]> result = repository.findPensionersByPensionerIdAndStatusByApproved(pensionerId, dept);

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
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, pensionerId);

			}

			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, "0" + pensionerId);

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
			details.setSubmittedDate(row[12] != null ? row[12].toString() : null);

			detailsList.add(details);
		}

		return detailsList;
	}

	public List<MatchedDetails> getPensionersByPensionerIdByRejected(String pensionerId, String dept) {
		List<Object[]> result = repository.findPensionersByPensionerIdAndStatusByRejected(pensionerId, dept);

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
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, pensionerId);

			}

			if (departmentPhoto == null) {
				departmentPhoto = utils.getPensionerImageAsBase64JPEG(depCode, "0" + pensionerId);

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

			details.setSubmittedDate(row[12] != null ? row[12].toString() : null);
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
