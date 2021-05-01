package com.microselbourse.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.dao.IBlocageRepository;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.IWalletRepository;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.dto.PropositionUpdateDTO;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IPropositionUpdateMapper;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IPropositionService;
import com.microselbourse.service.IWalletService;

@Service
@Transactional
public class PropositionServiceImpl implements IPropositionService {

	@Autowired
	private IPropositionRepository propositionRepository;
	@Autowired
	private ICategorieRepository categorieRepository;
	@Autowired
	private IPropositionMapper propositionMapper;
	@Autowired
	private IPropositionUpdateMapper propositionUpdateMapper;
	@Autowired
	private IMicroselUsersProxy microselUsersProxy;
	/*
	 * @Autowired private IMicroselReferentielsProxy microselReferentielsProxy;
	 */
	@Autowired
	private IWalletRepository walletRepository;
	@Autowired
	private IWalletService walletService;
	@Autowired
	private IBlocageRepository blocageRepository;
	@Autowired
	private RabbitMQSender rabbitMQSender;

	@Override
	public Proposition createProposition(PropositionDTO propositionDTO)
			throws EntityAlreadyExistsException, EntityNotFoundException, DeniedAccessException {

		UserBean emetteurProposition = microselUsersProxy.consulterCompteAdherent(propositionDTO.getEmetteurId());
		if (!emetteurProposition.getId().equals(propositionDTO.getEmetteurId()))
			throw new EntityNotFoundException("Vous n'êtes pas identifié comme adhérent de l'association");

		Optional<Blocage> blocageEmetteurId = blocageRepository
				.findByAdherentIdAndStatutBlocage(propositionDTO.getEmetteurId(), EnumStatutBlocage.ENCOURS);
		if (blocageEmetteurId.isPresent())
			throw new DeniedAccessException(
					"La proposition ne peut pas être créée : il existe un blocage encours concernant l'émetteur de la proposition.");

		Optional<EnumTradeType> enumTradeType = EnumTradeType
				.getEnumTradeTypeByCode(propositionDTO.getEnumTradeTypeCode());
		if (enumTradeType.isEmpty())
			throw new EntityNotFoundException(
					"Votre proposition ne peut être qu'une OFFRE ou une DEMANDE : merci de renseigner une des 2 valeurs");

		Optional<Proposition> titreAlreadyExists = propositionRepository
				.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours(emetteurProposition.getId(),
						propositionDTO.getTitre(), enumTradeType.get(), EnumStatutProposition.ENCOURS);
		if (titreAlreadyExists.isPresent())
			throw new EntityAlreadyExistsException(
					"Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre");

		Optional<Categorie> categorieFound = categorieRepository
				.findByName(EnumCategorie.fromValueCode(propositionDTO.getCategorieName()));
		if (!categorieFound.isPresent())
			throw new EntityNotFoundException("La catégorie dans laquelle vous avez choisi de publier n'existe pas");
		
		
		Proposition propositionToCreate = propositionMapper.propositionDTOToProposition(propositionDTO);

		if (propositionToCreate.getDateEcheance().isBefore(LocalDate.now())
				|| propositionToCreate.getDateFin().isBefore(LocalDate.now()))
			throw new DeniedAccessException(
					"La date d'échéance et la date de fin de publication doivent être postérieures à la date d'aujourd'hui");

		propositionToCreate.setCategorie(categorieFound.get());
		propositionToCreate.setEmetteurId(propositionDTO.getEmetteurId());
		propositionToCreate.setDateDebut(LocalDate.now().plusDays(1));
		propositionToCreate.setStatut(EnumStatutProposition.ENCOURS);

		//Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId(emetteurProposition.getId());
		Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId(propositionToCreate.getEmetteurId());
		if (walletEmetteur.isEmpty()) {
			// Wallet emetteurWalletCreated =
			// walletService.createWallet(emetteurProposition.getId());
			rabbitMQSender.sendMessageCreateWallet(emetteurProposition);// ------------------------------------------------------->
																		// RMQ
			return propositionRepository.save(propositionToCreate);
		}

		return propositionRepository.save(propositionToCreate);

	}

	@Override
	public Page<Proposition> searchAllPropositionsByCriteria(PropositionCriteria propositionCriteria,
			Pageable pageable) {
		
		Specification<Proposition> propositionSpecification = new PropositionSpecification(propositionCriteria);
		
		Page<Proposition> propositions = propositionRepository.findAll(propositionSpecification, pageable);
		return propositions;
	}

	@Override
	public Proposition readProposition(Long id) throws EntityNotFoundException {

		Optional<Proposition> propositionToRead = propositionRepository.findById(id);
		if (!propositionToRead.isPresent())
			throw new EntityNotFoundException("L'offre ou la demande que vous voulez consulter n'existe pas");

		return propositionToRead.get();
	}

	@Override
	public Proposition updateProposition(Long id, String emetteurId, PropositionUpdateDTO propositionUpdateDTO)
			throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException {

		Proposition propositionUpdated;

		Optional<Proposition> propositionToUpdate = propositionRepository.findById(id);
		if (!propositionToUpdate.isPresent())
			throw new EntityNotFoundException("L'Offre ou la Demande que vous souhaitez modifier n'existe pas.");
		if (!propositionToUpdate.get().getEmetteurId().equals(emetteurId))
			throw new DeniedAccessException("Vous ne pouvez pas modifier la proposition d'un autre adhérent");
		if (propositionToUpdate.get().getDateFin().isBefore(LocalDate.now())) {
			propositionToUpdate.get().setStatut(EnumStatutProposition.ECHUE);
			throw new DeniedAccessException(
					"Vous ne pouvez pas modifier une OFFRE ou une DEMANDE dont la date de fin de publication est échue");
		}
		if (propositionToUpdate.get().getStatut().equals(EnumStatutProposition.CLOTUREE))
			throw new DeniedAccessException("Vous ne pouvez pas modifier une OFFRE ou une DEMANDE déjà clôturée");

		if (!propositionToUpdate.get().getTitre().equals(propositionUpdateDTO.getTitre())) {
			Optional<Proposition> propositionWithSameTitre = propositionRepository
					.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours(propositionToUpdate.get().getEmetteurId(),
							propositionUpdateDTO.getTitre(), propositionToUpdate.get().getEnumTradeType(),
							EnumStatutProposition.ENCOURS);
			if (propositionWithSameTitre.isPresent())
				throw new EntityAlreadyExistsException(
						"Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre");
			propositionUpdated = propositionUpdateMapper.propositionUpdateDTOToProposition(propositionUpdateDTO);
		} else {
			propositionUpdated = propositionUpdateMapper.propositionUpdateDTOToProposition(propositionUpdateDTO);
		}

		propositionUpdated.setEmetteurId(propositionToUpdate.get().getEmetteurId());
		propositionUpdated.setEmetteurUsername(propositionToUpdate.get().getEmetteurUsername());
		propositionUpdated.setId(propositionToUpdate.get().getId());
		propositionUpdated.setStatut(EnumStatutProposition.ENCOURS);
		propositionUpdated.setCategorie(propositionToUpdate.get().getCategorie());
		propositionUpdated.setEnumTradeType(propositionToUpdate.get().getEnumTradeType());

		if (propositionUpdated.getDateEcheance().isBefore(LocalDate.now())
				|| propositionUpdated.getDateFin().isBefore(LocalDate.now()))
			throw new DeniedAccessException(
					"La date d'échéance et la date de fin de publication doivent être postérieures à la date d'aujourd'hui");

		return propositionRepository.save(propositionUpdated);
	}
	

	@Override
	public Proposition closeProposition(Long id, String emetteurId) throws EntityNotFoundException, DeniedAccessException {

		Optional<Proposition> propositionToClose = propositionRepository.findById(id);
		if (!propositionToClose.isPresent())
			throw new EntityNotFoundException("L'Offre ou la Demande que vous souhaitez clôturer n'existe pas.");
		
		if (!propositionToClose.get().getEmetteurId().equals(emetteurId))
			throw new DeniedAccessException("Vous ne pouvez pas clôturer la proposition d'un autre adhérent");

		if (propositionToClose.get().getDateFin().isBefore(LocalDate.now())) {
			propositionToClose.get().setStatut(EnumStatutProposition.ECHUE);
			throw new DeniedAccessException(
					"Vous ne pouvez pas clôturer une OFFRE ou une DEMANDE dont la date de fin de publication est échue");
		}

		if (propositionToClose.get().getStatut().equals(EnumStatutProposition.CLOTUREE))
			throw new DeniedAccessException("Vous ne pouvez pas clôturer une OFFRE ou une DEMANDE déjà clôturée");

		propositionToClose.get().setStatut(EnumStatutProposition.CLOTUREE);
		propositionToClose.get().setDateFin(LocalDate.now());
		
		return propositionRepository.save(propositionToClose.get());
	}

}
