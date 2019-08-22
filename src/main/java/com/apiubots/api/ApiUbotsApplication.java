package com.apiubots.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.apiubots.api", "controller", "service"})
@SpringBootApplication
public class ApiUbotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiUbotsApplication.class, args);
	}

}
