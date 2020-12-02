package com.zzg.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.zzg.entity.Cost;

@Repository
public interface CostRepository extends JpaRepository<Cost, Integer>, JpaSpecificationExecutor<Cost> {

	/**
	 * 原生SQL
	 * @param houseId
	 * @param costId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update cost u set u.house_id = ?1 where u.cost_id = ?2")
	int modifyHouseId(Integer houseId, Integer costId);

}
