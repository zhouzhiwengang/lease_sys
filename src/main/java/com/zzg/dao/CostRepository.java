package com.zzg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zzg.entity.Cost;

@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {

}
