package com.microselwebclientjspui.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

import com.microselwebclientjspui.dto.UserDTO;
import com.microselwebclientjspui.objets.User;
import com.microselwebclientjspui.service.IUserService;






@Controller
public class UserController {


	@Autowired
	private IUserService userService;

	@GetMapping("/accueil")
	public String accueil() {

		return "accueil";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserDTO());
		return "registration";
	}

	
	  @PostMapping("/registration") public String registerUserAccount(Model
	  model, @ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result)
	  {
	  
	  if (result.hasErrors()) { return "registration"; }
	  
	  try { ResponseEntity<User> reponseAdherentCreated =
	  userService.enregistrerCompteAdherent(userDTO); } catch
	  (HttpClientErrorException e) {
	  
			/*
			 * String errorMessage = errorDecoder.decode(null, null);
			 * //reservationExceptionMessage.convertCodeStatusToExceptionMessage(e.
			 * getRawStatusCode()); model.addAttribute("error", errorMessage);
			 * return"/error";
			 */
	  
	  }
	  
	  return "redirect:/registration?success"; }
	 

	@GetMapping("/users/accueilAdherent")
	public String indexAdherent() {

		return "users/accueilAdherent";
	}

	@GetMapping("/users/accounts/{id}")
	public String selIndex(Model model, @PathVariable() Long id) {

		User adherent = userService.consulterCompteAdherent(id);

		model.addAttribute("adherent", adherent);

		return "users/adherent";
	}

	
	  @GetMapping("/bureau/adherents") public String displayAllUsers(Model model){
	  
	  List<User> adherents = userService.listeDesAdherents();
	  
	  Page pageAdherents = new PageImpl(adherents);
	  
	  model.addAttribute("adherents", adherents);
	  
	  return "users/adherents"; }
	 

	
	  @GetMapping(value = "/bureau/pagesAdherents") public String
	  displayPageAllUsers(Model model, @RequestParam("page") Optional<Integer>
	  page,
	  
	  @RequestParam("size") Optional<Integer> size) { int currentPage =
	  page.orElse(1); int pageSize = size.orElse(6);
	  
	  List<User> adherents = userService.listeDesAdherents();
	  
	  Page<User> adherentsPage = userService.findPaginated(adherents,
	  PageRequest.of(currentPage - 1, pageSize));
	  
	  model.addAttribute("adherentsPage", adherentsPage);
	  
	  int totalPages = adherentsPage.getTotalPages(); if (totalPages > 0) {
	  List<Integer> pageNumbers = IntStream.rangeClosed(1,
	  totalPages).boxed().collect(Collectors.toList());
	  model.addAttribute("pageNumbers", pageNumbers); }
	  
	  return "users/adherentsPage"; }
	 
}