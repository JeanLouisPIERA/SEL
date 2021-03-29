package com.microselbourse.proxies;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microselbourse.beans.CategorieBean;


@FeignClient(name = "microselreferentiels") 
@RibbonClient(name = "microselreferentiels")
public interface IMicroselReferentielsProxy {
	
	
	  @GetMapping( value = "/sel/referentiels/{typeName}")
	  CategorieBean consulterTypeProposition(@PathVariable("typeName") String
	  typeName);
	  
	  @GetMapping( value = "/sel/referentiels")
	  List<CategorieBean> getAllTypePropositions();
	 

}
