package com.wissen.tpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.tpu.entity.VerifierAudit;

@Repository
public interface VerifierAuditRepository extends JpaRepository<VerifierAudit, Long> {

}
