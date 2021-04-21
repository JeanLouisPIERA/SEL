package com.microselwebclientjspui.security;

/*import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microselwebclientjspui.errors.NotAuthorizedException;
import com.microselwebclientjspui.objets.AccessTokenResponse;
import com.microselwebclientjspui.objets.KeycloakUser;*/

/*@Service*/
public class AuthService {
	/*
	 * // Constants
	 * 
	 * @Value("${keycloak.auth-server-url}") private String keycloakUrl;
	 * 
	 * @Value("${keycloak.realm}") private String keycloakRealm;
	 * 
	 * @Value("${keycloakclientId}") private String keycloakClientId;
	 * 
	 * @Value("${keycloak.credentials.secret}") private String clientSecret;
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); private static final String
	 * BEARER = "BEARER ";
	 * 
	 * public AccessTokenResponse login(KeycloakUser user) { try { String uri =
	 * keycloakUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
	 * String data = "grant_type=password&username=" + user.getUsername() +
	 * "&password=" + user.getPassword() + "&client_id=" + keycloakClientId +
	 * "&client_secret=" + clientSecret;
	 * 
	 * System.out.println("usernameTest = " + user.getUsername());
	 * System.out.println("passwordTest = " + user.getPassword());
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.set("Content-Type",
	 * "application/x-www-form-urlencoded");
	 * 
	 * HttpEntity<String> entity = new HttpEntity<String>(data, headers);
	 * ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(uri,
	 * HttpMethod.POST, entity, AccessTokenResponse.class);
	 * 
	 * if (response.getStatusCode().value() != HttpStatus.SC_OK) {
	 * System.out.println("Unauthorised access to protected resource = " +
	 * response.getStatusCode().value()); throw new
	 * NotAuthorizedException("Unauthorised access to protected resource"); } return
	 * response.getBody(); } catch (Exception ex) {
	 * System.out.println("Unauthorised access to protected resource = " + ex);
	 * throw new
	 * NotAuthorizedException("Unauthorised access to protected resource"); } }
	 * 
	 * public Boolean isValid(String authToken) throws NotAuthorizedException {
	 * String uri = keycloakUrl + "/realms/" + keycloakRealm +
	 * "/protocol/openid-connect/token/introspect";
	 * 
	 * String data = "&client_id=" + keycloakClientId + "&client_secret=" +
	 * clientSecret + "&token_type_hint=access_token" + "&token=" + authToken;
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.set("Content-Type",
	 * "application/x-www-form-urlencoded");
	 * 
	 * HttpEntity<String> entity = new HttpEntity<String>(data, headers);
	 * System.out.println("Token info: {} =" + entity);
	 * 
	 * ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(uri,
	 * HttpMethod.POST, entity, AccessTokenResponse.class);
	 * 
	 * if (response.getStatusCode().value() != HttpStatus.SC_OK) {
	 * System.out.println("Unauthorised access to protected resource =" +
	 * response.getStatusCode().value()); throw new
	 * NotAuthorizedException("Unauthorised access to protected resource"); }
	 * 
	 * return response.getBody().isActive(); }
	 */

}
