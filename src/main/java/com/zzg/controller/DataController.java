package com.zzg.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	@RequiresPermissions({"save"}) //没有的话 AuthorizationException
    @PostMapping("/data/save")
 	@ResponseBody
    public Map<String, Object> save(@RequestHeader("token")String token) {
        System.out.println("save");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有save的权力");
        return map;
    }//f603cd4348b8f1d41226e3f555d392bd

    @RequiresPermissions({"delete"}) //没有的话 AuthorizationException
    @DeleteMapping("/data/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestHeader("token")String token) {
        System.out.println("delete");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有delete的权力");
        return map;
    }

    @RequiresPermissions({"update"}) //没有的话 AuthorizationException
    @PutMapping("/data/update")
    @ResponseBody
    public Map<String, Object> update(@RequestHeader("token")String token) {
        System.out.println("update");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有update的权力");
        return map;
    }

    @RequiresPermissions({"select"}) //没有的话 AuthorizationException
    @GetMapping("/data/select")
    @ResponseBody
    public Map<String, Object> select(@RequestHeader("token")String token) {
        System.out.println("select");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有select的权力");
        return map;
    }

    @RequiresRoles({"vip"}) //没有的话 AuthorizationException
    @GetMapping("/data/vip")
    @ResponseBody
    public Map<String, Object> vip(@RequestHeader("token")String token) {
        System.out.println("vip");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有VIP角色");
        return map;
    }
    @RequiresRoles({"svip"}) //没有的话 AuthorizationException
    @GetMapping("/data/svip")
    @ResponseBody
    public Map<String, Object> svip(@RequestHeader("token")String token) {
        System.out.println("svip");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有SVIP角色");
        return map;
    }
    @RequiresRoles({"p"}) //没有的话 AuthorizationException
    @GetMapping("/data/p")
    @ResponseBody
    public Map<String, Object> p(@RequestHeader("token")String token) {
        System.out.println("p");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        map.put("msg", "当前用户有P角色");
        return map;
    }
}
