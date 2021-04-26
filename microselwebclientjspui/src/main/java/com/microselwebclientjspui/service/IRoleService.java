package com.microselwebclientjspui.service;

import java.util.List;

import com.microselwebclientjspui.objets.Role;

public interface IRoleService {

	List<Role> getRoles();
	
	List<Role> getRolesByUserId();

	List<Role> getRolesByUserId(String id);

	

}
