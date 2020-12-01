package com.zzg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zzg.entity.Verification;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Integer> {
	Verification findByVerificationCode(String verificationCode);
}
