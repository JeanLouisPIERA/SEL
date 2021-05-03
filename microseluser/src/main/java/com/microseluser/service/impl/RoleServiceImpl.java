package com.microseluser.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microseluser.dao.IRoleRepository;
import com.microseluser.dao.IUserRepository;
import com.microseluser.entities.Role;
import com.microseluser.entities.User;
import com.microseluser.exceptions.EntityNotFoundException;
import com.microseluser.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IUserRepository userRepository;

	@Override
	public List<Role> showAllRoles() {
		List<Role> listRoles = roleRepository.findAll();
		return listRoles;
	}

	@Override
	public List<Role> showAllRolesByUserId(String userId) throws EntityNotFoundException {
		
		

		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent())
			throw new EntityNotFoundException("Il n'existe aucun compte correspond à votre recherche");

		List<Role> listRolesByUserId = user.get().getRoles();

		return listRolesByUserId;
	}

}
