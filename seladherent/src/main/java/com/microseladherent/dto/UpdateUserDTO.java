package com.microseladherent.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.microseladherent.errors.IPasswordConfirmation;

import io.swagger.annotations.ApiModelProperty;



@IPasswordConfirmation.List({
    @IPasswordConfirmation(
            password = "password",
            passwordConfirm = "passwordConfirm",
            message = "La confirmation de votre mot de passe n'est pas correcte"
    )
})
public class UpdateUserDTO {
	
	@ApiModelProperty(notes= "Nouveau nom de l'utilisateur")
	@Size(min = 5, max = 25, message = "{user.name.invalid}")
	private String username;
	
	@ApiModelProperty(notes= "Nouvelle Adresse mail de l'utilisateur")
	@Email(message="{user.email.invalid}")
	private String adresseMail;
	
	@ApiModelProperty(notes= "Nouveau Mot de passe de l'utilisateur")
	private String password;
	
	@ApiModelProperty(notes= "Confirmation du nouveau mot de passe de l'utilisateur (Saisie obligatoire pour changer le mot de passe)")
	@NotEmpty(message="Merci de confirmer votre mot de passe")
	private String passwordConfirm;
	
	public UpdateUserDTO() {
		super();
	}

	public UpdateUserDTO(
			@NotEmpty(message = "Merci de saisir votre identifiant") @Size(min = 5, max = 25, message = "{user.name.invalid}") String username,
			@NotEmpty(message = "Merci de saisir votre e-mail") @Email(message = "{user.email.invalid}") String adresseMail,
			@NotEmpty(message = "Merci de saisir votre mot de passe") String password,
			@NotEmpty(message = "Merci de confirmer votre mot de passe") String passwordConfirm) {
		super();
		this.username = username;
		this.adresseMail = adresseMail;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

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
