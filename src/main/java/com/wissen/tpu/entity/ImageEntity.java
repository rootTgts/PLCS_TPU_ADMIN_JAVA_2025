package com.wissen.tpu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ImageEntity")
public class ImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long requestId;
	private String department;
	private String image;
	private String pensionerId;
}
