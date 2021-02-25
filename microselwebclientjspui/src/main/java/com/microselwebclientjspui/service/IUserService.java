package com.microselwebclientjspui.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microselwebclientjspui.dto.UserDTO;
import com.microselwebclientjspui.objets.User;




public interface IUserService {

	ResponseEntity<User> enregistrerCompteAdherent(@Valid UserDTO userDTO);

	User consulterCompteAdherent(Long id);

	List<User> listeDesAdherents();

	Page<User> findPaginated(List<User> adherents, PageRequest of);

}
