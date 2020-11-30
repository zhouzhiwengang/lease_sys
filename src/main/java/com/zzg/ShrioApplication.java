package com.zzg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.zzg.spring.SpringContextUtil;


@SpringBootApplication
public class ShrioApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ApplicationContext applicationContext = SpringApplication.run(ShrioApplication.class, args);
		 SpringContextUtil.setApplicationContext(applicationContext);
	}

}
