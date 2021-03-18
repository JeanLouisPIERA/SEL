package com.microselreferentiels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients("com.microselreferentiels")
public class MicroselreferentielsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroselreferentielsApplication.class, args);
	}

}
