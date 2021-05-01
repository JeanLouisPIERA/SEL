package com.microselwebclientjspui.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microselwebclientjspui.beans.UserBean;


@FeignClient(name = "zuul-server")
@RibbonClient(name = "microseluser")
public interface IMicroselUsersProxy {

	@GetMapping(value = "/microseluser/sel/users/accounts/{id}")
	UserBean consulterCompteAdherent(@PathVariable("id") String id);

}
