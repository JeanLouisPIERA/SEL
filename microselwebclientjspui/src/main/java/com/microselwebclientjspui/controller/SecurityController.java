package com.microselwebclientjspui.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/accueil";
		
	}
	
	@GetMapping("/login")
	public String login() {
		return "redirect:/accueil";
		
	}

}
