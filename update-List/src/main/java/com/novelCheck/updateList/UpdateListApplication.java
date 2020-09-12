package com.novelCheck.updateList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class UpdateListApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateListApplication.class, args);
	}

}
