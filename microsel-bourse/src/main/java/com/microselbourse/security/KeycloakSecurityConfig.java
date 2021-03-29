package com.microselbourse.security;

/*import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;*/

/*@KeycloakConfiguration*/
public class KeycloakSecurityConfig 
	
	  /*extends KeycloakWebSecurityConfigurerAdapter{
	  
	  @Override protected SessionAuthenticationStrategy
	  sessionAuthenticationStrategy() { return new
	  RegisterSessionAuthenticationStrategy(new SessionRegistryImpl()); }
	  
	  @Override protected void configure(AuthenticationManagerBuilder auth) throws
	  Exception { auth.authenticationProvider(keycloakAuthenticationProvider()); }
	  
	  @Override protected void configure(HttpSecurity http) throws Exception {
	  super.configure(http); http.authorizeRequests()
	  .antMatchers("/sel/bourse/propositions", "/sel/bourse/echanges").permitAll()
	  .antMatchers("/sel/bourse/propositions/create",
	  "/sel/bourse/propositions/update","/sel/bourse/propositions/close",
	  "/reponses/newReponse/**").hasAuthority("USER")
	  .antMatchers("/wallets/**","/evaluations/echange/**").hasAuthority("BUREAU")
	  .antMatchers("/referentiels", "/referentiels/**").hasAuthority("ADMIN") ;
	  
	  }
	 
										 */
{	

}
