package com.microselwebui.microselwebui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microselwebui.microselwebui.errors.CustomErrorDecoder;

@Configuration
public class FeignExceptionConfig {

    @Bean
    public CustomErrorDecoder mCustomErrorDecoder(){
        return new CustomErrorDecoder();
    }
}

