package com.persistent.deskManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeskManagementApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(DeskManagementApplication.class, args);
		
		System.out.println("hello saurabh");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DeskManagementApplication.class);
	}
}
