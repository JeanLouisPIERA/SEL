package com.microselbourse.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microselbourse.beans.UserBean;

@FeignClient(name = "microsel-adherent", url = "localhost:9001")
public interface IMicroselAdherentsProxy {
	
	@GetMapping( value = "/sel/users/accounts/{id}")
	UserBean consulterCompteAdherent(@PathVariable("id") Long id);

}
