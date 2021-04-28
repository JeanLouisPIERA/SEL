package com.microselreferentiels.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microselreferentiels.beans.UserBean;

@FeignClient(name = "microseluser") 
@RibbonClient(name = "microseluser")
public interface IMicroselAdherentsProxy {
	
	
	@GetMapping(value = "/sel/users/accounts/{id}")
	UserBean consulterCompteAdherent(@PathVariable("id") String id);


}
