package com.zzg.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author zzg
 *
 */
@Getter
@Setter
@Entity
public class User {
    @Id
    private Integer userId;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId")})
    private Set<Role> roles;
    
    private String telephone;
    
    private String email;
    
    private String guid;
    
    private String realName;
    
    private String address;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// 级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
	// 拥有mappedBy注解的实体类为关系被维护端
	// mappedBy="user"中的user是House中的user属性
	private List<House> houses;// 租赁房屋列表

}
