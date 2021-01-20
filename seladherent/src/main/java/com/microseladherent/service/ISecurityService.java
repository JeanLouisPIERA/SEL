package com.microseladherent.service;

import com.microseladherent.entities.User;

public interface ISecurityService {
	
	String findLoggedInUsername();
	
	User findLoggedInUser();
	
	User autologin(String username, String password);

}
