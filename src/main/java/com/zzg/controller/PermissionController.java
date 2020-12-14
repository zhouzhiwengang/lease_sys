package com.zzg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zzg.common.AbstractController;
import com.zzg.dao.PermissionRepository;
import com.zzg.dao.RoleRepository;
import com.zzg.dao.UserRepository;
import com.zzg.entity.Permission;
import com.zzg.entity.Role;
import com.zzg.entity.User;
import com.zzg.vo.PermissionVo;

@RestController
public class PermissionController extends AbstractController{
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	
	@RequestMapping(value = "/permission/save", method = RequestMethod.POST)
	@ResponseBody
	public Map insert(@RequestBody Permission permission) {
		permissionRepository.save(permission);
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
	@RequestMapping(value = "/permission/update", method = RequestMethod.POST)
	@ResponseBody
	public Map update(@RequestBody Permission permission) {
		Optional<Permission> old = permissionRepository.findById(permission.getPermissionId());
		if(old.isPresent()){
			Permission oldPermission = old.get();
            //将传过来的 house 中的非NULL属性值复制到 oldHouse 中
            copyPropertiesIgnoreNull(permission, oldPermission);
            //将得到的新的 oldHouse 对象重新保存到数据库，因为数据库中已经存在该记录
            //所以JPA会很智能的 改为更新操作，更新数据库
            permissionRepository.save(oldPermission);
        }
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "更新成功");
		return map;
	}
	
	@RequestMapping(value = "/permission/findPage", method = RequestMethod.POST)
	@ResponseBody
	public Map findPage(@RequestBody Map<String, Object> paramter) {
		//显示第1页每页显示3条
		PageRequest pr = super.initPageBounds(paramter);
		
		Page<Permission> stus = permissionRepository.findAll(new Specification<Permission>(){
			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
	
	/**
	 * 查询用户指定权限
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/permission/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public Map findPermission(@PathVariable Integer uid) {
		List<PermissionVo> permissionsVos = new ArrayList<PermissionVo>();
		
		List<Permission> permissions = new ArrayList<Permission>();
		//显示第1页每页显示3条
		User user = userRepository.findByUserId(uid);
		
		user.getRoles().stream().forEach(item->{
        	Role role = roleRepository.findByRoleId(item.getRoleId());
        	if(!CollectionUtils.isEmpty(role.getPermissions())){
        		for(Permission permission : role.getPermissions()){
        			if(permission.getPermisionParentId() == null){
        				permissions.add(permission);
        			}
        		}
        	}
        });
		
	
		
		
		permissions.stream().forEach(item ->{
			 
			 /**
			  * Spring Data JPA 动态查询条件
			  */
			 Specification<Permission> specification = new Specification<Permission>() {
	             @Override
	            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
	                List<Predicate> predicates = new ArrayList<Predicate>();
	                if(null != item.getPermissionId()) {
	                    predicates.add(cb.equal(root.<Integer>get("permisionParentId"), item.getPermissionId()));
	                }
	                return cq.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
	            }
	        };
	        List<Permission> childPermission = permissionRepository.findAll(specification);
	        List<PermissionVo> childPermissionVO = new ArrayList<PermissionVo>();
	        for(Permission permission : childPermission){
	        	 PermissionVo vo = new PermissionVo();
	        	 BeanUtils.copyProperties(permission, vo);
	        	 childPermissionVO.add(vo);
	        }
	        
	        PermissionVo vo = new PermissionVo();
	        BeanUtils.copyProperties(item, vo);
	        vo.setChild(childPermissionVO);
	        
	        permissionsVos.add(vo);
	        
		});
		 
		
		// 数据清理
//		Iterator<Permission> iterator = permissions.iterator();
//		while(iterator.hasNext()){
//			Permission permission = iterator.next();
//			if(permission.getPermisionParentId() != null){
//				iterator.remove();
//			}
//		}
		
		

//		for(Permission permission : permissions){
//			if(permission.getParent() != null){
//				permissions.remove(permission);
//			}
//		}
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "查询成功");
		map.put("date", permissionsVos);
		return map;
	}
	
	@RequestMapping(value = "/permission/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map delete(@PathVariable("id") Integer id) {
		permissionRepository.deleteById(id);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "删除成功");
		return map;
	}

}
