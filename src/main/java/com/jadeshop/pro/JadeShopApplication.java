package com.jadeshop.pro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jadeshop.pro.dao")
public class JadeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JadeShopApplication.class, args);
	}

}
