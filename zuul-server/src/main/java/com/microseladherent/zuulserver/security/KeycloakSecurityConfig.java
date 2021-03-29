package com.microseladherent.zuulserver.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.http.HttpMethod;
/*import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;*/

/*@KeycloakConfiguration*/
public class KeycloakSecurityConfig {
	
	/*
	 * extends KeycloakWebSecurityConfigurerAdapter{
	 * 
	 * @Override protected SessionAuthenticationStrategy
	 * sessionAuthenticationStrategy() { return new
	 * RegisterSessionAuthenticationStrategy(new SessionRegistryImpl()); }
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.authenticationProvider(keycloakAuthenticationProvider()); }
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * super.configure(http); http.authorizeRequests()
	 * .antMatchers("/microselbourse/sel/bourse/echanges",
	 * "/microselbourse/sel/bourse/propositions").permitAll()
	 * .antMatchers(HttpMethod.POST,
	 * "/microselbourse/sel/bourse/propositions/create").hasAuthority("ROLE_USER")
	 * .antMatchers("/microselbourse/sel/bourse/propositions/update",
	 * "/microselbourse/sel/bourse/propositions/close",
	 * "/microselbourse/reponses/newReponse/**",
	 * "/microsel-adherent/sel/adherents/users/**").hasAuthority("ROLE_USER")
	 * .antMatchers("/wallets/**","/evaluations/echange/**").hasAuthority("BUREAU")
	 * .antMatchers("/referentiels", "/referentiels/**").hasAuthority("ADMIN") ;
	 * 
	 * }
	 */
		 
											 

}
