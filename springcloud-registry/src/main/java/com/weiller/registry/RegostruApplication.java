package com.weiller.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegostruApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegostruApplication.class, args);
	}
}
