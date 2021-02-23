package com.microselwebclient_ui.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microselwebclient_ui.dto.UserDTO;
import com.microselwebclient_ui.objets.User;
import com.microselwebclient_ui.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Override
	public ResponseEntity<User> enregistrerCompteAdherent(@Valid UserDTO userDTO) {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public User consulterCompteAdherent(Long id) {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listeDesAdherents() {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findPaginated(List<User> adherents, PageRequest of) {
		// FIXME Auto-generated method stub
		return null;
	}

}
