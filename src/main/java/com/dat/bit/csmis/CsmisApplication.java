package com.dat.bit.csmis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CsmisApplication extends SpringBootServletInitializer   {

	public static void main(String[] args) {
		SpringApplication.run(CsmisApplication.class, args);
	}
	
	@Override  
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)   
	{  
		return application.sources(CsmisApplication.class);  
	}   

}
