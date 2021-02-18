package com.microselwebui.microselwebui.proxies;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;



import com.microselwebui.microselwebui.criteria.PropositionCriteria;



@FeignClient(name = "microselbourse", url = "localhost:9002", primary = false)
public interface IMicroselBourseProxy {
	
	/*
	 * @GetMapping(value = "/sel/bourse/propositions") Page<Proposition>
	 * PagesDesPropositions(@PathParam(value="propositionCriteria")
	 * PropositionCriteria propositionCriteria,
	 * 
	 * @RequestParam(value = "page") int page, @RequestParam(value="size") int
	 * size);
	 */

}
