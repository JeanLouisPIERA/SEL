package com.microselbourse.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.ITransactionRepository;
import com.microselbourse.dao.IWalletRepository;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Transaction;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.ITransactionService;
import com.microselbourse.service.IWalletService;

@Service
public class TransactionServiceImpl implements ITransactionService{
	
	@Autowired
	IReponseRepository reponseRepository;
	
	@Autowired
	IEchangeRepository echangeRepository;
	
	@Autowired
	ITransactionRepository transactionRepository;
	
	@Autowired
	IWalletRepository walletRepository;
	
	@Autowired
	IMicroselAdherentsProxy adherentsProxy;
	
	@Autowired
	IWalletService walletService;
	
	

	@Override
	public Transaction createTransaction(Long echangeId) throws EntityNotFoundException, EntityAlreadyExistsException {
		
		Optional<Echange> echangeToTransaction = echangeRepository.findById(echangeId);
		if(echangeToTransaction.isEmpty()) 
			throw new EntityNotFoundException("L'échange qui sert de base à la création de votre transaction n'existe pas");
		
		Optional<Transaction> transactionAlreadyCreated = transactionRepository.findById(echangeId);
		if(!transactionAlreadyCreated.isEmpty())
			throw new EntityAlreadyExistsException("La transaction que vous voulez enregistrer existe déjà");
		
		Optional<Reponse> reponseFromEchange= reponseRepository.findById(echangeId);
		if (reponseFromEchange.isEmpty())
			throw new EntityNotFoundException("Le détail des informations concernant votre transaction n'est pas accessible");
		
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(echangeToTransaction.get().getEmetteurId());
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToTransaction.get().getRecepteurId());
		
		Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId(echangeToTransaction.get().getEmetteurId());
		if(walletEmetteur.isEmpty())
			throw new EntityNotFoundException("Transaction impossible : le portefeuille de l'émetteur de la proposition n'existe pas");
		
		Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId(echangeToTransaction.get().getRecepteurId());
		if(walletEmetteur.isEmpty())
			throw new EntityNotFoundException("Transaction impossible : le portefeuille du récepteur de la proposition n'existe pas ");
 		
		Transaction transactionToCreate = new Transaction();
		transactionToCreate.setId(echangeId);
		transactionToCreate.setDateTransaction(LocalDate.now());
		transactionToCreate.setMontant(reponseFromEchange.get().getValeur());
		List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(), walletRecepteur.get());
		transactionToCreate.setWallets(transactionToCreateInvolvedWallets);
		
		Transaction transactionSaved = transactionRepository.save(transactionToCreate);
		
		walletService.enregistrerTransaction(transactionToCreate.getId());

		return transactionSaved;		
	}

}
