package com.microseladherent.service;

import java.util.Optional;

import com.microseladherent.entities.User;

public interface ISecurityService {
	
	String findLoggedInUsername();
	
	User findLoggedInUser();
	
	Optional<User> autologin(String username, String password);

}
