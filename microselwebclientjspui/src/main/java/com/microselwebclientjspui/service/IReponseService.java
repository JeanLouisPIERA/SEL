package com.microselwebclientjspui.service;


import com.microselwebclientjspui.dto.ReponseDTO;
import com.microselwebclientjspui.objets.Reponse;


public interface IReponseService {
	
	public Reponse createReponse(Long id, ReponseDTO reponseDTO);

}
