package com.microseladherent.dto;

public class UserDTO {
	
	private String username;
	private String adresseMail;
	private String password;
	private String passwordConfirm;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAdresseMail() {
		return adresseMail;
	}
	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
