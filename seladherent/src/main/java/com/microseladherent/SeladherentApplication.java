package com.microseladherent;

/*import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/*import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;*/
import org.springframework.context.annotation.Bean;

@SpringBootApplication
/* @EnableWebSecurity */
@EnableConfigurationProperties
@EnableDiscoveryClient
public class SeladherentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeladherentApplication.class, args);
	}
	
	/*
	 * @Bean public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
	 * return new KeycloakSpringBootConfigResolver(); }
	 */

}
