package com.microseladherent.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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
	@Column(name = "user_id", length=5)
	private Long id;
	
	/*
	 * @ApiModelProperty(notes= "ID du SEL d'adhésion")
	 * 
	 * @Column(name = "sel_id", length = 5, nullable=false, unique=true) private
	 * Long sel;
	 */
	
	@ApiModelProperty(notes= "Nom de l'utilisateur")
	@Column(length = 25, nullable=false, unique=true)
	private String username;
	
	@JsonIgnore
	@Column(length= 255, nullable=false)
	private String password;
	
	@Transient
	@JsonIgnore
	private String passwordConfirm;
	
	@ApiModelProperty(notes= "Adresse mail de l'utilisateur")
	@Column(length = 25, nullable=false, unique=true)
	private String email;
	
	/*
	 * @ApiModelProperty(notes= "Statut du compte de l'utilisateur")
	 * 
	 * @Column(nullable=false) private UserEnum accountStatut;
	 */
	
	
	@ManyToOne
	@JoinColumn(name="role_id")
    private Role role;
	
	public User() {
		super();
	}
	


	

	public User(Long id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}





	public Role getRole() {
		return role;
	}





	public void setRole(Role role) {
		this.role = role;
	}





	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", email=" + email + ", role=" + role + "]";
	}





	

	
	
    
	
}
