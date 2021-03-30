package com.microselwebclientjspui.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
/*import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;*/
import org.springframework.stereotype.Service;

import com.microselwebclientjspui.objets.KeycloakClientCredentialsRestTemplate;

/*@Service*/
public class KeycloakClientCredentialsConfig {

	/*
	 * @Value("${keycloak.realm}") private String realm;
	 * 
	 * @Value("${keycloak.auth-server-url}") private String authServerUrl;
	 * 
	 * @Value("${keycloakclientId}") private String clientId;
	 * 
	 * @Value("${keycloak.credentials.secret}") private String clientSecret;
	 * 
	 * @Bean public KeycloakClientCredentialsRestTemplate createRestTemplate() {
	 * return new
	 * KeycloakClientCredentialsRestTemplate(getClientCredentialsResourceDetails(),
	 * new DefaultOAuth2ClientContext()); }
	 * 
	 * private ClientCredentialsResourceDetails
	 * getClientCredentialsResourceDetails() {
	 * 
	 * String accessTokenUri =
	 * String.format("%s/realms/%s/protocol/openid-connect/token", authServerUrl,
	 * realm); List<String> scopes = new ArrayList<String>(0); // TODO introduce
	 * scopes
	 * 
	 * 
	 * List<String> scopes = new ArrayList<String>(0); scopes.add("email profile");
	 * 
	 * ClientCredentialsResourceDetails clientCredentialsResourceDetails = new
	 * ClientCredentialsResourceDetails();
	 * 
	 * clientCredentialsResourceDetails.setAccessTokenUri(
	 * "http://localhost:8180/auth/realms/microsel-realm/protocol/openid-connect/token"
	 * );
	 * clientCredentialsResourceDetails.setAuthenticationScheme(AuthenticationScheme
	 * .header); clientCredentialsResourceDetails.setClientId(clientId);
	 * clientCredentialsResourceDetails.setClientSecret(clientSecret);
	 * clientCredentialsResourceDetails.setScope(scopes);
	 * 
	 * return clientCredentialsResourceDetails; }
	 */

}
