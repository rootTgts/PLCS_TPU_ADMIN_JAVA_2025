package com.wissen.tpu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.tpu.entity.ImageEntity;

@Repository
public interface FileUploadRepository extends JpaRepository<ImageEntity, Long> {

	
}
