package com.zzg.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Permission {

    @Id
    private Integer permissionId;
    private String permissionName;
    private String permissionZh;
    private String permissionPath;
    private String permisionIcon;
    private String permisionComponent;
    private Integer permisionParentId;
    
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)   
//    @JoinColumn(name="permision_parent_id")  
//    private Permission parent;
//    // 子菜单项
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER)  
//    private Set<Permission> child  = new HashSet<Permission>() ;
}
