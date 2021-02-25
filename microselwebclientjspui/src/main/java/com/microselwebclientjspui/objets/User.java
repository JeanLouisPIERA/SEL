package com.microselwebclientjspui.objets;

import java.time.LocalDate;



public class User {
	
	
	private Long id;
	
	/*
	 * Long sel;
	 */
	
	private String username;
	
	private String password;
	
	private String passwordConfirm;
	
	private String email;
	
	private UserStatutEnum statut;
	
	private LocalDate dateAdhesion;
	
	private LocalDate dateClotureDebut;
	
	private LocalDate dateClotureFin;
	
	private LocalDate dateBlocageDebut;
	
	private LocalDate dateBlocageFin;
	
	private LocalDate dateBureauDebut;
	
	private LocalDate dateBureauFin;

	private LocalDate dateAdminDebut;

	private LocalDate dateAdminFin;

	public User() {
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
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", email=" + email + ", statut=" + statut + ", dateAdhesion=" + dateAdhesion
				+ ", dateClotureDebut=" + dateClotureDebut + ", dateClotureFin=" + dateClotureFin
				+ ", dateBlocageDebut=" + dateBlocageDebut + ", dateBlocageFin=" + dateBlocageFin + ", dateBureauDebut="
				+ dateBureauDebut + ", dateBureauFin=" + dateBureauFin + ", dateAdminDebut=" + dateAdminDebut
				+ ", dateAdminFin=" + dateAdminFin + "]";
	}

	
	

}