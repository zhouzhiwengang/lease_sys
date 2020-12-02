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
import com.zzg.dao.LeaseRepository;
import com.zzg.entity.Lease;

@RestController
public class LeaseController extends AbstractController {
	@Autowired
	private LeaseRepository leaseRepository;
	
	@RequestMapping(value = "/lease/save", method = RequestMethod.POST)
	@ResponseBody
	public Map insert(@RequestBody Lease lease) {
		leaseRepository.save(lease);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "新增成功");
		return map;
	}
	
	/**
	 * 动态更新:仅限于更新单表字段和和多对多表关系
	 * @param house
	 * @return
	 */
	@RequestMapping(value = "/lease/update", method = RequestMethod.POST)
	@ResponseBody
	public Map update(@RequestBody Lease lease) {
		Optional<Lease> old = leaseRepository.findById(lease.getLeaseId());
		if(old.isPresent()){
			Lease oldLease = old.get();
            //将传过来的 house 中的非NULL属性值复制到 oldHouse 中
            copyPropertiesIgnoreNull(lease, oldLease);
            //将得到的新的 oldHouse 对象重新保存到数据库，因为数据库中已经存在该记录
            //所以JPA会很智能的 改为更新操作，更新数据库
            leaseRepository.save(oldLease);
        }
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "更新成功");
		return map;
	}
	

	@RequestMapping(value = "/lease/findPage", method = RequestMethod.POST)
	@ResponseBody
	public Map findPage(@RequestBody Map<String, Object> paramter) {
		//显示第1页每页显示3条
		PageRequest pr = super.initPageBounds(paramter);
		
		Page<Lease> stus = leaseRepository.findAll(new Specification<Lease>(){
			@Override
			public Predicate toPredicate(Root<Lease> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
	
	@RequestMapping(value = "/lease/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map delete(@PathVariable("id") Integer id) {
		leaseRepository.deleteById(id);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "删除成功");
		return map;
	}
	
	

}
