package com.zzg.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.zzg.entity.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer>, JpaSpecificationExecutor<House> {
	/**
	 * 原生SQL
	 * @param houseId
	 * @param costId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update house u set u.user_id = ?1 where u.house_id = ?2")
	int modifyUserId(Integer userId, Integer houseId);
}
