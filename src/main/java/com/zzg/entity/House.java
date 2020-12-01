package com.zzg.entity;

import java.util.List;
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
import javax.persistence.OneToMany;

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

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false) // 可选属性optional=false,表示house不能为空。删除房屋，不影响用户
	@JoinColumn(name = "user_id") // 设置在house表中的关联字段(外键)
	private User user;// 所属房屋

	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// 级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
	// 拥有mappedBy注解的实体类为关系被维护端
	// mappedBy="house"中的house是Cost中的house属性
	private List<Cost> costs;// 费用列表

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "house_lease", joinColumns = {
			@JoinColumn(name = "house_id", referencedColumnName = "houseId") }, inverseJoinColumns = {
					@JoinColumn(name = "lease_id", referencedColumnName = "leaseId") })
	private Set<Lease> leases;

}
