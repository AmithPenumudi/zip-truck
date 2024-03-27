package com.ziptruck.truckservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class TruckServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruckServiceApplication.class, args);
	}

}
