package com.wissen.tpu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wissen.tpu.entity.ApproverUser;
import com.wissen.tpu.entity.VerifierUser;

@Repository
public interface ApproverRepository extends JpaRepository<ApproverUser, Integer> {
	
	ApproverUser findByCode(String code);
	
	@Query(value = " SELECT  distinct pt.pensioner_fp_id,pt.pensioner_name,pt.pensioner_type,rp.verifier_status,rp.created_time"
			+ " FROM registration_pensioner rp JOIN pensioners_target pt "
			+ "ON rp.pensioner_id=pt.pensioner_fp_id WHERE pt.approver_unit_code=:approverOfficerCode AND rp.department = :deptCode  "
			+ "AND rp.photo_match_status='MATCHED' AND rp.verifier_status is NOT NULL and approver_status IS NULL ", nativeQuery = true)
	List<Object[]> findPensionersByApproverOfficerCodeAndDept(@Param("approverOfficerCode") String approverOfficerCode,
			@Param("deptCode") String dept);

	@Query(value = "SELECT DISTINCT rp.pensioner_id, rp.ppo_no, rp.pensioner_name, rp.mobile_no, "
			+ "rp.photo_match_status, rp.photo_match_score, rp.dg_status, rp.status, "
			+ "rp.input_photo_uuid, rp.department, pt.pensioner_type, pt.approver_unit_code ,rp.created_time ,rp.re_marriage "
			+ "FROM registration_pensioner rp "
			+ "INNER JOIN pensioners_target pt ON pt.pensioner_fp_id = rp.pensioner_id "
			+ "WHERE rp.pensioner_id = :pensionerId " + "AND rp.photo_match_status = 'MATCHED' "
			+ " AND rp.department = :deptCode AND verifier_status is not null AND approver_status is  null", nativeQuery = true)
	List<Object[]> findPensionersByPensionerIdAndStatus(@Param("pensionerId") String pensionerId,
			@Param("deptCode") String dept);

	ApproverUser findByCodeAndPassword(String code, String password);

	@Modifying
	@Transactional
	@Query(value = "UPDATE registration_pensioner SET approver_status = :status,approver_remark=:reMark WHERE pensioner_id = :pensionerId AND department =:dept   AND photo_match_status = 'MATCHED' AND approver_status IS NULL", nativeQuery = true)
	int updateApproverStatusByPensionerId(String status, String reMark, String pensionerId, String dept);

	ApproverUser findByOtp(String otp);

	ApproverUser findByPasswordAndCode(String oldPassword, String verifierCode);

	@Query(value = " SELECT  distinct pt.pensioner_fp_id,pt.pensioner_name,pt.pensioner_type,rp.approver_status,rp.created_time "
			+ " FROM registration_pensioner rp JOIN pensioners_target pt "
			+ "ON rp.pensioner_id=pt.pensioner_fp_id WHERE pt.approver_unit_code=:approverOfficerCode AND rp.department = :deptCode  "
			+ "AND rp.photo_match_status='MATCHED' AND rp.verifier_status is NOT NULL and approver_status='Approved' ", nativeQuery = true)
	List<Object[]> findPensionersByApproverOfficerCodeAndDeptByApproved(
			@Param("approverOfficerCode") String approverOfficerCode, @Param("deptCode") String dept);

	@Query(value = " SELECT  distinct pt.pensioner_fp_id,pt.pensioner_name,pt.pensioner_type,rp.approver_status ,rp.created_time"
			+ " FROM registration_pensioner rp JOIN pensioners_target pt "
			+ "ON rp.pensioner_id=pt.pensioner_fp_id WHERE pt.approver_unit_code=:approverOfficerCode AND rp.department = :deptCode  "
			+ "AND rp.photo_match_status='MATCHED' AND rp.verifier_status is NOT NULL and approver_status='Rejected' ", nativeQuery = true)
	List<Object[]> findPensionersByApproverOfficerCodeAndDeptByRejected(
			@Param("approverOfficerCode") String approverOfficerCode, @Param("deptCode") String dept);

	@Query(value = "SELECT DISTINCT rp.pensioner_id, rp.ppo_no, rp.pensioner_name, rp.mobile_no, "
			+ "rp.photo_match_status, rp.photo_match_score, rp.dg_status, rp.status, "
			+ "rp.input_photo_uuid, rp.department, pt.pensioner_type, pt.approver_unit_code,rp.created_time ,rp.re_marriage "
			+ "FROM registration_pensioner rp "
			+ "INNER JOIN pensioners_target pt ON pt.pensioner_fp_id = rp.pensioner_id "
			+ "WHERE rp.pensioner_id = :pensionerId " + "AND rp.photo_match_status = 'MATCHED' "
			+ " AND rp.department = :deptCode AND verifier_status is not null AND approver_status='Approved'", nativeQuery = true)
	List<Object[]> findPensionersByPensionerIdAndStatusByApproved(@Param("pensionerId") String pensionerId,
			@Param("deptCode") String dept);

	@Query(value = "SELECT DISTINCT rp.pensioner_id, rp.ppo_no, rp.pensioner_name, rp.mobile_no, "
			+ "rp.photo_match_status, rp.photo_match_score, rp.dg_status, rp.status, "
			+ "rp.input_photo_uuid, rp.department, pt.pensioner_type, pt.approver_unit_code,rp.created_time ,rp.re_marriage "
			+ "FROM registration_pensioner rp "
			+ "INNER JOIN pensioners_target pt ON pt.pensioner_fp_id = rp.pensioner_id "
			+ "WHERE rp.pensioner_id = :pensionerId " + "AND rp.photo_match_status = 'MATCHED' "
			+ " AND rp.department = :deptCode AND verifier_status is not null AND approver_status='Rejected'", nativeQuery = true)
	List<Object[]> findPensionersByPensionerIdAndStatusByRejected(@Param("pensionerId") String pensionerId,
			@Param("deptCode") String dept);
	
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
