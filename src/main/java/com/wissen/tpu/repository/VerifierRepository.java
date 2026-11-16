package com.wissen.tpu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wissen.tpu.entity.VerifierUser;

@Repository
public interface VerifierRepository extends JpaRepository<VerifierUser, Integer> {

	VerifierUser findByCode(String code);

//	@Query(value = " SELECT rp.pensioner_id,rp.ppo_no,rp.pensioner_name,pt.date_of_birth,pt.pensioner_type,rp.mobile_no, rp.photo_match_status,rp.photo_match_score,rp.status FROM registration_pensioner rp JOIN pensioners_target pt ON rp.pensioner_id=pt.pensioner_fp_id WHERE pt.verifying_officer_code=:verifyingOfficerCode AND rp.photo_match_status='MATCHED'", nativeQuery = true)
//	List<Object[]> findPensionersByVerifyingOfficerCode(@Param("verifyingOfficerCode") String verifyingOfficerCode);

	@Query(value = "SELECT DISTINCT pt.pensioner_fp_id, pt.pensioner_name, pt.pensioner_type, rp.status ,rp.created_time "
			+ "FROM registration_pensioner rp " + "JOIN pensioners_target pt ON rp.pensioner_id = pt.pensioner_fp_id "
			+ "WHERE pt.verifying_officer_code = :verifyingOfficerCode " + "AND rp.department = :deptCode "
			+ "AND rp.photo_match_status = 'MATCHED' " + "AND rp.verifier_status IS NULL", nativeQuery = true)
	List<Object[]> findPensionersByVerifyingOfficerCode(@Param("verifyingOfficerCode") String verifyingOfficerCode,
			@Param("deptCode") String deptCode);

	@Query(value = "SELECT DISTINCT pt.pensioner_fp_id, pt.ppo_no, pt.pensioner_name, rp.mobile_no, "
			+ "rp.photo_match_status, rp.photo_match_score, rp.dg_status, rp.status, "
			+ "rp.input_photo_uuid, rp.department, pt.pensioner_type, pt.approver_unit_code,rp.created_time,rp.re_marriage "
			+ "FROM registration_pensioner rp "
			+ "INNER JOIN pensioners_target pt ON pt.pensioner_fp_id = rp.pensioner_id "
			+ "WHERE rp.pensioner_id = :pensionerId  AND rp.department = :deptCode AND pt.verifying_officer_code=:code "
			+ "AND rp.photo_match_status = 'MATCHED' " + "AND rp.verifier_status IS NULL", nativeQuery = true)
	List<Object[]> findPensionersByPensionerIdAndStatus(@Param("pensionerId") String pensionerId,
			@Param("deptCode") String dept, @Param("code") String verifyingOfficerCode);

	// VerifierUser findByCodeAndPassword(String code, String password);

	@Modifying
	@Transactional
	@Query(value = "UPDATE registration_pensioner SET verifier_status = :status ,verifier_remark=:remark WHERE pensioner_id = :pensionerId AND department = :department AND photo_match_status = 'MATCHED' AND verifier_status IS NULL", nativeQuery = true)
	int updateVerifierStatusByPensionerId(String status, String remark, String pensionerId, String department);

	 

	VerifierUser findByPasswordAndCode(String password, String code);

	@Query(value = "SELECT DISTINCT pt.pensioner_fp_id, pt.pensioner_name, pt.pensioner_type, rp.verifier_status , rp.created_time "
			+ "FROM registration_pensioner rp " + "JOIN pensioners_target pt ON rp.pensioner_id = pt.pensioner_fp_id "
			+ "WHERE pt.verifying_officer_code = :verifyingOfficerCode " + "AND rp.department = :deptCode "
			+ "AND rp.photo_match_status = 'MATCHED' " + "AND rp.verifier_status ='Verified'", nativeQuery = true)
	List<Object[]> findPensionersByVerifyingOfficerCodeVerified(
			@Param("verifyingOfficerCode") String verifyingOfficerCode, @Param("deptCode") String deptCode);

	@Query(value = "SELECT DISTINCT pt.pensioner_fp_id, pt.pensioner_name, pt.pensioner_type, rp.verifier_status ,rp.created_time "
			+ "FROM registration_pensioner rp " + "JOIN pensioners_target pt ON rp.pensioner_id = pt.pensioner_fp_id "
			+ "WHERE pt.verifying_officer_code = :verifyingOfficerCode " + "AND rp.department = :deptCode "
			+ "AND rp.photo_match_status = 'MATCHED' " + "AND rp.verifier_status ='Rejected'", nativeQuery = true)
	List<Object[]> findPensionersByVerifyingOfficerCodeVerifierRejected(
			@Param("verifyingOfficerCode") String verifyingOfficerCode, @Param("deptCode") String deptCode);

	@Query(value = "SELECT DISTINCT pt.pensioner_fp_id, pt.ppo_no, pt.pensioner_name, rp.mobile_no, "
			+ "rp.photo_match_status, rp.photo_match_score, rp.dg_status, rp.status, "
			+ "rp.input_photo_uuid, rp.department, pt.pensioner_type, pt.approver_unit_code,rp.created_time,rp.re_marriage "
			+ "FROM registration_pensioner rp "
			+ "INNER JOIN pensioners_target pt ON pt.pensioner_fp_id = rp.pensioner_id "
			+ "WHERE rp.pensioner_id = :pensionerId  AND rp.department = :deptCode AND pt.verifying_officer_code=:code "
			+ "AND rp.photo_match_status = 'MATCHED' " + "AND rp.verifier_status='Verified'", nativeQuery = true)
	List<Object[]> findPensionersByPensionerIdAndStatusVerified(@Param("pensionerId") String pensionerId,
			@Param("deptCode") String dept, @Param("code") String verifyingOfficerCode);

	@Query(value = "SELECT DISTINCT pt.pensioner_fp_id, pt.ppo_no, pt.pensioner_name, rp.mobile_no, "
			+ "rp.photo_match_status, rp.photo_match_score, rp.dg_status, rp.status, "
			+ "rp.input_photo_uuid, rp.department, pt.pensioner_type, pt.approver_unit_code ,rp.created_time,rp.re_marriage "
			+ "FROM registration_pensioner rp "
			+ "INNER JOIN pensioners_target pt ON pt.pensioner_fp_id = rp.pensioner_id "
			+ "WHERE rp.pensioner_id = :pensionerId  AND rp.department = :deptCode AND pt.verifying_officer_code=:code "
			+ "AND rp.photo_match_status = 'MATCHED' " + "AND rp.verifier_status='Rejected'", nativeQuery = true)
	List<Object[]> findPensionersByPensionerIdAndStatusVerifierRejectedDisplay(@Param("pensionerId") String pensionerId,
			@Param("deptCode") String dept, @Param("code") String verifyingOfficerCode);

    @Query(value = "SELECT pensioner_fp_id, ppo_no, pensioner_name, date_of_birth, pensioner_type, " +
            "pan_no, mobile_no, verifying_officer_code, verifying_officer_name, verifying_officer_mobile_no, " +
            "approver_unit_code, approver_name, approver_mobile, deptCode " +
            "FROM pensioners_target pt " +
            "WHERE pt.pensioner_fp_id = :pensionerId " +
            "AND pt.deptCode = :adminDept",
            nativeQuery = true)
    List<Object[]> getMasterDetails(@Param("pensionerId") String pensionerId,
                                    @Param("adminDept") String adminDept);
	
}
