package com.zzg.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.zzg.entity.House;

@Repository
public interface HouseRepository extends PagingAndSortingRepository<House, Integer> {
	 Page<House> findAll(Pageable pageable);
}
