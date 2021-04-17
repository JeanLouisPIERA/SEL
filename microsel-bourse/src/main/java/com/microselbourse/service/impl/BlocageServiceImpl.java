package com.microselbourse.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IBlocageRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IBlocageService;

@Service
public class BlocageServiceImpl implements IBlocageService {

	@Autowired
	private IEchangeRepository echangeRepository;

	@Autowired
	private IMicroselUsersProxy usersProxy;

	@Autowired
	private IBlocageRepository blocageRepository;

	@Override
	public Blocage createBlocage(Long echangeId, String adherentId) throws EntityNotFoundException {

		Optional<Echange> echangeToCheck = echangeRepository.findById(echangeId);
		if (!echangeToCheck.isPresent())
			throw new EntityNotFoundException("L'échange à l'origine du blocage n'existe pas");

		UserBean adherentToBlock = usersProxy.consulterCompteAdherent(adherentId);
		if (adherentToBlock == null)
			throw new EntityNotFoundException("L'identifiant de l'adhérent que vous souhaitez bloquer n'existe pas");

		Optional<Echange> echangeAtOrigin = echangeRepository.findByIdAndAdherentId(echangeId, adherentId);
		if (!echangeAtOrigin.isPresent())
			throw new EntityNotFoundException(
					"L'échange auquel on fait référence pour créer le blocage n'existe pas pour l'adherent indiqué");

		Blocage blocageToCreate = new Blocage();
		blocageToCreate.setAdherentId(adherentToBlock.getId());
		blocageToCreate.setDateDebutBlocage(LocalDate.now());
		blocageToCreate.setEchange(echangeAtOrigin.get());
		blocageToCreate.setStatutBlocage(EnumStatutBlocage.ENCOURS);

		return blocageRepository.save(blocageToCreate);

	}

	@Override
	public Blocage annulerBlocage(String adherentId) throws EntityNotFoundException {

		Optional<Blocage> blocageToAnnuler = blocageRepository.findByAdherentId(adherentId);
		if (!blocageToAnnuler.isPresent())
			throw new EntityNotFoundException("Il n'existe aucun blocage pour cet adhérent");

		blocageToAnnuler.get().setDateFinBlocage(LocalDate.now());
		blocageToAnnuler.get().setStatutBlocage(EnumStatutBlocage.ANNULE);

		return blocageRepository.save(blocageToAnnuler.get());
	}

	@Override
	public Blocage readBlocage(String adherentId) throws EntityNotFoundException {

		Optional<Blocage> blocageToRead = blocageRepository.findByAdherentId(adherentId);
		if (!blocageToRead.isPresent())
			throw new EntityNotFoundException("Le blocage que vous recherchez n'existe pas");

		return blocageToRead.get();
	}

}
