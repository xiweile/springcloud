package com.weiller.springcloudprivider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudPrividerApp_8001 {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudPrividerApp_8001.class, args);
	}
}
