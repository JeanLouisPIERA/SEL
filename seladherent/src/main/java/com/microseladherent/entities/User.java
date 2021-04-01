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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
	
	
	
	@Column(name = "first_name", length = 25, nullable=false)
	private String firstname;
	
	@Column(name = "last_name", length = 25, nullable=false)
	private String lastname;
	/*
	 * @Column(name = "status_code", length = 3) private long statusCode;
	 */
	
	@Column(name = "status", length = 25)
	private String status;
	
	
	
	
	@Column(name = "statut", length = 5, nullable=false)
	private UserStatutEnum statut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_adhesion", nullable=false)
	private LocalDate dateAdhesion;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_cloture_debut", nullable=true)
	private LocalDate dateClotureDebut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_cloture_fin", nullable=true)
	private LocalDate dateClotureFin;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name="date_blocage_debut", nullable=true)
	private LocalDate dateBlocageDebut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name="date_blocage_fin", nullable=true)
	private LocalDate dateBlocageFin;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name="date_bureau_debut", nullable=true)
	private LocalDate dateBureauDebut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name="date_bureau_fin", nullable=true)
	private LocalDate dateBureauFin;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name="date_admin_debut", nullable=true)
	private LocalDate dateAdminDebut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	 
	 
	
}
	