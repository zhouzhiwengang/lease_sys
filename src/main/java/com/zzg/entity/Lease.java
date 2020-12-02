package com.zzg.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Lease {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Integer leaseId;

	private String realName;

	private String idCard;

	private Integer sex;

	private String telphone;

	private String hometown;

	private Integer status;

	private Date startDate;

	private Date endDate;

	private Date signDate;

//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "house_lease", joinColumns = {
//			@JoinColumn(name = "lease_id", referencedColumnName = "leaseId") }, inverseJoinColumns = {
//					@JoinColumn(name = "house_id", referencedColumnName = "houseId") })
//	private Set<House> houses;
}
