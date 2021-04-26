package com.microselwebclientjspui.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebclientjspui.criteria.UserCriteria;
import com.microselwebclientjspui.criteria.WalletCriteria;
import com.microselwebclientjspui.dto.UserDTO;
import com.microselwebclientjspui.objets.Role;
import com.microselwebclientjspui.objets.User;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.IRoleService;
import com.microselwebclientjspui.service.IUserService;
import com.microselwebclientjspui.service.IWalletService;

@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;

	/*
	 * @Autowired private IUserService userService;
	 */

	@GetMapping("/accueil")
	public String accueil() {

		return "accueil";
	}
	
	@GetMapping("/accueilAdmin")
	public String accueilAdmin() {

		return "accueilAdmin";
	}

	@GetMapping("/accounts/account")
	public String selIndex(Model model) {
		
		User adherent = userService.consulterCompteAdherent();
		
		List<Role> roles = roleService.getRolesByUserId();

		model.addAttribute("adherent", adherent);
		model.addAttribute("roles", roles);

		return "users/userView";
	}
	
	@GetMapping("/accounts/{id}")
	public String searchById(Model model, @PathVariable String id) {
		
		User adherent = userService.searchById(id);
		
		List<Role> roles = roleService.getRolesByUserId(id);

		model.addAttribute("adherent", adherent);
		model.addAttribute("roles", roles);

		return "users/userPageView";
	}


	/**
	 * Permet d'afficher une sélection les portefeuilles sous forme de page
	 */
	@GetMapping(value = "/accounts")
	public String searchByCriteria(Model model, @PathParam(value = "userCriteria") UserCriteria userCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("userCriteria", new UserCriteria());

		Page<User> users = userService.searchByCriteria(userCriteria, PageRequest.of(page, size));

		model.addAttribute("users", users.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", users.getNumber());
		model.addAttribute("totalPages", users.getTotalPages());
		model.addAttribute("totalElements", users.getTotalElements());
		model.addAttribute("size", users.getSize());
		
		List<Role> roles = roleService.getRoles();
		model.addAttribute("roles", roles);
		

		return "users/usersPage";

	}

}