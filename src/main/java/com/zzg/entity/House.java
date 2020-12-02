package com.zzg.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class House {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Integer houseId;

	private String houseNumber;

	private String area;

	private String houseAddress;

	private String houseType;

	private String acreage;

	private Integer peroples;

	private Integer rent;

	private Integer status;

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH}, optional = false) // 可选属性optional=false,表示house不能为空。删除房屋，不影响用户
	@JoinColumn(name = "user_id") // 设置在house表中的关联字段(外键)
	private User user;// 所属房屋


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "house_lease", joinColumns = {
			@JoinColumn(name = "house_id", referencedColumnName = "houseId") }, inverseJoinColumns = {
					@JoinColumn(name = "lease_id", referencedColumnName = "leaseId") })
	private Set<Lease> leases;

}
