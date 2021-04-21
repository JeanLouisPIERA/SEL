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
import com.microselbourse.proxies.IMicroselAdherentsProxy;
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

		/*
		 * CategorieBean categorieFound =
		 * microselReferentielsProxy.consulterTypeProposition(propositionDTO.
		 * getCategorieName());
		 * if(!categorieFound.getTypeName().equals(propositionDTO.getCategorieName()))
		 * throw new EntityNotFoundException( "Il n'existe aucune catégorie de ce nom");
		 */

		Proposition propositionToCreate = propositionMapper.propositionDTOToProposition(propositionDTO);

		propositionToCreate.setCategorie(categorieFound.get());
		propositionToCreate.setEmetteurId(propositionDTO.getEmetteurId());
		propositionToCreate.setDateDebut(LocalDate.now());
		propositionToCreate.setStatut(EnumStatutProposition.ENCOURS);

		Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId(emetteurProposition.getId());
		if (walletEmetteur.isEmpty()) {
			//Wallet emetteurWalletCreated = walletService.createWallet(emetteurProposition.getId());
			rabbitMQSender.sendMessageCreateWallet(emetteurProposition);
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
	public Proposition updateProposition(Long id, PropositionDTO propositionDTO)
			throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException {

		Proposition propositionUpdated;

		Optional<Proposition> propositionToUpdate = propositionRepository.findById(id);
		if (!propositionToUpdate.isPresent())
			throw new EntityNotFoundException("L'Offre ou la Demande que vous souhaitez modifier n'existe pas.");
		if (propositionToUpdate.get().getEmetteurId() != propositionDTO.getEmetteurId())
			throw new DeniedAccessException("Vous ne pouvez pas modifier la proposition d'un autre adhérent");
		if (propositionToUpdate.get().getDateFin().isBefore(LocalDate.now())) {
			propositionToUpdate.get().setStatut(EnumStatutProposition.ECHUE);
			throw new DeniedAccessException(
					"Vous ne pouvez pas modifier une OFFRE ou une DEMANDE dont la date de fin de publication est échue");
		}
		if (propositionToUpdate.get().getStatut().equals(EnumStatutProposition.CLOTUREE))
			throw new DeniedAccessException("Vous ne pouvez pas modifier une OFFRE ou une DEMANDE déjà clôturée");

		Optional<Categorie> categorieToUpdate = categorieRepository
				.findByName(EnumCategorie.fromValueCode(propositionDTO.getCategorieName()));
		if (!categorieToUpdate.isPresent())
			throw new EntityNotFoundException("Votre modification est impossible : cette catégorie n'existe pas");

		Optional<EnumTradeType> enumTradeType = EnumTradeType
				.getEnumTradeTypeByCode(propositionDTO.getEnumTradeTypeCode());
		if (enumTradeType.isEmpty())
			throw new EntityNotFoundException(
					"Votre proposition ne peut être qu'une OFFRE ou une DEMANDE : merci de renseigner une des 2 valeurs");

		if (!propositionToUpdate.get().getTitre().equals(propositionDTO.getTitre())) {
			Optional<Proposition> propositionWithSameTitre = propositionRepository
					.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours(propositionDTO.getEmetteurId(),
							propositionDTO.getTitre(), enumTradeType.get(), EnumStatutProposition.ENCOURS);
			if (propositionWithSameTitre.isPresent())
				throw new EntityAlreadyExistsException(
						"Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre");

			propositionUpdated = propositionMapper.propositionDTOToProposition(propositionDTO);
		} else {
			propositionUpdated = propositionMapper.propositionDTOToProposition(propositionDTO);
		}

		propositionUpdated.setId(id);
		propositionUpdated.setCategorie(categorieToUpdate.get());
		propositionUpdated.setDateDebut(propositionToUpdate.get().getDateDebut());
		propositionUpdated.setStatut(EnumStatutProposition.ENCOURS);

		return propositionRepository.save(propositionUpdated);
	}

	@Override
	public Proposition closeProposition(Long id) throws EntityNotFoundException, DeniedAccessException {

		Optional<Proposition> propositionToClose = propositionRepository.findById(id);
		if (!propositionToClose.isPresent())
			throw new EntityNotFoundException("L'Offre ou la Demande que vous souhaitez clôturer n'existe pas.");

		if (propositionToClose.get().getDateFin().isBefore(LocalDate.now())) {
			propositionToClose.get().setStatut(EnumStatutProposition.ECHUE);
			throw new DeniedAccessException(
					"Vous ne pouvez pas clôturer une OFFRE ou une DEMANDE dont la date de fin de publication est échue");
		}
		;

		if (propositionToClose.get().getStatut().equals(EnumStatutProposition.CLOTUREE))
			throw new DeniedAccessException("Vous ne pouvez pas clôturer une OFFRE ou une DEMANDE déjà clôturée");

		propositionToClose.get().setStatut(EnumStatutProposition.CLOTUREE);
		return propositionRepository.save(propositionToClose.get());
	}

	

}
