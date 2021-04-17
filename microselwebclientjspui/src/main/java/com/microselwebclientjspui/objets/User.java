package com.microselwebclientjspui.objets;


import java.util.ArrayList;
import java.util.List;

import com.microselwebclientjspui.beans.RoleBean;



public class User {
	
	String id;

	String email;

	String emailConstraint;

	Boolean emailVerified;

	Boolean enabled;

	String federationLink;

	String firstName;

	String lastName;

	String realmId;

	String username;

	Long createdTimestamp;

	String serviceAccountClientLink;

	Integer notBefore;

	List<RoleBean> roles = new ArrayList<RoleBean>();

	public User() {
		super();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailConstraint() {
		return emailConstraint;
	}

	public void setEmailConstraint(String emailConstraint) {
		this.emailConstraint = emailConstraint;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getFederationLink() {
		return federationLink;
	}

	public void setFederationLink(String federationLink) {
		this.federationLink = federationLink;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getServiceAccountClientLink() {
		return serviceAccountClientLink;
	}

	public void setServiceAccountClientLink(String serviceAccountClientLink) {
		this.serviceAccountClientLink = serviceAccountClientLink;
	}

	public Integer getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(Integer notBefore) {
		this.notBefore = notBefore;
	}

	public List<RoleBean> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleBean> roles) {
		this.roles = roles;
	}
	

}