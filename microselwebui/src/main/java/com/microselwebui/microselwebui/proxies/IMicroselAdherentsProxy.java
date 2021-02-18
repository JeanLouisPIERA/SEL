package com.microselwebui.microselwebui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.microselwebui.microselwebui.beans.UserBean;
import com.microselwebui.microselwebui.dto.UserDTO;

@FeignClient(name = "microsel-adherent", url = "localhost:9001")
public interface IMicroselAdherentsProxy {
	
	@GetMapping(value = "/sel/bureau/accounts")
	List<UserBean> listeDesAdherents();

	@GetMapping( value = "/sel/users/accounts/{id}")
	UserBean consulterCompteAdherent(@PathVariable("id") Long id);
	
	@PostMapping (value = "/sel/users/accounts")
	ResponseEntity<UserBean> enregistrerCompteAdherent(@RequestBody UserDTO userDTO);
	
	
	

}
