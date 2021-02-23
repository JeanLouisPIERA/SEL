package com.microselwebclient_ui.service.impl;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class HttpHeadersFactory {
	
	/*
	 * public HttpHeaders createHeaders(String username, String password){ return
	 * new HttpHeaders() {{ String auth = username + ":" + password; byte[]
	 * encodedAuth = Base64.encodeBase64( auth.getBytes(Charset.forName("US-ASCII"))
	 * ); String authHeader = "Basic " + new String( encodedAuth ); set(
	 * "Authorization", authHeader ); }};
	 * 
	 * }
	 */

}
