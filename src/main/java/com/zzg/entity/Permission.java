package com.zzg.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

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
    private String permission;
}
