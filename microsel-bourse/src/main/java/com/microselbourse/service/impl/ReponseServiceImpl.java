package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.ReponseCriteria;
import com.microselbourse.dao.IBlocageRepository;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.IWalletRepository;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.dao.specs.ReponseSpecification;
import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Transaction;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IReponseMapper;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.IEchangeService;
import com.microselbourse.service.IMailSenderService;
import com.microselbourse.service.IReponseService;
import com.microselbourse.service.IWalletService;

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
	private IMailSenderService mailSender;
	
	@Autowired
	private IWalletRepository walletRepository;
	
	@Autowired
	private IWalletService walletService;
	
	@Autowired
	private IEchangeService echangeService;
	
	@Autowired
	private IBlocageRepository blocageRepository;

	@Override
	public Reponse createReponse(Long propositionId, ReponseDTO reponseDTO) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException {
		
		UserBean recepteurProposition = microselAdherentsProxy.consulterCompteAdherent(reponseDTO.getRecepteurId());
		if(recepteurProposition.getId()!= reponseDTO.getRecepteurId())
			throw new EntityNotFoundException(
					"Vous n'êtes pas identifié comme adhérent de l'association");
		
		Optional<Blocage> blocageRecepteurId = blocageRepository.findByAdherentIdAndStatutBlocage(recepteurProposition.getId(), EnumStatutBlocage.ENCOURS);
		if(blocageRecepteurId.isPresent())
			throw new DeniedAccessException("La réponse ne peut pas être créée : il existe un blocage encours concernant l'adherent récepteur.");
		
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
				Reponse reponseCreated = reponseRepository.save(reponseToCreate);
				
				Echange echange = echangeService.createEchange(reponseCreated.getId());
				
				List<Reponse> reponses = propositionToRespond.get().getReponses();
				reponses.add(reponseCreated);
				propositionToRespond.get().setReponses(reponses);
				propositionRepository.save(propositionToRespond.get());
				
				UserBean emetteurProposition = microselAdherentsProxy.consulterCompteAdherent(propositionToRespond.get().getEmetteurId());
				
				mailSender.sendMailEchangeCreation(reponseToCreate, recepteurProposition, "Creation d'un nouvel échange", "01_RecepteurReponse_EchangeCreation");
				mailSender.sendMailEchangeCreation(reponseToCreate, emetteurProposition, "Reponse a votre Proposition", "02_EmetteurProposition_EchangeCreation");
				/*
				 * Long recepteurPropositionId = reponseDTO.getRecepteurId(); Long
				 * emetteurPropositionId = propositionToRespond.get().getEmetteurId();
				 * mailSender.sendMessageMailEchangeCreation(reponseToCreate,
				 * recepteurPropositionId, "Creation d'un nouvel échange",
				 * "01_RecepteurReponse_EchangeCreation");
				 * mailSender.sendMessageMailEchangeCreation(reponseToCreate,
				 * emetteurPropositionId, "Reponse a votre Proposition",
				 * "02_EmetteurProposition_EchangeCreation");
				 * 
				 */
				
				Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId(recepteurProposition.getId()); 
			    if(walletRecepteur.isEmpty()) {
			    	Wallet recepteurWalletCreated = walletService.createWallet(recepteurProposition.getId());
			    	walletRepository.save(recepteurWalletCreated);
			    	return reponseCreated;
			    }
				return reponseCreated;
			} 
		
		reponseToCreate.setEnumTradeType(EnumTradeType.OFFRE);
		
		Reponse reponseCreated = reponseRepository.save(reponseToCreate);
		
		Echange echange = echangeService.createEchange(reponseCreated.getId());
		
		List<Reponse> reponses = propositionToRespond.get().getReponses();
		reponses.add(reponseCreated);
		propositionToRespond.get().setReponses(reponses);
		propositionRepository.save(propositionToRespond.get());
		
		UserBean emetteurProposition = microselAdherentsProxy.consulterCompteAdherent(propositionToRespond.get().getEmetteurId());
		
		mailSender.sendMailEchangeCreation(reponseToCreate, recepteurProposition, "Creation d'un nouvel échange", "01_RecepteurReponse_EchangeCreation");
		mailSender.sendMailEchangeCreation(reponseToCreate, emetteurProposition, "Reponse a votre Proposition", "02_EmetteurProposition_EchangeCreation");
		/*
		 * Long recepteurPropositionId = reponseDTO.getRecepteurId(); Long
		 * emetteurPropositionId = propositionToRespond.get().getEmetteurId();
		 * mailSender.sendMessageMailEchangeCreation(reponseToCreate,
		 * recepteurPropositionId, "Creation d'un nouvel échange",
		 * "01_RecepteurReponse_EchangeCreation");
		 * mailSender.sendMessageMailEchangeCreation(reponseToCreate,
		 * emetteurPropositionId, "Reponse a votre Proposition",
		 * "02_EmetteurProposition_EchangeCreation");
		 */
		
		
		Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId(recepteurProposition.getId()); 
	    if(walletRecepteur.isEmpty()) {
	    	Wallet recepteurWalletCreated = walletService.createWallet(recepteurProposition.getId());
	    	walletRepository.save(recepteurWalletCreated);
	    	return reponseCreated;
	    }
		
	    return reponseCreated;
		
	}
	
	
	@Override
	public Page<Reponse> searchAllReponsesByCriteria(ReponseCriteria reponseCriteria, Pageable pageable) {
		return null;
		/*
		 * Specification<Reponse> reponseSpecification = new
		 * ReponseSpecification(reponseCriteria); Page<Reponse> reponses =
		 * propositionRepository.findAll(reponseSpecification, pageable); return
		 * reponses; }
		 */
	}

	@Override
	public Reponse readReponse(Long id) throws EntityNotFoundException {
		Optional<Reponse> reponseToRead = reponseRepository.findById(id); 
		if(!reponseToRead.isPresent())
			throw new EntityNotFoundException("L'offre ou la demande que vous voulez consulter n'existe pas");
		
		return reponseToRead.get();
	}


	@Override
	public Page<Reponse> findAllByWalletId(Long id, Pageable pageable) throws EntityNotFoundException {
		Optional<Proposition> propositionFound = propositionRepository.findById(id);
		if(!propositionFound.isPresent())
			throw new EntityNotFoundException("Il n'existe aucune proposition enregistrée pour l'identifiant indiqué");
		
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > propositionFound.get().getReponses().size() ? propositionFound.get().getReponses().size()
				  : (start + pageable.getPageSize()));
		
		Page<Reponse> reponses = new PageImpl<Reponse>(
				propositionFound.get().getReponses().subList(start, end), 
				pageable, 
				propositionFound.get().getReponses().size());
		
		return reponses;
	}
	
	

}
