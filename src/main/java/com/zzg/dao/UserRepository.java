package com.zzg.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zzg.entity.User;


/**
 * 
 * @author zzg
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByUserId(Integer userId);
    
    User findByEmail(String email);
    
    User findByTelephone(String telephone);
}
