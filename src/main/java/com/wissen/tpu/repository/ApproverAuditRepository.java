package com.wissen.tpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.tpu.entity.ApproverAudit;

@Repository
public interface ApproverAuditRepository extends JpaRepository<ApproverAudit, Long> {

	public String findCreatedDateByPensionerIdAndDepartment(String pensionerId, String dept);

}
