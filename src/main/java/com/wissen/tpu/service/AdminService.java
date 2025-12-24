package com.wissen.tpu.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wissen.tpu.dto.AdminDetails;
import com.wissen.tpu.dto.LoginResponse;
import com.wissen.tpu.dto.MasterDetails;
import com.wissen.tpu.entity.AdminUser;
import com.wissen.tpu.entity.MasterDataAudit;
import com.wissen.tpu.repository.AdminRepository;
import com.wissen.tpu.repository.AuditRepository;
import com.wissen.tpu.utils.Utils;

@Service
public class AdminService {

	private AdminRepository repository;
	@Autowired
	Utils utils;
	@Autowired
	AuditRepository auditRepository;

	AdminService(AdminRepository repo) {
		repository = repo;
	}

	private List<AdminDetails> getApprovedCount(String department) {

		List<Object[]> result = repository.getPensionerDetails(department);

		List<AdminDetails> listOfPensioners = result.stream().map(data -> {
			AdminDetails details = new AdminDetails();
			details.setPensionerId(data[0] != null ? data[0].toString() : null);
			details.setPensionerName(data[1] != null ? data[1].toString() : null);
			details.setStatus(data[2] != null ? data[2].toString() : null);
			details.setVerifierStatus(data[3] != null ? data[3].toString() : null);
			details.setApproverStatus(data[4] != null ? data[4].toString() : null);
			return details;
		}).collect(Collectors.toList());

		return listOfPensioners;

	}

	public List<AdminDetails> getApprovedCountAndApproverCode(String department, String approverCode) {

		List<Object[]> result = repository.getPensionerDetailsByDeptAndApproverCode(department, approverCode);

		List<AdminDetails> listOfPensioners = result.stream().map(data -> {
			AdminDetails details = new AdminDetails();

			String pensionerId = data[0] != null ? data[0].toString() : null;

			details.setPensionerId(pensionerId);
			details.setPensionerName(data[1] != null ? data[1].toString() : null);
			details.setStatus(data[2] != null ? data[2].toString() : null);
			details.setVerifierStatus(data[3] != null ? data[3].toString() : null);
			details.setApproverStatus(data[4] != null ? data[4].toString() : null);

			return details;
		}).collect(Collectors.toList());

		return listOfPensioners;

	}

	public Map<String, String> getCountDetails(String department) {

		List<AdminDetails> result = getApprovedCount(department);
		Map<String, String> map = new HashMap<>();
		long approverCount = result.stream().filter(data -> "Approved".equalsIgnoreCase(data.getApproverStatus()))
				.count();
		long submittedCount = result.stream().filter(data -> "Registered".equalsIgnoreCase(data.getStatus())).count();
		long rejectedCount = result.stream().filter(data -> "Rejected".equalsIgnoreCase(data.getApproverStatus()))
				.count();
		BigInteger downloadCount = getDLCDownloadCount(department);
		BigInteger totalCount = getTotalCountbyDept(department);

		map.put("totalCount", String.valueOf(totalCount));
		map.put("submittedCount", String.valueOf(submittedCount));
		map.put("approverCount", String.valueOf(approverCount));

		long pendingCount = submittedCount - (approverCount + rejectedCount);
		map.put("pendingCount", String.valueOf(pendingCount));
		map.put("rejectedCount", String.valueOf(rejectedCount));
		map.put("downloadCount", String.valueOf(downloadCount));
		return map;

	}

	public Map<String, String> getCountDetailsByDeptAndApproverCode(String department, String approverCode) {

		List<AdminDetails> result = getApprovedCountAndApproverCode(department, approverCode);
		Map<String, String> map = new HashMap<>();
		long approverCount = result.stream().filter(data -> "Approved".equalsIgnoreCase(data.getApproverStatus()))
				.count();
		long submittedCount = result.stream().filter(data -> "Registered".equalsIgnoreCase(data.getStatus())).count();
		long rejectedCount = result.stream().filter(data -> "Rejected".equalsIgnoreCase(data.getApproverStatus()))
				.count();
		BigInteger downloadCount = getDLCDownloadCount(department);
		BigInteger totalCount = getTotalCountbyDeptAndApproverCode(department, approverCode);

		map.put("totalCount", String.valueOf(totalCount));
		map.put("submittedCount", String.valueOf(submittedCount));
		map.put("approverCount", String.valueOf(approverCount));
		map.put("pendingCount", String.valueOf(submittedCount - approverCount - rejectedCount));
		map.put("rejectedCount", String.valueOf(rejectedCount));
		map.put("downloadCount", String.valueOf(downloadCount));
		return map;

	}

	private BigInteger getDLCDownloadCount(String department) {
		return repository.getDLCDownloadCount(department);

	}

	private BigInteger getTotalCountbyDept(String department) {
		return repository.getTotalCount(department);

	}

	private BigInteger getTotalCountbyDeptAndApproverCode(String department, String approverCode) {
		return repository.getTotalCount(department, approverCode);

	}

//	public String authAdmin(String username, String password) {
//		return repository.authAdmin(username, password);
//
//	}
	
    public LoginResponse authenticateAdmin(String code, String password) throws Exception {
        AdminUser user = repository.findAdminByUsername(code);

        if (user == null) {
            throw new Exception("Invalid username or password");
        }

        if (!password.equals(user.getPassword())) {
            throw new Exception("Invalid username or password");
        }

        LoginResponse response = new LoginResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }


	public List<String> getApprovesCodes(String department) {
		return repository.getApproverList(department);
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

//	   @Transactional
//	    public MasterDetails updateMasterData(MasterDetails request) {
//
//	        int updated = repository.updateMasterDetails(request);
//
//	        if (updated == 0) {
//	            throw new IllegalStateException(
//	                    "Update failed. No record found for pensioner_fp_id: " );
//	        }
//
//	        MasterDetails response = getMasterDetails(request.getPensionerId(), request.getDept());
//
//	        if (response == null) {
//	            throw new IllegalStateException(
//	                    "Update succeeded but no data found for pensionerId: " 
//	                    + request.getPensionerId());
//	        }
//
//	        return response;
//	    }
//	
	public List<AdminDetails> getDetailsByDept(String department, String status) {

		if ("Approved".equalsIgnoreCase(status) || "Rejected".equalsIgnoreCase(status)) {

			List<Object[]> result = repository.getDetailsByDeptAndByApproverStatus(department, status);

			List<AdminDetails> listOfPensioners = result.stream().map(data -> {
				AdminDetails details = new AdminDetails();

				String pensionerId = data[0] != null ? data[0].toString() : null;
				details.setPensionerId(pensionerId);
				details.setPensionerName(data[1] != null ? data[1].toString() : null);
				details.setPensionerType(data[2] != null ? data[2].toString() : null);
				details.setApproverCode(data[3] != null ? data[3].toString() : null);
				details.setSubmittedDate(data[4] != null ? data[4].toString() : null);
				details.setVerifierStatus(data[5] != null ? data[5].toString() : null);
				details.setApproverStatus(data[6] != null ? data[6].toString() : null);

				// Fetch additional fields from repository
				String approvedDate = repository.getSubmittedDateInApproverAuditByPensionerIdAndDept(pensionerId,
						department);
				details.setApprovedDate(approvedDate);

				String res = repository.getStatusInStatusEntityByPensionerIdAndDept(pensionerId, department);
				details.setDLCStatus("Approved".equalsIgnoreCase(res) ? "Downloaded" : "Pending");

				return details;
			}).collect(Collectors.toList());

			int a = 1;

			listOfPensioners.forEach(data -> {
				if ("Downloaded".equalsIgnoreCase(data.getDLCStatus())) {
					data.setStatus("Downloaded");
				} else if ("Approved".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Approved");
				} else if ("Rejected".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Rejected");
				} else {
					data.setStatus(data.getVerifierStatus());
				}
			});

			listOfPensioners = listOfPensioners.stream().collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AdminDetails::getPensionerId) // Ensure

					)), ArrayList::new // Convert TreeSet back to ArrayList
			));

			for (AdminDetails details : listOfPensioners) {
				details.setSLNo(a++);
			}

			return listOfPensioners;

		} else if ("Pending".equalsIgnoreCase(status)) {

			List<Object[]> result = repository.getDetailsByDeptAndByPending(department);

			List<AdminDetails> listOfPensioners = result.stream().map(data -> {
				AdminDetails details = new AdminDetails();

				String pensionerId = data[0] != null ? data[0].toString() : null;
				details.setPensionerId(pensionerId);
				details.setPensionerName(data[1] != null ? data[1].toString() : null);
				details.setPensionerType(data[2] != null ? data[2].toString() : null);
				details.setApproverCode(data[3] != null ? data[3].toString() : null);
				details.setSubmittedDate(data[4] != null ? data[4].toString() : null);
				details.setVerifierStatus(data[5] != null ? data[5].toString() : null);
				details.setApproverStatus(data[6] != null ? data[6].toString() : null);

				// details.setSLNo(0);
				// Fetch additional fields from repository
//				String approvedDate = repository.getSubmittedDateInApproverAuditByPensionerIdAndDept(pensionerId,
//						department);
//				details.setApprovedDate(approvedDate);
//
//				String res = repository.getStatusInStatusEntityByPensionerIdAndDept(pensionerId, department);
//				details.setDLCStatus("Approved".equalsIgnoreCase(res) ? "Downloaded" : "Pending");

				return details;
			}).collect(Collectors.toList());

			listOfPensioners = listOfPensioners.stream().collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AdminDetails::getPensionerId) // Ensure

					)), ArrayList::new // Convert TreeSet back to ArrayList
			));
			int a = 1;
			for (AdminDetails details : listOfPensioners) {
				details.setSLNo(a++);
			}

			return listOfPensioners;

		} else if ("Submitted".equalsIgnoreCase(status)) {
			List<Object[]> result = repository.getDetailsByDeptAndBySubmitted(department);

			List<AdminDetails> listOfPensioners = result.stream().map(data -> {
				AdminDetails details = new AdminDetails();

				String pensionerId = data[0] != null ? data[0].toString() : null;
				details.setPensionerId(pensionerId);
				details.setPensionerName(data[1] != null ? data[1].toString() : null);
				details.setPensionerType(data[2] != null ? data[2].toString() : null);
				details.setApproverCode(data[3] != null ? data[3].toString() : null);
				details.setSubmittedDate(data[4] != null ? data[4].toString() : null);
				details.setVerifierStatus(data[5] != null ? data[5].toString() : null);
				details.setApproverStatus(data[6] != null ? data[6].toString() : null);

				// Fetch additional fields from repository
				String approvedDate = repository.getSubmittedDateInApproverAuditByPensionerIdAndDept(pensionerId,
						department);
				details.setApprovedDate(approvedDate);

				String res = repository.getStatusInStatusEntityByPensionerIdAndDept(pensionerId, department);
				details.setDLCStatus("Approved".equalsIgnoreCase(res) ? "Downloaded" : "Pending");

				return details;
			}).collect(Collectors.toList());
			int a = 1;

			listOfPensioners.forEach(data -> {
				if ("Downloaded".equalsIgnoreCase(data.getDLCStatus())) {
					data.setStatus("Downloaded");
				} else if ("Approved".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Approved");
				} else if ("Rejected".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Rejected");
				} else if ("Null".equalsIgnoreCase(data.getApproverStatus())
						&& "Verified".equalsIgnoreCase(data.getVerifierStatus())) {
					data.setStatus(data.getVerifierStatus());
				} else {

					data.setStatus("Submitted");
				}
			});
			listOfPensioners = listOfPensioners.stream().collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AdminDetails::getPensionerId) // Ensure

					)), ArrayList::new // Convert TreeSet back to ArrayList
			));

			for (AdminDetails details : listOfPensioners) {
				details.setSLNo(a++);
			}

			return listOfPensioners;
		}
		return null;

	}

	@Cacheable(value = "adminDetailsCache", key = "#department + '_' + #status + '_' + #approverCode")
	public List<AdminDetails> getDetailsByDeptAndApproverCode(String department, String status, String approverCode) {

		if ("Approved".equalsIgnoreCase(status) || "Rejected".equalsIgnoreCase(status)) {

			List<Object[]> result = repository.getDetailsByDeptAndByApproverStatusAndApproverCode(department, status,
					approverCode);

			List<AdminDetails> listOfPensioners = result.stream().map(data -> {
				AdminDetails details = new AdminDetails();

				String pensionerId = data[0] != null ? data[0].toString() : null;
				details.setPensionerId(pensionerId);
				details.setPensionerName(data[1] != null ? data[1].toString() : null);
				details.setPensionerType(data[2] != null ? data[2].toString() : null);
				details.setApproverCode(data[3] != null ? data[3].toString() : null);
				details.setSubmittedDate(data[4] != null ? data[4].toString() : null);
				details.setVerifierStatus(data[5] != null ? data[5].toString() : null);
				details.setApproverStatus(data[6] != null ? data[6].toString() : null);

				// Fetch additional fields from repository
				String approvedDate = repository.getSubmittedDateInApproverAuditByPensionerIdAndDept(pensionerId,
						department);
				details.setApprovedDate(approvedDate);

				String res = repository.getStatusInStatusEntityByPensionerIdAndDept(pensionerId, department);
				details.setDLCStatus("Approved".equalsIgnoreCase(res) ? "Downloaded" : "Pending");

				return details;
			}).collect(Collectors.toList());

			listOfPensioners.forEach(data -> {
				if ("Downloaded".equalsIgnoreCase(data.getDLCStatus())) {
					data.setStatus("Downloaded");
				} else if ("Approved".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Approved");
				} else if ("Rejected".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Rejected");
				} else {
					data.setStatus(data.getVerifierStatus());
				}
			});
			listOfPensioners = listOfPensioners.stream().collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AdminDetails::getPensionerId) // Ensure

					)), ArrayList::new // Convert TreeSet back to ArrayList
			));
			int a = 1;
			for (AdminDetails details : listOfPensioners) {
				details.setSLNo(a++);
			}

			return listOfPensioners;

		} else if ("Pending".equalsIgnoreCase(status)) {

			List<Object[]> result = repository.getDetailsByDeptAndApproverCodeByPending(department, approverCode);

			List<AdminDetails> listOfPensioners = result.stream().map(data -> {
				AdminDetails details = new AdminDetails();

				String pensionerId = data[0] != null ? data[0].toString() : null;
				details.setPensionerId(pensionerId);
				details.setPensionerName(data[1] != null ? data[1].toString() : null);
				details.setPensionerType(data[2] != null ? data[2].toString() : null);
				details.setApproverCode(data[3] != null ? data[3].toString() : null);
				details.setSubmittedDate(data[4] != null ? data[4].toString() : null);
				details.setVerifierStatus(data[5] != null ? data[5].toString() : null);
				details.setApproverStatus(data[6] != null ? data[6].toString() : null);

				// Fetch additional fields from repository
//				String approvedDate = repository.getSubmittedDateInApproverAuditByPensionerIdAndDept(pensionerId,
//						department);
//				details.setApprovedDate(approvedDate);
//
//				String res = repository.getStatusInStatusEntityByPensionerIdAndDept(pensionerId, department);
//				details.setDLCStatus("Approved".equalsIgnoreCase(res) ? "Downloaded" : "Pending");

				return details;
			}).collect(Collectors.toList());

			listOfPensioners = listOfPensioners.stream().collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AdminDetails::getPensionerId) // Ensure

					)), ArrayList::new // Convert TreeSet back to ArrayList
			));

			int a = 1;
			for (AdminDetails details : listOfPensioners) {
				details.setSLNo(a++);
			}

			return listOfPensioners;
		} else if ("Submitted".equalsIgnoreCase(status)) {
			List<Object[]> result = repository.getDetailsByDeptAndApproverCodeBySubmitted(department, approverCode);

			List<AdminDetails> listOfPensioners = result.stream().map(data -> {
				AdminDetails details = new AdminDetails();

				String pensionerId = data[0] != null ? data[0].toString() : null;
				details.setPensionerId(pensionerId);
				details.setPensionerName(data[1] != null ? data[1].toString() : null);
				details.setPensionerType(data[2] != null ? data[2].toString() : null);
				details.setApproverCode(data[3] != null ? data[3].toString() : null);
				details.setSubmittedDate(data[4] != null ? data[4].toString() : null);
				details.setVerifierStatus(data[5] != null ? data[5].toString() : null);
				details.setApproverStatus(data[6] != null ? data[6].toString() : null);

				// Fetch additional fields from repository
				String approvedDate = repository.getSubmittedDateInApproverAuditByPensionerIdAndDept(pensionerId,
						department);
				details.setApprovedDate(approvedDate);

				String res = repository.getStatusInStatusEntityByPensionerIdAndDept(pensionerId, department);
				details.setDLCStatus("Approved".equalsIgnoreCase(res) ? "Downloaded" : "Pending");

				return details;
			}).collect(Collectors.toList());

			listOfPensioners.forEach(data -> {
				if ("Downloaded".equalsIgnoreCase(data.getDLCStatus())) {
					data.setStatus("Downloaded");
				} else if ("Approved".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Approved");
				} else if ("Rejected".equalsIgnoreCase(data.getApproverStatus())) {
					data.setStatus("Rejected");
				} else if ("Null".equalsIgnoreCase(data.getApproverStatus())
						&& "Verified".equalsIgnoreCase(data.getVerifierStatus())) {
					data.setStatus(data.getVerifierStatus());
				} else {

					data.setStatus("Submitted");
				}
			});
			listOfPensioners = listOfPensioners.stream().collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AdminDetails::getPensionerId) // Ensure

					)), ArrayList::new // Convert TreeSet back to ArrayList
			));

			int a = 1;
			for (AdminDetails details : listOfPensioners) {
				details.setSLNo(a++);
			}
			return listOfPensioners;

		}
		return null;

	}

	@Transactional
	public MasterDetails updateMasterData(MasterDetails request) {

		// ✅ 1. Get existing (OLD) data before update
		MasterDetails existing = getMasterDetails(request.getPensionerId(), request.getDept());

		if (existing == null) {
			throw new IllegalStateException(
					"Update failed. No record found for pensionerId: " + request.getPensionerId());
		}

		// ✅ 2. COPY OLD DATA INTO AUDIT TABLE (EXACT FIELD MAPPING)
		MasterDataAudit audit = new MasterDataAudit();

		audit.setPensionerId(existing.getPensionerId());
		audit.setPpoNo(existing.getPpoNo());
		audit.setPensionerName(existing.getPensionerName());
		audit.setDob(existing.getDob());
		audit.setPensionerType(existing.getPensionerType());
		audit.setPanNo(existing.getPanNo());
		audit.setMobile(existing.getMobile());

		audit.setVerifierOfficerCode(existing.getVerifierOfficerCode());
		audit.setVerifierOfficerName(existing.getVerifierOfficerName());
		audit.setVerifierOfficerMobile(existing.getVerifierOfficerMobile());

		audit.setApproverOfficerCode(existing.getApproverOfficerCode());
		audit.setApproverOfficerName(existing.getApproverOfficerName());
		audit.setApproverOfficerMobile(existing.getApproverOfficerMobile());

		audit.setDept(existing.getDept());
		// audit.setDepartmentPhoto(existing.getDepartmentPhoto());
		audit.setAdmin(existing.getDept());

		// ✅ Creation & modified timestamps will be AUTO handled by Hibernate
		auditRepository.save(audit); // 🔥 OLD DATA SAFELY STORED

		// ✅ 3. Now perform your EXISTING UPDATE (UNCHANGED)
		int updated = repository.updateMasterDetails(request);

		if (updated == 0) {
			throw new IllegalStateException(
					"Update failed. No record updated for pensioner_fp_id: " + request.getPensionerId());
		}

		// ✅ 4. Fetch updated record (UNCHANGED)
		MasterDetails response = getMasterDetails(request.getPensionerId(), request.getDept());

		// byte[] oldImage = utils.getPensionerImage(request.getDept(),
		// request.getPensionerId());

		try {
			utils.deletePensionerImage(request.getDept(), request.getPensionerId());
		} catch (Exception e) {
			 
		}

		utils.createPensionerImage(request.getDept(), request.getPensionerId(), request.getDepartmentPhoto());
		if (response == null) {
			throw new IllegalStateException(
					"Update succeeded but no data found for pensionerId: " + request.getPensionerId());
		}

		return response;
	}

	private String detectImageExtension(byte[] imageBytes) {

		if (imageBytes.length < 4)
			return null;

		// PNG
		if ((imageBytes[0] & 0xFF) == 0x89 && (imageBytes[1] & 0xFF) == 0x50 && (imageBytes[2] & 0xFF) == 0x4E
				&& (imageBytes[3] & 0xFF) == 0x47) {
			return ".png";
		}

		// JPG
		if ((imageBytes[0] & 0xFF) == 0xFF && (imageBytes[1] & 0xFF) == 0xD8 && (imageBytes[2] & 0xFF) == 0xFF) {
			return ".jpg";
		}

		return null;
	}

}
