package com.weiller.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients(basePackages = "com.weiller.api")
@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer // 向微服务表明这是一个受保护的资源
public class ProductApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
}
