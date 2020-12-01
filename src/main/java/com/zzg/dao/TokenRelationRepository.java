package com.zzg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zzg.entity.TokenRelation;

@Repository("tokenRelationRepository")
public interface TokenRelationRepository extends JpaRepository<TokenRelation, Integer> {
	TokenRelation findByUsername(String username);
	
	TokenRelation findByToken(String token);
}
