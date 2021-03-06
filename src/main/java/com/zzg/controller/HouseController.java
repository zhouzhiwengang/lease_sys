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
import com.zzg.dao.HouseRepository;
import com.zzg.entity.House;


@RestController
public class HouseController extends AbstractController{
	@Autowired
	HouseRepository houseRepository;
	
	@RequestMapping(value = "/house/save", method = RequestMethod.POST)
	@ResponseBody
	public Map insert(@RequestBody House house) {
		houseRepository.save(house);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "新增成功");
		return map;
	}
	
	/**
	 * 动态更新:仅限于更新单表字段和多对多表关系，产生原因：由于@Many-To-One
	 * @param house
	 * @return
	 */
	@RequestMapping(value = "/house/update", method = RequestMethod.POST)
	@ResponseBody
	public Map update(@RequestBody House house) {
		Optional<House> old = houseRepository.findById(house.getHouseId());
		if(old.isPresent()){
			House oldHouse = old.get();
            //将传过来的 house 中的非NULL属性值复制到 oldHouse 中
            copyPropertiesIgnoreNull(house, oldHouse);
            //将得到的新的 oldHouse 对象重新保存到数据库，因为数据库中已经存在该记录
            //所以JPA会很智能的 改为更新操作，更新数据库
            houseRepository.save(oldHouse);
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
	@RequestMapping(value = "/house/userId", method = RequestMethod.GET)
	@ResponseBody
	public Map updateUserId(Integer userId,Integer houseId) {
		houseRepository.modifyUserId(userId, houseId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "更新成功");
		return map;
	}
	
	@RequestMapping(value = "/house/findPage", method = RequestMethod.POST)
	@ResponseBody
	public Map findPage(@RequestBody Map<String, Object> paramter) {
		//显示第1页每页显示3条
		PageRequest pr = super.initPageBounds(paramter);
		
		Page<House> stus = houseRepository.findAll(new Specification<House>(){
			@Override
			public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicateList = new ArrayList<>();
				if(paramter.get("houseId") != null){
					 predicateList.add(criteriaBuilder.equal(root.get("houseId"), paramter.get("houseId")));
				}
				if(paramter.get("houseNumber") != null){
					 predicateList.add(criteriaBuilder.equal(root.get("houseNumber"), paramter.get("houseNumber")));
				}
				if(paramter.get("area") != null){
					 predicateList.add(criteriaBuilder.like(root.get("area"), "%" + paramter.get("area") +"%"));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		}, pr);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "查询成功");
		map.put("date", stus);
		return map;
	}
	
	@RequestMapping(value = "/house/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map delete(@PathVariable("id") Integer id) {
		houseRepository.deleteById(id);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "删除成功");
		return map;
	}
	


}
