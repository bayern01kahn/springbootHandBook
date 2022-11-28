package com.justin.springbootHandBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
//@SpringBootApplication(exclude = {
//		DataSourceAutoConfiguration.class
//})
public class SpringBootHandBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHandBookApplication.class, args);
	}

}
