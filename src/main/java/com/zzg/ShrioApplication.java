package com.zzg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.zzg.spring.SpringContextUtil;


@SpringBootApplication
@EntityScan("com.zzg.entity") //Spring-JPA实体扫描
@EnableJpaRepositories(basePackages = { "com.zzg.dao" }) //Spring-JPA Repository扫描
public class ShrioApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ApplicationContext applicationContext = SpringApplication.run(ShrioApplication.class, args);
		 SpringContextUtil.setApplicationContext(applicationContext);
	}

}
