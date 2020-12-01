package com.zzg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zzg.entity.Lease;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Integer> {

}
