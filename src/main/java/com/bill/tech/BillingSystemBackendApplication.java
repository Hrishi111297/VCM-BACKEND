package com.bill.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class BillingSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingSystemBackendApplication.class, args);
		
	}

}
