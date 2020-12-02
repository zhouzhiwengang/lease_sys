package com.zzg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zzg.common.AbstractController;
import com.zzg.dao.CostRepository;
import com.zzg.entity.Cost;

@RestController
public class CostController extends AbstractController {
	@Autowired
	CostRepository costRepository;
	
	@RequestMapping(value = "/cost/save", method = RequestMethod.POST)
	@ResponseBody
	public Map insert(@RequestBody Cost cost) {
		costRepository.save(cost);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "新增成功");
		return map;
	}
	
	/**
	 * 动态更新:仅限于更新单表字段和多对多表关系,产生原因：由于@Many-To-One
	 * @param house
	 * @return
	 */
	@RequestMapping(value = "/cost/update", method = RequestMethod.POST)
	@ResponseBody
	public Map update(@RequestBody Cost cost) {
		Optional<Cost> old = costRepository.findById(cost.getCostId());
		if(old.isPresent()){
			Cost oldCost = old.get();
            //将传过来的 house 中的非NULL属性值复制到 oldHouse 中
            copyPropertiesIgnoreNull(cost, oldCost);
            //将得到的新的 oldHouse 对象重新保存到数据库，因为数据库中已经存在该记录
            //所以JPA会很智能的 改为更新操作，更新数据库
            costRepository.save(oldCost);
        }
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "更新成功");
		return map;
	}
	
	/**
	 * 基于原生SQL 功能实现
	 * @param houseId
	 * @param costId
	 * @return
	 */
	@RequestMapping(value = "/cost/houseId", method = RequestMethod.GET)
	@ResponseBody
	public Map updateHouseId(Integer houseId,Integer costId) {
		costRepository.modifyHouseId(houseId, costId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "更新成功");
		return map;
	}
	
	
	
	@RequestMapping(value = "/cost/findPage", method = RequestMethod.POST)
	@ResponseBody
	public Map findPage(@RequestBody Map<String, Object> paramter) {
		//显示第1页每页显示3条
		PageRequest pr = super.initPageBounds(paramter);
		
		Page<Cost> stus = costRepository.findAll(new Specification<Cost>(){
			@Override
			public Predicate toPredicate(Root<Cost> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicateList = new ArrayList<>();
				
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		}, pr);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "查询成功");
		map.put("date", stus);
		return map;
	}
	
	@RequestMapping(value = "/cost/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map delete(@PathVariable("id") Integer id) {
		costRepository.deleteById(id);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "删除成功");
		return map;
	}

}
