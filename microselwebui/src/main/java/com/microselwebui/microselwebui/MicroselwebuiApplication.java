package com.microselwebui.microselwebui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.microselwebui")
public class MicroselwebuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroselwebuiApplication.class, args);
	}

}
