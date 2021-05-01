package com.microseluser.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = User.class)
@Entity
@Table(name = "user_entity")
public class User implements Serializable {

	@Id
	@Column(name = "id", length = 36)
	String id;

	@Column(name = "email", length = 255)
	String email;

	@JsonIgnore
	@Column(name = "email_constraint", length = 255)
	String emailConstraint;

	@JsonIgnore
	@Column(name = "email_verified", length = 1)
	Boolean emailVerified;

	@JsonIgnore
	@Column(name = "enabled", length = 1)
	Boolean enabled;

	@JsonIgnore
	@Column(name = "federation_link", length = 255)
	String federationLink;

	@Column(name = "first_name", length = 255)
	String firstName;

	@Column(name = "last_name", length = 255)
	String lastName;

	@JsonIgnore
	@Column(name = "realm_id", length = 255)
	String realmId;

	@Column(name = "username", length = 255)
	String username;

	@JsonIgnore
	@Column(name = "created_timestamp")
	Long createdTimestamp;

	@JsonIgnore
	@Column(name = "service_account_client_link", length = 255)
	String serviceAccountClientLink;

	@JsonIgnore
	@Column(name = "not_before")
	int notBefore;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "user_role_mapping", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private List<Role> roles = new ArrayList<Role>();

	public User() {
		super();
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailConstraint() {
		return emailConstraint;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public String getFederationLink() {
		return federationLink;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRealmId() {
		return realmId;
	}

	public String getUsername() {
		return username;
	}

	public Long getCreatedTimestamp() {
		return createdTimestamp;
	}

	public String getServiceAccountClientLink() {
		return serviceAccountClientLink;
	}

	public Integer getNotBefore() {
		return notBefore;
	}

	public List<Role> getRoles() {
		return roles;
	}

}
