package com.teamcubation.reportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class ReportserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReportserviceApplication.class, args);
	}

}
