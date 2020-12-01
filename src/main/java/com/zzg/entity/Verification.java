package com.zzg.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="verification")
public class Verification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Integer verificationId;
	
	private String verificationCode;
	
	private Integer status;
	
	private Date createDate;
	
	private Date failDate;
	

}
