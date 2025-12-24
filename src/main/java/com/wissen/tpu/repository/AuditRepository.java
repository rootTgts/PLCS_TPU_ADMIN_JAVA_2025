package com.wissen.tpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wissen.tpu.entity.MasterDataAudit;

public interface AuditRepository extends JpaRepository<MasterDataAudit, Long> {

}
