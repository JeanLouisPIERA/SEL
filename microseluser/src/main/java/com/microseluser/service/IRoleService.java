package com.microseluser.service;

import java.util.List;

import com.microseluser.entities.Role;
import com.microseluser.exceptions.EntityNotFoundException;

public interface IRoleService {

	List<Role> showAllRoles();

	List<Role> showAllRolesByUserId(String userId) throws EntityNotFoundException;

}
