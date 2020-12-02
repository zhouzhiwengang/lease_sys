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
import com.zzg.dao.RoleRepository;
import com.zzg.entity.Role;

@RestController
public class RoleController extends AbstractController {
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	@ResponseBody
	public Map insert(@RequestBody Role role) {
		roleRepository.save(role);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "新增成功");
		return map;
	}
	
	/**
	 * 动态更新:仅限于更新单表字段
	 * @param house
	 * @return
	 */
	@RequestMapping(value = "/role/update", method = RequestMethod.POST)
	@ResponseBody
	public Map update(@RequestBody Role role) {
		Optional<Role> old = roleRepository.findById(role.getRoleId());
		if(old.isPresent()){
			Role oldRole = old.get();
            //将传过来的 house 中的非NULL属性值复制到 oldHouse 中
            copyPropertiesIgnoreNull(role, oldRole);
            //将得到的新的 oldHouse 对象重新保存到数据库，因为数据库中已经存在该记录
            //所以JPA会很智能的 改为更新操作，更新数据库
            roleRepository.save(oldRole);
        }
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "更新成功");
		return map;
	}
	
	@RequestMapping(value = "/role/findPage", method = RequestMethod.POST)
	@ResponseBody
	public Map findPage(@RequestBody Map<String, Object> paramter) {
		//显示第1页每页显示3条
		PageRequest pr = super.initPageBounds(paramter);
	
		Page<Role> stus = roleRepository.findAll(new Specification<Role>(){
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
	
	@RequestMapping(value = "/role/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map delete(@PathVariable("id") Integer id) {
		roleRepository.deleteById(id);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "删除成功");
		return map;
	}

}
