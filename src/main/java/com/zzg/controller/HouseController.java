package com.zzg.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zzg.dao.HouseRepository;
import com.zzg.entity.House;


@RestController
public class HouseController {
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
	
	@RequestMapping(value = "/house/update", method = RequestMethod.POST)
	@ResponseBody
	public Map update(@RequestBody House house) {
		houseRepository.save(house);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "更新成功");
		return map;
	}
	
	@RequestMapping(value = "/house/findPage", method = RequestMethod.GET)
	@ResponseBody
	public Map findPage() {
		//显示第1页每页显示3条
		PageRequest pr = new PageRequest(1,1);
		//根据年龄进行查询
		Page<House> stus = houseRepository.findAll(pr); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		map.put("message", "查询成功");
		map.put("date", stus);
		return map;
	}

}
