package com.microselwebui.microselwebui.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebui.microselwebui.beans.UserBean;
import com.microselwebui.microselwebui.dto.UserDTO;
import com.microselwebui.microselwebui.errors.CustomErrorDecoder;
import com.microselwebui.microselwebui.proxies.IMicroselAdherentsProxy;

@Controller
public class ClientController {
	
	 @Autowired
	    private IMicroselAdherentsProxy adherentsProxy;
	 @Autowired
	 	private CustomErrorDecoder errorDecoder;

    @GetMapping("/accueil")
    public String accueil(){
    	
        return "accueil";
    }
    
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
    	model.addAttribute("user", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(Model model, @ModelAttribute("user") @Valid UserDTO userDTO,
        BindingResult result) {
    	
        if (result.hasErrors()) {
            return "registration";
        }
        
        try {
			ResponseEntity<UserBean> reponseAdherentCreated = adherentsProxy.enregistrerCompteAdherent(userDTO);
		} catch (HttpClientErrorException e) {
			/*
			 * String errorMessage = errorDecoder.decode(null, null);
			 * //reservationExceptionMessage.convertCodeStatusToExceptionMessage(e.
			 * getRawStatusCode()); model.addAttribute("error", errorMessage);
			 * return"/error";
			 */
		}
        
        return "redirect:/registration?success";
    }
    
    @GetMapping("/users/accueilAdherent")
    public String indexAdherent(){
    	
        return "users/accueilAdherent";
    }
    
    @GetMapping("/users/accounts/{id}")
    public String selIndex(Model model, @PathVariable() Long id) {
    	
    	UserBean adherent = adherentsProxy.consulterCompteAdherent(id);
    	
    	model.addAttribute("adherent", adherent);
    	
        return "users/adherent";
    }
       
    @GetMapping("/bureau/adherents")
    public String displayAllUsers(Model model){
    	
    	 List<UserBean> adherents =  adherentsProxy.listeDesAdherents();

         model.addAttribute("adherents", adherents);

        return "users/adherents";
    }
    

}