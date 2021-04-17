package com.microselbourse.beans;

import java.util.ArrayList;
import java.util.List;

public class RoleBean {

	String id;

	String clientRealmConstraint;

	Boolean clientRole;

	String description;

	String name;

	String realmId;

	String client;

	String realm;

	List<UserBean> users = new ArrayList<UserBean>();

	public RoleBean() {
		super();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientRealmConstraint() {
		return clientRealmConstraint;
	}

	public void setClientRealmConstraint(String clientRealmConstraint) {
		this.clientRealmConstraint = clientRealmConstraint;
	}

	public Boolean getClientRole() {
		return clientRole;
	}

	public void setClientRole(Boolean clientRole) {
		this.clientRole = clientRole;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public List<UserBean> getUsers() {
		return users;
	}

	public void setUsers(List<UserBean> users) {
		this.users = users;
	}

}
