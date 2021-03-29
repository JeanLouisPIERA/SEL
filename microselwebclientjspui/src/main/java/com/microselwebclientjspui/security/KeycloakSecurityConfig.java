package com.microselwebclientjspui.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter{
	
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(keycloakAuthenticationProvider());
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests()      
		.antMatchers("/accueil",  "/propositions", "/propositions/reponses/**","/echanges", "/echanges/evaluations/**").permitAll()
		.antMatchers("/propositions/newProposition", "/propositions/update/**","/propositions/close/**",
				"/reponses/**",
				"/echanges/confirmation/**","/echanges/annulation/**",
				"/echanges/emetteurValidation/**","/echanges/recepteurValidation/**","/echanges/emetteurRefus/**","/echanges/recepteurRefus/**",
				"/evaluations/**",
				"/transactions/**",
				"/wallets/**", 
				"/referentiels/**",
				"/users/**",
				"/login").authenticated()
		;
	}
	
	

}
