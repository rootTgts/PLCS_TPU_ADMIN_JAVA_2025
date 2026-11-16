package com.wissen.tpu.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DownloadRepository {

	@Autowired
	private EntityManager entityManager;

	public Object[] findByPensionerIDAndDeptForDownload(String pensionerId, String dept) {

	    String jpql = "SELECT pt.pensioner_fp_id, pt.ppo_no, pt.pensioner_name, pt.mobile_no, "
	            + "rp.input_photo_uuid, rp.created_time as submitted_time, aa.created_date as approved_time ,rp.re_marriage "
	            + "FROM pensioners_target pt "
	            + "  INNER JOIN registration_pensioner rp ON pt.pensioner_fp_id = rp.pensioner_id and pt.deptCode =rp.department "
	            + "  INNER JOIN approver_audit aa ON rp.pensioner_id = aa.pensioner_id and rp.department =aa.department  "
	            + "WHERE pt.deptCode  = :department "
	            + "AND aa.pensioner_id = :pensioner_id "
	            + "AND rp.photo_match_status = 'Matched'";

	    Query query = entityManager.createNativeQuery(jpql);

	    query.setParameter("pensioner_id", pensionerId);
	    query.setParameter("department", dept);

	    try {
	        List<Object[]> result = query.getResultList();
	        if (!result.isEmpty()) {
	            return result.get(0); // Return the first result
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null; // Return null if no results are found
	}


}
