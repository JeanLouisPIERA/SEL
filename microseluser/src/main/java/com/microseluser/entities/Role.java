package com.microseluser.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Role.class)
@Entity
@Table(name = "keycloak_role")
public class Role implements Serializable {

	@Id
	@Column(name = "id", length = 36)
	String id;

	@JsonIgnore
	@Column(name = "client_realm_constraint", length = 36)
	String clientRealmConstraint;

	@JsonIgnore
	@Column(name = "client_role", length = 1)
	Boolean clientRole;

	@JsonIgnore
	@Column(name = "description", length = 255)
	String description;

	@Column(name = "name", length = 255)
	String name;

	@JsonIgnore
	@Column(name = "realm_id", length = 255)
	String realmId;

	@JsonIgnore
	@Column(name = "client", length = 36)
	String client;

	@JsonIgnore
	@Column(name = "realm", length = 36)
	String realm;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private List<User> users = new ArrayList<User>();

	public Role() {
		super();
	}

	public String getId() {
		return id;
	}

	public String getClientRealmConstraint() {
		return clientRealmConstraint;
	}

	public Boolean getClientRole() {
		return clientRole;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getRealmId() {
		return realmId;
	}

	public String getClient() {
		return client;
	}

	public String getRealm() {
		return realm;
	}

	@JsonIgnore
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	

}
