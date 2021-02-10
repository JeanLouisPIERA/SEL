package com.microselbourse.service.impl;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.IPropositionService;

@Service
public class PropositionServiceImpl implements IPropositionService {
	
	@Autowired
	private IPropositionRepository propositionRepository;
	@Autowired
	private ICategorieRepository categorieRepository;
	@Autowired
	private IPropositionMapper propositionMapper;
	@Autowired
	private IMicroselAdherentsProxy microselAdherentsProxy;
	

	@Override
	public Proposition createProposition(PropositionDTO propositionDTO) throws EntityAlreadyExistsException, EntityNotFoundException {
		
		//TODO : Voir comment récupérer l'id du user loggé qui va créer la proposition. Par défaut userId = 1
		UserBean emetteurProposition = microselAdherentsProxy.consulterCompteAdherent((long)1);
		if(emetteurProposition.getId()!= (long) 1)
			throw new EntityNotFoundException("Vous n'êtes pas identifié comme adhérent de l'association");
		
		Optional<Proposition> titreAlreadyExists = propositionRepository.findByIdAndTitre((long)1, propositionDTO.getTitre());
		if (titreAlreadyExists.isPresent()) 
			throw new EntityAlreadyExistsException("Vous avez déjà créé une proposition avec le même titre"); 
		
		Optional<Categorie> categorieNotFound = categorieRepository.findById(propositionDTO.getIdCategorie());
		if(!categorieNotFound.isPresent())
			throw new EntityNotFoundException("La catégorie dans laquelle vous avez choisi de publier n'existe pas");
		
		if(EnumTradeType.getEnumTradeTypeByCode(propositionDTO.getEnumTradeTypeCode())==null)
				throw new EntityNotFoundException("La saisie du type OFFRE ou DEMANDE de votre proposition est incorrecte");
			
		Proposition propositionToCreate = propositionMapper.propositionDTOToProposition(propositionDTO);
			
	    propositionToCreate.setEmetteurId(emetteurProposition.getId());
	    propositionToCreate.setDateDebut(LocalDate.now());
	    
	    return propositionRepository.save(propositionToCreate);
		
	}

	@Override
	public Proposition readProposition(Long id) {
		
		return null;
	}

	@Override
	public Proposition updateProposition(Long id, PropositionDTO propositionDTO) {
		
		return null;
	}
	
	

}
