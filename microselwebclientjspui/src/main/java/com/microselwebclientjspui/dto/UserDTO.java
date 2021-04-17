package com.microselwebclientjspui.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.microselwebclientjspui.constraints.FieldValidation;






@FieldValidation.List({
    @FieldValidation(first = "password", second = "passwordConfirm", message = "Valeur incorrecte : vous devez confirmer votre mot de passe"),
})
public class UserDTO {
	
	/*
	 * @NotEmpty(message="Merci de saisir votre identifiant")
	 * 
	 * @Size(min = 5, max = 25, message =
	 * "Votre identifiant doit contenir entre 5 et 25 caractères") private String
	 * username;
	 * 
	 * 
	 * @NotEmpty(message="Merci de saisir votre e-mail") private String adresseMail;
	 * 
	 * 
	 * @NotEmpty(message="Merci de saisir votre mot de passe")
	 * 
	 * @Size(min = 5, max = 25, message =
	 * "Votre identifiant doit contenir entre 5 et 25 caractères") private String
	 * password;
	 * 
	 * 
	 * @NotEmpty(message="Merci de confirmer votre mot de passe")
	 * 
	 * @Size(min = 5, max = 25, message =
	 * "Votre identifiant doit contenir entre 5 et 25 caractères") private String
	 * passwordConfirm;
	 * 
	 * 
	 * @AssertTrue
	 * (message="Vous devez accepter nos conditions générales d'utilisation pour valider votre demande d'adhésion"
	 * ) private Boolean terms;
	 * 
	 * 
	 * 
	 * public UserDTO() { super(); }
	 * 
	 * 
	 * public String getUsername() { return username; }
	 * 
	 * 
	 * public void setUsername(String username) { this.username = username; }
	 * 
	 * 
	 * public String getAdresseMail() { return adresseMail; }
	 * 
	 * 
	 * public void setAdresseMail(String adresseMail) { this.adresseMail =
	 * adresseMail; }
	 * 
	 * 
	 * public String getPassword() { return password; }
	 * 
	 * 
	 * public void setPassword(String password) { this.password = password; }
	 * 
	 * 
	 * public String getPasswordConfirm() { return passwordConfirm; }
	 * 
	 * 
	 * public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm
	 * = passwordConfirm; }
	 * 
	 * 
	 * 
	 * public Boolean getTerms() { return terms; }
	 * 
	 * 
	 * public void setTerms(Boolean terms) { this.terms = terms; }
	 */
  
	/*
	 * @Override public String toString() { return "UserDTO [username=" + username +
	 * ", adresseMail=" + adresseMail + ", password=" + password +
	 * ", passwordConfirm=" + passwordConfirm + "]"; }
	 * 
	 */

/*
 * @Override public String toString() { return "UserDTO [username=" + username +
 * ", adresseMail=" + adresseMail + ", password=" + password +
 * ", passwordConfirm=" + passwordConfirm + "]"; }
 */







}


