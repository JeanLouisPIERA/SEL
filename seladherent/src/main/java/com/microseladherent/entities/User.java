package com.microseladherent.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="users")
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
	
	
	@Column(name = "username", length = 100, nullable=false, unique=true)
	private String username;
	
	@Column(name = "password", length= 25, nullable=false)
	private String password;
	
	@Transient
	@JsonIgnore
	private String passwordConfirm;
	
	
	@Column(name = "email", length = 100, nullable=false, unique=true)
	private String email;
	
	@Column(name = "statut", length = 5, nullable=false)
	private UserStatutEnum statut;
	
	@Column(name = "date_adhesion", nullable=false)
	private LocalDate dateAdhesion;
	
	
	@Column(name = "date_cloture_debut", nullable=true)
	private LocalDate dateClotureDebut;
	
	@Column(name = "date_cloture_fin", nullable=true)
	private LocalDate dateClotureFin;
	
	
	@Column(name="date_blocage_debut", nullable=true)
	private LocalDate dateBlocageDebut;
	
	
	@Column(name="date_blocage_fin", nullable=true)
	private LocalDate dateBlocageFin;
	
	@Column(name="date_bureau_debut", nullable=true)
	private LocalDate dateBureauDebut;
	
	@Column(name="date_bureau_fin", nullable=true)
	private LocalDate dateBureauFin;
	
	@Column(name="date_admin_debut", nullable=true)
	private LocalDate dateAdminDebut;
	
	
	@Column(name="date_admin_fin", nullable=true)
	private LocalDate dateAdminFin;
	
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="role_id") private Role role;
	 * 
	 * private Role role
	 */
	 @JsonIgnore 
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "users_roles", 
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Role> roles = new ArrayList<Role>();
	
	
	public User() {
		super();
	}
	
	

	public User(Long id, String username, String password, String passwordConfirm, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.email = email;
	}



	public User(String username, String password, String email, UserStatutEnum statut, LocalDate dateAdhesion) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.statut = statut;
		this.dateAdhesion = dateAdhesion;
	}


	public User(Long id, String username, String password, String email, UserStatutEnum statut,
			LocalDate dateAdhesion) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.statut = statut;
		this.dateAdhesion = dateAdhesion;
	}



	public User(Long id, String username, String password, String passwordConfirm, String email, UserStatutEnum statut,
			LocalDate dateAdhesion) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.email = email;
		this.statut = statut;
		this.dateAdhesion = dateAdhesion;
	}
	
	public User(Long id, String username, String password, String passwordConfirm, String email, UserStatutEnum statut,
			LocalDate dateAdhesion, LocalDate dateClotureDebut, LocalDate dateClotureFin, LocalDate dateBlocageDebut,
			LocalDate dateBlocageFin) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.email = email;
		this.statut = statut;
		this.dateAdhesion = dateAdhesion;
		this.dateClotureDebut = dateClotureDebut;
		this.dateClotureFin = dateClotureFin;
		this.dateBlocageDebut = dateBlocageDebut;
		this.dateBlocageFin = dateBlocageFin;
	}

	public User(Long id, String username, String password, String passwordConfirm, String email, UserStatutEnum statut,
			LocalDate dateAdhesion, LocalDate dateClotureDebut, LocalDate dateClotureFin, LocalDate dateBlocageDebut,
			LocalDate dateBlocageFin, List<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.email = email;
		this.statut = statut;
		this.dateAdhesion = dateAdhesion;
		this.dateClotureDebut = dateClotureDebut;
		this.dateClotureFin = dateClotureFin;
		this.dateBlocageDebut = dateBlocageDebut;
		this.dateBlocageFin = dateBlocageFin;
		this.roles = roles;
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

	public UserStatutEnum getStatut() {
		return statut;
	}

	public void setStatut(UserStatutEnum statut) {
		this.statut = statut;
	}

	public LocalDate getDateAdhesion() {
		return dateAdhesion;
	}

	public void setDateAdhesion(LocalDate dateAdhesion) {
		this.dateAdhesion = dateAdhesion;
	}

	public LocalDate getDateClotureDebut() {
		return dateClotureDebut;
	}

	public void setDateClotureDebut(LocalDate dateClotureDebut) {
		this.dateClotureDebut = dateClotureDebut;
	}

	public LocalDate getDateClotureFin() {
		return dateClotureFin;
	}

	public void setDateClotureFin(LocalDate dateClotureFin) {
		this.dateClotureFin = dateClotureFin;
	}

	public LocalDate getDateBlocageDebut() {
		return dateBlocageDebut;
	}

	public void setDateBlocageDebut(LocalDate dateBlocageDebut) {
		this.dateBlocageDebut = dateBlocageDebut;
	}

	public LocalDate getDateBlocageFin() {
		return dateBlocageFin;
	}

	public void setDateBlocageFin(LocalDate dateBlocageFin) {
		this.dateBlocageFin = dateBlocageFin;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	

	public LocalDate getDateBureauDebut() {
		return dateBureauDebut;
	}



	public void setDateBureauDebut(LocalDate dateBureauDebut) {
		this.dateBureauDebut = dateBureauDebut;
	}



	public LocalDate getDateBureauFin() {
		return dateBureauFin;
	}



	public void setDateBureauFin(LocalDate dateBureauFin) {
		this.dateBureauFin = dateBureauFin;
	}



	public LocalDate getDateAdminDebut() {
		return dateAdminDebut;
	}



	public void setDateAdminDebut(LocalDate dateAdminDebut) {
		this.dateAdminDebut = dateAdminDebut;
	}



	public LocalDate getDateAdminFin() {
		return dateAdminFin;
	}



	public void setDateAdminFin(LocalDate dateAdminFin) {
		this.dateAdminFin = dateAdminFin;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", email=" + email + ", statut=" + statut + ", dateAdhesion=" + dateAdhesion
				+ ", dateClotureDebut=" + dateClotureDebut + ", dateClotureFin=" + dateClotureFin
				+ ", dateBlocageDebut=" + dateBlocageDebut + ", dateBlocageFin=" + dateBlocageFin + ", dateBureauDebut="
				+ dateBureauDebut + ", dateBureauFin=" + dateBureauFin + ", dateAdminDebut=" + dateAdminDebut
				+ ", dateAdminFin=" + dateAdminFin + ", roles=" + roles + "]";
	}



	

	
	
}
