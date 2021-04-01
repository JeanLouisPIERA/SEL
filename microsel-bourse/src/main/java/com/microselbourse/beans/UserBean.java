package com.microselbourse.beans;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;



public class UserBean {
	
	
	private Long id;
	
	/*
	 * Long sel;
	 */
	
	private String username;
	
	private String password;
	
	private String passwordConfirm;
	
	private String email;
	
	private UserStatutEnum statut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateAdhesion;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateClotureDebut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateClotureFin;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateBlocageDebut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateBlocageFin;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateBureauDebut;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateBureauFin;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateAdminDebut;

	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")	
	private LocalDate dateAdminFin;

	public UserBean() {
		super();
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
		return "UserBean [id=" + id + ", username=" + username + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", email=" + email + ", statut=" + statut + ", dateAdhesion=" + dateAdhesion
				+ ", dateClotureDebut=" + dateClotureDebut + ", dateClotureFin=" + dateClotureFin
				+ ", dateBlocageDebut=" + dateBlocageDebut + ", dateBlocageFin=" + dateBlocageFin + ", dateBureauDebut="
				+ dateBureauDebut + ", dateBureauFin=" + dateBureauFin + ", dateAdminDebut=" + dateAdminDebut
				+ ", dateAdminFin=" + dateAdminFin + "]";
	}

	
	

}