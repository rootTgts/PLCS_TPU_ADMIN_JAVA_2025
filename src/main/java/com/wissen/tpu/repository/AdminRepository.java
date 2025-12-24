package com.wissen.tpu.repository;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wissen.tpu.dto.MasterDetails;
import com.wissen.tpu.entity.AdminUser;

@Repository
public class AdminRepository {

	@Autowired
	private EntityManager entityManager;

	public List<Object[]> getPensionerDetails(String department) {
		// Define the query
		String queryStr = "SELECT DISTINCT pensioner_id ,pensioner_name,status ,verifier_status ,approver_status "
				+ "FROM registration_pensioner WHERE photo_match_status ='Matched' AND department = :department";
		Query query = entityManager.createNativeQuery(queryStr);

		query.setParameter("department", department);

		try {

			return query.getResultList();
		} catch (NoResultException e) {

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("Error while fetching approved count", e);
		}
		return null;

	}

	public List<Object[]> getPensionerDetailsByDeptAndApproverCode(String department, String approverCode) {
		// Define the query
		String queryStr = "    SELECT DISTINCT rp.pensioner_id ,rp.pensioner_name,rp.status ,rp.verifier_status ,rp.approver_status   from registration_pensioner rp inner join pensioners_target pt \r\n"
				+ "       on pt.pensioner_fp_id =rp.pensioner_id where rp.photo_match_status ='MATCHED' and rp.department =:department and pt.approver_unit_code=:approverCode ;";
		Query query = entityManager.createNativeQuery(queryStr);

		query.setParameter("department", department);
		query.setParameter("approverCode", approverCode);
		try {

			return query.getResultList();
		} catch (NoResultException e) {

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("Error while fetching approved count", e);
		}
		return null;

	}

	public BigInteger getTotalCount(String department) {

		String st1 = "select count(distinct  pensioner_fp_id) from  pensioners_target where deptCode = :department";
		Query query = entityManager.createNativeQuery(st1);
		query.setParameter("department", department);
		BigInteger count = null;
		try {
			count = (BigInteger) query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	public BigInteger getTotalCountByDeptAndApproverCode(String department) {

		String st1 = "select count(distinct  pensioner_fp_id) from  pensioners_target where deptCode = :department";
		Query query = entityManager.createNativeQuery(st1);
		query.setParameter("department", department);
		BigInteger count = null;
		try {
			count = (BigInteger) query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	public BigInteger getTotalCount(String department, String approverCode) {

		String st1 = "select count(distinct  pensioner_fp_id) from  pensioners_target where deptCode = :department and approver_unit_code=:approverCode";
		Query query = entityManager.createNativeQuery(st1);
		query.setParameter("department", department);
		query.setParameter("approverCode", approverCode);
		BigInteger count = null;
		try {
			count = (BigInteger) query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	public BigInteger getDLCDownloadCount(String department) {

		String st1 = "select count(distinct  pensioner_Id) from  status_entity where department = :department and status='A'";
		Query query = entityManager.createNativeQuery(st1);
		query.setParameter("department", department);
		BigInteger count = null;
		try {
			count = (BigInteger) query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

//	public String authAdmin(String userName, String password) {
//
//		String st1 = "   select department from admin_user WHERE username =:username AND  password =:password ;";
//		Query query = entityManager.createNativeQuery(st1);
//		query.setParameter("username", userName);
//		query.setParameter("password", password);
//		String dept = null;
//		try {
//			dept = (String) query.getSingleResult();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return dept;
//
//	}
	@Transactional(readOnly = true)
	public AdminUser findAdminByUsername(String code) {
	    try {
	        String sql = "SELECT * FROM admin_user WHERE username = :username";
	        Query query = entityManager.createNativeQuery(sql, AdminUser.class);
	        query.setParameter("username", code);

	        return (AdminUser) query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
	
	public List<String> getApproverList(String department) {
		List<String> response;
		String str1 = " SELECT approver_unit_code  FROM pensioners_target pt  where deptCode =:department group by approver_unit_code";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("department", department);
		return response = (List<String>) query.getResultList();
	}
	
	public List<Object[]> getMasterDetails(String pensionerId,String adminDept) {
		String str1 = " SELECT pensioner_fp_id,ppo_no ,pensioner_name ,date_of_birth ,pensioner_type ,pan_no ,mobile_no ,verifying_officer_code ,verifying_officer_name ,verifying_officer_mobile_no ,approver_unit_code ,approver_name ,approver_mobile,deptCode FROM pensioners_target pt where pensioner_fp_id =:pensionerId and deptCode=:adminDept ";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("pensionerId", pensionerId);
		query.setParameter("adminDept", adminDept);
		List<Object[]> response = query.getResultList();
		return response;
	}
	
	public int updateMasterDetails(MasterDetails request) {

	    String sql =
	        "UPDATE pensioners_target SET " +
	        "ppo_no = :ppoNo, " +
	        "pensioner_name = :pensionerName, " +
	        "date_of_birth = :dateOfBirth, " +
	        "pensioner_type = :pensionerType, " +
	        "pan_no = :panNo, " +
	        "mobile_no = :mobileNo, " +
	        "verifying_officer_code = :voCode, " +
	        "verifying_officer_name = :voName, " +
	        "verifying_officer_mobile_no = :voMobile, " +
	        "approver_unit_code = :approverUnitCode, " +
	        "approver_name = :approverName, " +
	        "approver_mobile = :approverMobile " +
	        "WHERE pensioner_fp_id = :pensionerId  and deptCode=:adminDept";

	    Query query = entityManager.createNativeQuery(sql);

	    query.setParameter("ppoNo", request.getPpoNo());
	    query.setParameter("pensionerName", request.getPensionerName());
	    query.setParameter("dateOfBirth", request.getDob());
	    query.setParameter("pensionerType", request.getPensionerType());
	    query.setParameter("panNo", request.getPanNo());
	    query.setParameter("mobileNo", request.getMobile());
	    query.setParameter("voCode", request.getVerifierOfficerCode());
	    query.setParameter("voName", request.getVerifierOfficerName());
	    query.setParameter("voMobile", request.getVerifierOfficerMobile());
	    query.setParameter("approverUnitCode", request.getApproverOfficerCode());
	    query.setParameter("approverName", request.getApproverOfficerName());
	    query.setParameter("approverMobile", request.getApproverOfficerMobile());
		query.setParameter("pensionerId", request.getPensionerId());
		query.setParameter("adminDept", request.getDept());

	    return query.executeUpdate();
	}

	

	public List<Object[]> getDetailsByDeptAndByApproverStatus(String department, String status) {
		String str1 = "select rp.pensioner_id,rp.pensioner_name ,pt.pensioner_type ,pt.approver_unit_code , rp.created_time as Submitted_Date ,rp.verifier_status ,rp.approver_status  from  registration_pensioner rp "
				+ " inner join pensioners_target pt on rp.pensioner_id =pt.pensioner_fp_id and rp.department =pt.deptCode  "
				+ " where rp.photo_match_status ='MATCHED' and rp.department =:department and rp.approver_status =:status ";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("department", department);
		query.setParameter("status", status);
		List<Object[]> response = query.getResultList();
		return response;

	}

	public List<Object[]> getDetailsByDeptAndByApproverStatusAndApproverCode(String department, String status,
			String approverCode) {
		String str1 = "select rp.pensioner_id,rp.pensioner_name ,pt.pensioner_type ,pt.approver_unit_code , rp.created_time as Submitted_Date ,rp.verifier_status ,rp.approver_status  from  registration_pensioner rp "
				+ " inner join pensioners_target pt on rp.pensioner_id =pt.pensioner_fp_id and rp.department =pt.deptCode  "
				+ " where rp.photo_match_status ='MATCHED' and rp.department =:department and pt.approver_unit_code=:approverCode and rp.approver_status =:status ";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("department", department);
		query.setParameter("status", status);
		query.setParameter("approverCode", approverCode);
		List<Object[]> response = query.getResultList();
		return response;

	}

	public String getStatusInStatusEntityByPensionerIdAndDept(String pensionerId, String department) {
		try {
			String queryStr = "SELECT status_description FROM status_entity WHERE department = :department AND pensioner_id = :pensionerId LIMIT 1";
			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("department", department);
			query.setParameter("pensionerId", pensionerId);

			return (String) query.getSingleResult();
		} catch (NoResultException e) {

			System.err.println("No result found for pensionerId: " + pensionerId + ", department: " + department);
			return null;
		} catch (NonUniqueResultException e) {

			System.err
					.println("Multiple results found for pensionerId: " + pensionerId + ", department: " + department);
			return null;
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(
					"Error while fetching status for pensionerId: " + pensionerId + ", department: " + department, e);
		}
	}

	public String getSubmittedDateInApproverAuditByPensionerIdAndDept(String pensionerId, String department) {
		try {
			String queryStr = "SELECT created_date FROM approver_audit WHERE department = :department AND pensioner_id = :pensionerId";
			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("department", department);
			query.setParameter("pensionerId", pensionerId);

			java.sql.Timestamp timestamp = (java.sql.Timestamp) query.getSingleResult();
			if (timestamp != null) {
				return timestamp.toString(); // Returns in "yyyy-MM-dd HH:mm:ss.SSS" format
			}
		} catch (NoResultException e) {
			System.err.println("No result found for pensionerId: " + pensionerId + ", department: " + department);
		} catch (Exception e) {
			e.printStackTrace(); // Log the stack trace for debugging
		}
		return null;
	}

	public List<Object[]> getDetailsByDeptAndByPending(String department) {
		String str1 = "select rp.pensioner_id,rp.pensioner_name ,pt.pensioner_type ,pt.approver_unit_code , rp.created_time as Submitted_Date ,rp.verifier_status ,rp.approver_status  from  registration_pensioner rp "
				+ " inner join pensioners_target pt on rp.pensioner_id =pt.pensioner_fp_id and rp.department =pt.deptCode "
				+ " where rp.photo_match_status ='MATCHED' and rp.department =:department and rp.approver_status is NULL ";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("department", department);

		List<Object[]> response = query.getResultList();
		return response;
	}

	public List<Object[]> getDetailsByDeptAndApproverCodeByPending(String department,String approverCode) {
		String str1 = "select rp.pensioner_id,rp.pensioner_name ,pt.pensioner_type ,pt.approver_unit_code , rp.created_time as Submitted_Date ,rp.verifier_status ,rp.approver_status  from  registration_pensioner rp "
				+ " inner join pensioners_target pt on rp.pensioner_id =pt.pensioner_fp_id and rp.department =pt.deptCode "
				+ " where rp.photo_match_status ='MATCHED' and rp.department =:department and pt.approver_unit_code=:approverCode and rp.approver_status is NULL ";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("department", department);
		query.setParameter("approverCode", approverCode);
		List<Object[]> response = query.getResultList();
		return response;
	}
	public List<Object[]> getDetailsByDeptAndBySubmitted(String department) {
		String str1 = "select rp.pensioner_id,rp.pensioner_name ,pt.pensioner_type ,pt.approver_unit_code , rp.created_time as Submitted_Date ,rp.verifier_status ,rp.approver_status  from  registration_pensioner rp "
				+ " inner join pensioners_target pt on rp.pensioner_id =pt.pensioner_fp_id and rp.department =pt.deptCode"
				+ " where rp.photo_match_status ='MATCHED' and rp.department =:department ";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("department", department);

		List<Object[]> response = query.getResultList();
		return response;
	}
	
	public List<Object[]> getDetailsByDeptAndApproverCodeBySubmitted(String department,String approverCode) {
		String str1 = "select rp.pensioner_id,rp.pensioner_name ,pt.pensioner_type ,pt.approver_unit_code , rp.created_time as Submitted_Date ,rp.verifier_status ,rp.approver_status  from  registration_pensioner rp "
				+ " inner join pensioners_target pt on rp.pensioner_id =pt.pensioner_fp_id and rp.department =pt.deptCode"
				+ " where rp.photo_match_status ='MATCHED' and rp.department =:department and pt.approver_unit_code=:approverCode  ";
		Query query = entityManager.createNativeQuery(str1);
		query.setParameter("department", department);
		query.setParameter("approverCode", approverCode);
		List<Object[]> response = query.getResultList();
		return response;
	}
}
