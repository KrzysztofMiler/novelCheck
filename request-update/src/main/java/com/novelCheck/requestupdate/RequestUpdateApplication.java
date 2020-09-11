package com.novelCheck.requestupdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class RequestUpdateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestUpdateApplication.class, args);
	}

}
