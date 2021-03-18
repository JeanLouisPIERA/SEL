package com.microselwebclientjspui;

import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
/*import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;*/
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients("com.microselwebclientjspui")
public class MicroselwebclientjspuiApplication {

	/*
	 * @LoadBalanced
	 * 
	 * @Bean public RestTemplate getRestTemplate() {
	 * 
	 * return new RestTemplate(); }
	 */

	
	public static void main(String[] args) {
		SpringApplication.run(MicroselwebclientjspuiApplication.class, args);
	}
	
	@Bean 
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
    RestTemplate builtRestTemplate = builder.build(); 
    
		return builtRestTemplate;
	}
	
	
	
}
