package com.zzg.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cost {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Integer costId;
	
	 @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示house不能为空。删除房屋，不影响用户
	 @JoinColumn(name="house_id")//设置在house表中的关联字段(外键)
	 private House house;//所属房屋
	 
	 private Date createDate;
	 
	 private Integer money;
	 
	 private Integer status;

}
