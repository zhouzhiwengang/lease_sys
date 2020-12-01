package com.zzg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="token_relation")
public class TokenRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Integer relationSid;
	
	private String username;
	
	private String token;
	
}
