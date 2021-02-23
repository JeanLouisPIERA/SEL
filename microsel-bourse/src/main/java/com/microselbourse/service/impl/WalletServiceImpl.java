package com.microselbourse.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import com.microselbourse.service.IWalletService;

@Service
public class WalletServiceImpl implements IWalletService {
	
	@Autowired
	private IWalletRepository walletRepository;
	
	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Autowired
	private IEchangeRepository echangeRepository;
	
	@Autowired
	private IReponseRepository reponseRepository;
	
	@Value ("${solde.mini}")
	private Integer soldeMini;

	@Override
	public Wallet createWallet(Long adherentId) throws EntityAlreadyExistsException {
		
		Optional<Wallet> walletToCreate = walletRepository.readByTitulaireId(adherentId); 
		if(!walletToCreate.isEmpty())
			throw new EntityAlreadyExistsException("Il existe déjà un portefeuille pour cet adhérent");
		
		walletToCreate.get().setTitulaireId(adherentId);
		walletToCreate.get().setSoldeWallet(0);
	
		return walletToCreate.get();
	}
	
	@Override
	public void enregistrerTransaction(Long transactionId) throws EntityNotFoundException {
			
		Optional<Transaction> transactionToRegistrate = transactionRepository.findById(transactionId);
		if(transactionToRegistrate.isEmpty()) 
		  throw new EntityNotFoundException("La transaction que vous voulez enregistrer n'existe pas");
		 
		Optional<Echange> echangeFromTransaction = echangeRepository.findById(transactionId);
		if(echangeFromTransaction.isEmpty())
			throw new EntityNotFoundException("Les détails de l'échange à l'origine de votre transaction ne sont pas accessibles");
		
		Optional<Reponse> reponseFromEchange = reponseRepository.findById(transactionId);
		if(reponseFromEchange.isEmpty())
			throw new EntityNotFoundException("Les détails de la réponse à l'origine de votre transaction ne sont pas accessibles");
		
		Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId(echangeFromTransaction.get().getEmetteurId());
		if(walletEmetteur.isEmpty())
			throw new EntityNotFoundException("Il n'existe aucun portefeuille ouvert au nom de l'adhérent qui participe à la transaction en tant qu'émetteur de la proposition d'échange");
		
		Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId(echangeFromTransaction.get().getRecepteurId());
		if(walletEmetteur.isEmpty())
			throw new EntityNotFoundException("Il n'existe aucun portefeuille ouvert au nom de l'adhérent qui participe à la transaction en tant que récepteur de la proposition d'échange");
		
		Optional<Wallet> walletCounterpart = walletRepository.readByTitulaireId((long) 0);
		if(walletCounterpart.isEmpty())
			throw new EntityNotFoundException("Le portefeuille COUNTERPART de l'association n'est plus accessible");
 		
		//SCENARIO 1 : RécepteurReponse a fait une offre donc il est créditeur dans la transaction et émetteur est débiteur
				if(reponseFromEchange.get().getEnumTradeType().equals(EnumTradeType.OFFRE)) {
				//HYPOTHESE 1 : le solde du wallet de l'émetteur débiteur est insuffisant pour régler la transaction sans passer sous le solde mini
					if((walletEmetteur.get().getSoldeWallet()-transactionToRegistrate.get().getMontant())< soldeMini) {
						echangeFromTransaction.get().setStatutEchange(EnumStatutEchange.CONFLIT);
						//TO DO bloquer le compte de l'émetteur dans le microservice adherents
						walletRecepteur.get().setSoldeWallet(walletRecepteur.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
							List<Transaction> walletRecepteurTransactions = walletRecepteur.get().getTransactions();
							walletRecepteurTransactions.add(transactionToRegistrate.get());
							walletRecepteur.get().setTransactions(walletRecepteurTransactions);
						walletRepository.save(walletRecepteur.get());
						walletCounterpart.get().setSoldeWallet(walletCounterpart.get().getSoldeWallet()- transactionToRegistrate.get().getMontant());
							List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
							walletCounterpartTransactions.add(transactionToRegistrate.get());
							walletCounterpart.get().setTransactions(walletCounterpartTransactions);
						walletRepository.save(walletCounterpart.get());	
					}
				//HYPOTHDESE 2 : le solde du wallet de l'émetteur débiteur est suffisant pour régler la transaction sans passer sous le solde mini
					walletRecepteur.get().setSoldeWallet(walletRecepteur.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
						List<Transaction> walletRecepteurTransactions = walletRecepteur.get().getTransactions();
						walletRecepteurTransactions.add(transactionToRegistrate.get());
						walletRecepteur.get().setTransactions(walletRecepteurTransactions);
					walletRepository.save(walletRecepteur.get());
					walletEmetteur.get().setSoldeWallet(walletEmetteur.get().getSoldeWallet()- transactionToRegistrate.get().getMontant());
						List<Transaction> walletEmetteurTransactions = walletEmetteur.get().getTransactions();
						walletEmetteurTransactions.add(transactionToRegistrate.get());
						walletEmetteur.get().setTransactions(walletEmetteurTransactions);
					walletRepository.save(walletEmetteur.get());
				}
				
				
		//SCENARIO 2 : Récepteur Rponse a fait une demande face à une proposition d'offre, il est débiteur et emetteur est crediteur
				//HYPOTHESE 3 : e solde du wallet du récepteur débiteur est insuffisant pour régler la transaction sans passer sous le solde mini
					if((walletRecepteur.get().getSoldeWallet()-transactionToRegistrate.get().getMontant())< soldeMini) {
						echangeFromTransaction.get().setStatutEchange(EnumStatutEchange.CONFLIT);
						//TO DO bloquer le compte du Récepteur dans le microservice adherents
						walletEmetteur.get().setSoldeWallet(walletEmetteur.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
							List<Transaction> walletEmetteurTransactions = walletEmetteur.get().getTransactions();
							walletEmetteurTransactions.add(transactionToRegistrate.get());
							walletEmetteur.get().setTransactions(walletEmetteurTransactions);
						walletRepository.save(walletEmetteur.get());
						walletCounterpart.get().setSoldeWallet(walletCounterpart.get().getSoldeWallet()- transactionToRegistrate.get().getMontant());
							List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
							walletCounterpartTransactions.add(transactionToRegistrate.get());
							walletCounterpart.get().setTransactions(walletCounterpartTransactions);
						walletRepository.save(walletCounterpart.get());					
					}
				//HYPOTHDESE 4: le solde du wallet du récepteur débiteur est suffisant pour régler la transaction sans passer sous le solde mini
					walletRecepteur.get().setSoldeWallet(walletRecepteur.get().getSoldeWallet() - transactionToRegistrate.get().getMontant());
						List<Transaction> walletRecepteurTransactions = walletRecepteur.get().getTransactions();
						walletRecepteurTransactions.add(transactionToRegistrate.get());
						walletRecepteur.get().setTransactions(walletRecepteurTransactions);
					walletRepository.save(walletRecepteur.get());
					walletEmetteur.get().setSoldeWallet(walletEmetteur.get().getSoldeWallet()+ transactionToRegistrate.get().getMontant());
						List<Transaction> walletEmetteurTransactions = walletEmetteur.get().getTransactions();
						walletEmetteurTransactions.add(transactionToRegistrate.get());
						walletEmetteur.get().setTransactions(walletEmetteurTransactions);
					walletRepository.save(walletEmetteur.get());			
	}

	@Override
	public Wallet readByUserId(Long id) throws EntityNotFoundException {
		
		Optional<Wallet> walletToRead = walletRepository.findById(id); 
		if(walletToRead.isEmpty())
			throw new EntityNotFoundException("Il n'existe pas de wallet pour cet adhérent");
		
		return walletToRead.get();
	}



	
	
	
	

}
