package com.zzg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zzg.entity.Role;

/**
 * 
 * @author zzg
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRoleId(Integer userId);
}
