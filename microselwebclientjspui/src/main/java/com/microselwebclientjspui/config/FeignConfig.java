package com.microselwebclientjspui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignConfig {
	
	@Value("${zuul.u}")
	private String zuulu;
	
	@Value("${zuul.p}")
	private String zuulp;
	
	 @Bean
	    public BasicAuthRequestInterceptor mBasicAuthRequestInterceptor(){
	        return  new BasicAuthRequestInterceptor(zuulu, zuulp);
	    }

}
