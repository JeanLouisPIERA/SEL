package com.microseladherent.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    private RoleEnum name;
    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private Set<User> users = new HashSet<>();
	
    public Role() {
		super();
	}

	public Role(Long idRole, RoleEnum name, Set<User> users) {
		super();
		this.idRole = idRole;
		this.name = name;
		this.users = users;
	}

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	public RoleEnum getName() {
		return name;
	}

	public void setName(RoleEnum name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
    
	
    
    
}
    
