package com.seladherent.seladherent.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="user")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "ID de l'utilisateur generee dans la base de donnees")
	private Long idUser;
	@ApiModelProperty(notes= "Nom de l'utilisateur")
	private String username;
	@JsonIgnore
	private String password;
	@Transient
	@JsonIgnore
	private String passwordConfirm;
	@ApiModelProperty(notes= "Adresse mail de l'utilisateur")
	private String adresseMail;
	@JsonIgnore
	@ManyToMany
	@JoinTable(
	        name = "User_Role", 
	        joinColumns = { @JoinColumn(name = "user_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "role_id") }
	    )
	private Set<Role> roles = new HashSet<>();
	
	public User() {
		super();
	}
	
	public User(Long idUser, String username, String password, String passwordConfirm, String adresseMail,
			Set<Role> roles) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.adresseMail = adresseMail;
		this.roles = roles;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getAdresseMail() {
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", username=" + username + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", adresseMail=" + adresseMail + ", roles=" + roles + "]";
	}
    
	
}
