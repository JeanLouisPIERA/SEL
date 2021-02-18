package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.ReponseCriteria;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IReponseMapper;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.IEchangeService;
import com.microselbourse.service.IMailSenderService;
import com.microselbourse.service.IReponseService;

@Service
public class ReponseServiceImpl implements IReponseService{
	
	@Autowired
	ICategorieRepository categorieRepository;
	
	@Autowired
	private IPropositionRepository propositionRepository;
	
	@Autowired
	private IReponseRepository reponseRepository;
	
	@Autowired
	private IReponseMapper reponseMapper;
	
	@Autowired
	private IMicroselAdherentsProxy microselAdherentsProxy;
	
	@Autowired
	private IEchangeService echangeService; 
	
	@Autowired
	private IEchangeRepository echangeRepository;
	
	@Autowired
	private IMailSenderService mailSender;

	@Override
	public Reponse createReponse(Long propositionId, ReponseDTO reponseDTO) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException {
		
		UserBean recepteurProposition = microselAdherentsProxy.consulterCompteAdherent(reponseDTO.getRecepteurId());
		if(recepteurProposition.getId()!= reponseDTO.getRecepteurId())
			throw new EntityNotFoundException(
					"Vous n'êtes pas identifié comme adhérent de l'association");
		
		Optional<Proposition> propositionToRespond = propositionRepository.findById(propositionId); 
				if(propositionToRespond.isEmpty()) 
					throw new EntityNotFoundException("L'offre ou la demande à laquelle vous vouslez répondre n'existe pas");
				if(!propositionToRespond.get().getStatut().equals(EnumStatutProposition.ENCOURS)) 
					throw new DeniedAccessException("Vous ne pouvez répondre qu'à une offre ou une demande en cours");
				if(propositionToRespond.get().getEmetteurId()==reponseDTO.getRecepteurId())
					throw new DeniedAccessException("Vous ne pouvez pas répondre à une de vos propres offres ou demandes");
		
		Reponse reponseToCreate = reponseMapper.reponseDTOToReponse(reponseDTO);
		        
		reponseToCreate.setDateEcheance(propositionToRespond.get().getDateEcheance());  
		reponseToCreate.setDateReponse(LocalDate.now());
		reponseToCreate.setProposition(propositionToRespond.get());
		
		if(propositionToRespond.get().getEnumTradeType().equals(EnumTradeType.OFFRE))
			{
				reponseToCreate.setEnumTradeType(EnumTradeType.DEMANDE);
				return reponseRepository.save(reponseToCreate);
			} 
		
		reponseToCreate.setEnumTradeType(EnumTradeType.OFFRE);
		
		Reponse reponseCreated = reponseRepository.save(reponseToCreate);
		
		UserBean emetteurProposition = microselAdherentsProxy.consulterCompteAdherent(reponseDTO.getRecepteurId());
		
		mailSender.sendMailEchangeCreation(reponseToCreate, recepteurProposition, "Creation d'un nouvel échange", "01_RecepteurReponse_EchangeCreation");
		mailSender.sendMailEchangeCreation(reponseToCreate, emetteurProposition, "Reponse a votre Proposition", "02_EmetteurProposition_EchangeCreation");

	    return reponseCreated;
		
	}
	
	
	@Override
	public Page<Reponse> searchAllReponsesByCriteria(ReponseCriteria reponseCriteria, Pageable pageable) {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public Reponse readReponse(Long id) {
		// FIXME Auto-generated method stub
		return null;
	}
	
	

}
