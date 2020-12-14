package com.zzg.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionVo {
	private Integer permissionId;
	private String permissionName;
	private String permissionZh;
	private String permissionPath;
	private String permisionIcon;
	private String permisionComponent;
	private Integer permisionParentId;
	private List<PermissionVo> child;
}
