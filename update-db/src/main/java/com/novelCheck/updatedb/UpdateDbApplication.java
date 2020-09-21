package com.novelCheck.updatedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.novelCheck.updatedb.repository")
@SpringBootApplication
public class UpdateDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateDbApplication.class, args);
	}

}
