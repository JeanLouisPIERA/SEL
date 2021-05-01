package com.microselbourse.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.WalletCriteria;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.ITransactionRepository;
import com.microselbourse.dao.IWalletRepository;
import com.microselbourse.dao.specs.WalletSpecification;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Transaction;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IBlocageService;
import com.microselbourse.service.IWalletService;

@Service
@Transactional
public class WalletServiceImpl implements IWalletService {

	@Autowired
	private IWalletRepository walletRepository;

	@Autowired
	private ITransactionRepository transactionRepository;

	@Autowired
	private IEchangeRepository echangeRepository;

	@Autowired
	private IReponseRepository reponseRepository;

	@Autowired
	private IMicroselUsersProxy userProxy;

	@Autowired
	private IBlocageService blocageService;

	@Value("${solde.mini}")
	private Integer soldeMini;

	@Override
	public Wallet createWallet(String adherentId) throws EntityAlreadyExistsException {

		Optional<Wallet> walletToCreate = walletRepository.readByTitulaireId(adherentId);
		if (!walletToCreate.isEmpty())
			throw new EntityAlreadyExistsException("Il existe déjà un portefeuille pour cet adhérent");

		UserBean titulaire = userProxy.consulterCompteAdherent(adherentId);

		Wallet walletCreated = new Wallet();
		walletCreated.setTitulaireId(adherentId);
		walletCreated.setTitulaireUsername(titulaire.getUsername());
		walletCreated.setSoldeWallet(0);

		return walletRepository.save(walletCreated);
	}

	@Override
	public void enregistrerTransaction(Long transactionId) throws EntityNotFoundException {

		Optional<Transaction> transactionToRegistrate = transactionRepository.findById(transactionId);
		if (transactionToRegistrate.isEmpty())
			throw new EntityNotFoundException("La transaction que vous voulez enregistrer n'existe pas");

		Optional<Echange> echangeFromTransaction = echangeRepository.findById(transactionId);
		if (echangeFromTransaction.isEmpty())
			throw new EntityNotFoundException(
					"Les détails de l'échange à l'origine de votre transaction ne sont pas accessibles");

		Optional<Reponse> reponseFromEchange = reponseRepository.findById(transactionId);
		if (reponseFromEchange.isEmpty())
			throw new EntityNotFoundException(
					"Les détails de la réponse à l'origine de votre transaction ne sont pas accessibles");

		Optional<Wallet> walletEmetteurEchange = walletRepository
				.readByTitulaireId(echangeFromTransaction.get().getEmetteurId());
		if (walletEmetteurEchange.isEmpty())
			throw new EntityNotFoundException(
					"Il n'existe aucun portefeuille ouvert au nom de l'adhérent qui participe à la transaction en tant qu'émetteur de la proposition d'échange");

		Optional<Wallet> walletRecepteurEchange = walletRepository
				.readByTitulaireId(echangeFromTransaction.get().getRecepteurId());
		if (walletEmetteurEchange.isEmpty())
			throw new EntityNotFoundException(
					"Il n'existe aucun portefeuille ouvert au nom de l'adhérent qui participe à la transaction en tant que récepteur de la proposition d'échange");

		Optional<Wallet> walletCounterpart = walletRepository.readByTitulaireId((String) "000.000.000");
		if (walletCounterpart.isEmpty())
			throw new EntityNotFoundException("Le portefeuille COUNTERPART de l'association n'est plus accessible");


		// HYPOTHESE 1 le walletEmetteurEchange n'appartient pas à la transaction =
		// EMETTEUR REFUS ***************************************
		if (!transactionToRegistrate.get().getWallets().contains(walletEmetteurEchange.get())) {

			/*
			 * walletEmetteur = walletCounterpart.get(); // H1 ------------------- WALLET
			 * EMETTEUR = COUNTERPART
			 */

			// HYPOTHESE 1A le walletRecepteurEchange n'appartient pas non plus à la
			// transaction = EMETTEUR REFUS + RECEPTEUR REFUS
			// LITIGE suite à double REFUS transaction au débit et au crédit sur COUNTERPART
			if (!transactionToRegistrate.get().getWallets().contains(walletRecepteurEchange.get())) {

				
				// H1A ------------- WE et WR = COUNTERPART

				List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
				walletCounterpartTransactions.add(transactionToRegistrate.get());
				walletCounterpart.get().setTransactions(walletCounterpartTransactions);
				walletRepository.save(walletCounterpart.get());

			}
			// HYPOTHESE 1B le WalletRecepteurEchange appartient à la transaction = EMETTEUR
			// REFUS + RECEPTEUR VALIDE
			// LITIGE suite au refus de l'Emetteur
			else {

				
				 //H1B ------------WE = COUNTERPART et WR = Recepteur Echange
			

				// SCENARIO 1 : RécepteurReponse a fait une offre donc il est créditeur dans la
				// transaction et émetteur est débiteur
				if (reponseFromEchange.get().getEnumTradeType().equals(EnumTradeType.OFFRE)) {

					walletRecepteurEchange.get().setSoldeWallet(
							walletRecepteurEchange.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
					List<Transaction> walletRecepteurTransactions = walletRecepteurEchange.get().getTransactions();
					walletRecepteurTransactions.add(transactionToRegistrate.get());
					walletRecepteurEchange.get().setTransactions(walletRecepteurTransactions);
					walletRepository.save(walletRecepteurEchange.get());
					walletCounterpart.get().setSoldeWallet(
							walletCounterpart.get().getSoldeWallet() - transactionToRegistrate.get().getMontant());
				
					List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
					walletCounterpartTransactions.add(transactionToRegistrate.get());
					walletCounterpart.get().setTransactions(walletCounterpartTransactions);
					walletRepository.save(walletCounterpart.get());
				} else {

					// SCENARIO 2 : Récepteur Réponse a fait une demande face à une proposition
					// d'offre, il est débiteur et emetteur est crediteur

					// HYPOTHESE 3 : le solde du wallet du récepteur débiteur est insuffisant pour
					// régler la transaction sans passer sous le solde mini
					// walletEmetteur = COUNTERPART pour LITIGE et walletRecepteur = COUNTERPART
					// pour CONFLIT
					if ((walletRecepteurEchange.get().getSoldeWallet()
							- transactionToRegistrate.get().getMontant()) < soldeMini) {

						echangeFromTransaction.get().setStatutEchange(EnumStatutEchange.CONFLIT);
						blocageService.createBlocage(transactionToRegistrate.get().getId(),
								transactionToRegistrate.get().getRecepteurId(),
								transactionToRegistrate.get().getRecepteurUsername());
						
						List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
						walletCounterpartTransactions.add(transactionToRegistrate.get());
						walletCounterpart.get().setTransactions(walletCounterpartTransactions);
						walletRepository.save(walletCounterpart.get());
					} else {
						// HYPOTHESE 4: le solde du wallet du récepteur débiteur est suffisant pour
						// régler la transaction sans passer sous le solde mini

						int newSolde = (walletRecepteurEchange.get().getSoldeWallet())
								- (transactionToRegistrate.get().getMontant());
						walletRecepteurEchange.get().setSoldeWallet(newSolde);
						List<Transaction> walletRecepteurTransactions = walletRecepteurEchange.get().getTransactions();
						walletRecepteurTransactions.add(transactionToRegistrate.get());
						walletRecepteurEchange.get().setTransactions(walletRecepteurTransactions);
						walletRepository.save(walletRecepteurEchange.get());
						walletCounterpart.get().setSoldeWallet(
								walletCounterpart.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
						List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
						walletCounterpartTransactions.add(transactionToRegistrate.get());
						walletCounterpart.get().setTransactions(walletCounterpartTransactions);
						walletRepository.save(walletCounterpart.get());
					}
				}
			}
		}

		// ************************************************************************************************************************************

		// HYPOTHESE 2 le walletEmetteurEchange appartient à la transaction et EMETTEUR
		// = VALIDE ************************************************
		else {

			// walletEmetteur = walletEmetteurEchange.get(); // H2 ---------------- WALLET
			// EMETTEUR = emetteur echange

			// HYPOTHESE 2A le walletRecepteurEchange n'appartient pas à la transaction =
			// LITIGE suite à simple REFUS du Récepteur

			if (!transactionToRegistrate.get().getWallets().contains(walletRecepteurEchange.get())) {

				// H2A --------------- WALLET EMETTEUR = emetteur echange et WALLET RECEPTEUR = COUNTERPART

				// SCENARIO 1 : RécepteurReponse a fait une offre donc il est créditeur dans la
				// transaction et émetteur est débiteur

				if (reponseFromEchange.get().getEnumTradeType().equals(EnumTradeType.OFFRE)) {
					// HYPOTHESE 1 : le solde du wallet de l'émetteur débiteur est insuffisant pour
					// régler la transaction sans passer sous le solde mini
					// walletEmetteur = COUNTERPART pour LITIGE et walletEmetteur = COUNTERPART pour
					// CONFLIT

					if ((walletEmetteurEchange.get().getSoldeWallet()
							- transactionToRegistrate.get().getMontant()) < soldeMini) {
						echangeFromTransaction.get().setStatutEchange(EnumStatutEchange.CONFLIT);
						blocageService.createBlocage(transactionToRegistrate.get().getId(),
								transactionToRegistrate.get().getEmetteurId(),
								transactionToRegistrate.get().getEmetteurUsername());
					
						List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
						walletCounterpartTransactions.add(transactionToRegistrate.get());
						walletCounterpart.get().setTransactions(walletCounterpartTransactions);
						walletRepository.save(walletCounterpart.get());
					} else {
						// HYPOTHESE 2 : le solde du wallet de l'émetteur débiteur est suffisant pour
						// régler la transaction sans passer sous le solde mini

						walletCounterpart.get().setSoldeWallet(
								walletCounterpart.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
						List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
						walletCounterpartTransactions.add(transactionToRegistrate.get());
						walletCounterpart.get().setTransactions(walletCounterpartTransactions);
						Integer soldeCounterpartToBeSaved = walletCounterpart.get().getSoldeWallet()
								+ transactionToRegistrate.get().getMontant();///

						walletRepository.save(walletCounterpart.get());

						walletEmetteurEchange.get().setSoldeWallet(walletEmetteurEchange.get().getSoldeWallet()
								- transactionToRegistrate.get().getMontant());
						List<Transaction> walletEmetteurTransactions = walletEmetteurEchange.get().getTransactions();
						walletEmetteurTransactions.add(transactionToRegistrate.get());
						walletEmetteurEchange.get().setTransactions(walletEmetteurTransactions);
						Integer soldeEmetteurToBeSaved = walletEmetteurEchange.get().getSoldeWallet()
								- transactionToRegistrate.get().getMontant();///

						walletRepository.save(walletEmetteurEchange.get());

					}
				}
				// SCENARIO 2 : Récepteur Réponse a fait une demande face à une proposition
				// d'offre, il est débiteur et emetteur est crediteur
				// HYPOTHDESE 4: le solde du wallet du récepteur débiteur est suffisant pour
				// régler la transaction sans passer sous le solde mini
				else {

					int newSolde = (walletCounterpart.get().getSoldeWallet())
							- (transactionToRegistrate.get().getMontant());
					walletCounterpart.get().setSoldeWallet(newSolde);
					List<Transaction> walletRecepteurTransactions = walletCounterpart.get().getTransactions();
					walletRecepteurTransactions.add(transactionToRegistrate.get());
					walletCounterpart.get().setTransactions(walletRecepteurTransactions);
					walletRepository.save(walletCounterpart.get());
					walletEmetteurEchange.get().setSoldeWallet(
							walletEmetteurEchange.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
					List<Transaction> walletEmetteurTransactions = walletEmetteurEchange.get().getTransactions();
					walletEmetteurTransactions.add(transactionToRegistrate.get());
					walletEmetteurEchange.get().setTransactions(walletEmetteurTransactions);
					walletRepository.save(walletEmetteurEchange.get());

				}

			} else {

				// HYPOTHESE 2B le walletRecepteurEchange appartient à la transaction comme le
				// walletEmetteur : il y a donc CONFIRMATION
				// SCENARIO 1 : RécepteurReponse a fait une offre donc il est créditeur dans la
				// transaction et émetteur est débiteur

				//H2B -------WALLET EMETTEUR = emetteur echange et WALLET RECEPTEUR = recepteur echange

				if (reponseFromEchange.get().getEnumTradeType().equals(EnumTradeType.OFFRE)) {
					// HYPOTHESE 1 : le solde du wallet de l'émetteur débiteur est insuffisant pour
					// régler la transaction sans passer sous le solde mini

					if ((walletEmetteurEchange.get().getSoldeWallet()
							- transactionToRegistrate.get().getMontant()) < soldeMini) {

						echangeFromTransaction.get().setStatutEchange(EnumStatutEchange.CONFLIT);
						blocageService.createBlocage(transactionToRegistrate.get().getId(),
								transactionToRegistrate.get().getEmetteurId(),
								transactionToRegistrate.get().getEmetteurUsername());
						walletRecepteurEchange.get().setSoldeWallet(walletRecepteurEchange.get().getSoldeWallet()
								+ transactionToRegistrate.get().getMontant());
						List<Transaction> walletRecepteurTransactions = walletRecepteurEchange.get().getTransactions();
						walletRecepteurTransactions.add(transactionToRegistrate.get());
						walletRecepteurEchange.get().setTransactions(walletRecepteurTransactions);
						walletRepository.save(walletRecepteurEchange.get());
						walletCounterpart.get().setSoldeWallet(
								walletCounterpart.get().getSoldeWallet() - transactionToRegistrate.get().getMontant());
						List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
						walletCounterpartTransactions.add(transactionToRegistrate.get());
						walletCounterpart.get().setTransactions(walletCounterpartTransactions);
						walletRepository.save(walletCounterpart.get());
					} else {

						// HYPOTHDESE 2 : le solde du wallet de l'émetteur débiteur est suffisant pour
						// régler la transaction sans passer sous le solde mini

						walletRecepteurEchange.get().setSoldeWallet(walletRecepteurEchange.get().getSoldeWallet()
								+ transactionToRegistrate.get().getMontant());
						List<Transaction> walletRecepteurTransactions = walletRecepteurEchange.get().getTransactions();
						walletRecepteurTransactions.add(transactionToRegistrate.get());
						walletRecepteurEchange.get().setTransactions(walletRecepteurTransactions);
						walletRepository.save(walletRecepteurEchange.get());
						walletEmetteurEchange.get().setSoldeWallet(walletEmetteurEchange.get().getSoldeWallet()
								- transactionToRegistrate.get().getMontant());
						List<Transaction> walletEmetteurTransactions = walletEmetteurEchange.get().getTransactions();
						walletEmetteurTransactions.add(transactionToRegistrate.get());
						walletEmetteurEchange.get().setTransactions(walletEmetteurTransactions);
						walletRepository.save(walletEmetteurEchange.get());
					}
				}
				// SCENARIO 2 : Récepteur Rponse a fait une demande face à une proposition
				// d'offre, il est débiteur et emetteur est crediteur
				// HYPOTHESE 3 : e solde du wallet du récepteur débiteur est insuffisant pour
				// régler la transaction sans passer sous le solde mini

				else if ((walletRecepteurEchange.get().getSoldeWallet()
						- transactionToRegistrate.get().getMontant()) < soldeMini) {

					echangeFromTransaction.get().setStatutEchange(EnumStatutEchange.CONFLIT);
					blocageService.createBlocage(transactionToRegistrate.get().getId(),
							transactionToRegistrate.get().getRecepteurId(),
							transactionToRegistrate.get().getRecepteurUsername());
					walletEmetteurEchange.get().setSoldeWallet(
							walletEmetteurEchange.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
					List<Transaction> walletEmetteurTransactions = walletEmetteurEchange.get().getTransactions();
					walletEmetteurTransactions.add(transactionToRegistrate.get());
					walletEmetteurEchange.get().setTransactions(walletEmetteurTransactions);
					walletRepository.save(walletEmetteurEchange.get());
					walletCounterpart.get().setSoldeWallet(
							walletCounterpart.get().getSoldeWallet() - transactionToRegistrate.get().getMontant());
					List<Transaction> walletCounterpartTransactions = walletCounterpart.get().getTransactions();
					walletCounterpartTransactions.add(transactionToRegistrate.get());
					walletCounterpart.get().setTransactions(walletCounterpartTransactions);
					walletRepository.save(walletCounterpart.get());
				} else {

					// HYPOTHDESE 4: le solde du wallet du récepteur débiteur est suffisant pour
					// régler la transaction sans passer sous le solde mini

					int newSolde = (walletRecepteurEchange.get().getSoldeWallet())
							- (transactionToRegistrate.get().getMontant());
					walletRecepteurEchange.get().setSoldeWallet(newSolde);
					List<Transaction> walletRecepteurTransactions = walletRecepteurEchange.get().getTransactions();
					walletRecepteurTransactions.add(transactionToRegistrate.get());
					walletRecepteurEchange.get().setTransactions(walletRecepteurTransactions);
					walletRepository.save(walletRecepteurEchange.get());
					walletEmetteurEchange.get().setSoldeWallet(
							walletEmetteurEchange.get().getSoldeWallet() + transactionToRegistrate.get().getMontant());
					List<Transaction> walletEmetteurTransactions = walletEmetteurEchange.get().getTransactions();
					walletEmetteurTransactions.add(transactionToRegistrate.get());
					walletEmetteurEchange.get().setTransactions(walletEmetteurTransactions);
					walletRepository.save(walletEmetteurEchange.get());
				}
			}
		}

	}

	@Override
	public Wallet readByUserId(String userId) throws EntityNotFoundException {

		Optional<Wallet> walletToRead = walletRepository.findByTitulaireId(userId);
		if (!walletToRead.isPresent())
			throw new EntityNotFoundException("Il n'existe pas de wallet pour cet adhérent");

		return walletToRead.get();
	}

	@Override
	public Page<Wallet> searchAllWalletsByCriteria(WalletCriteria walletCriteria, Pageable pageable) {
		Specification<Wallet> walletSpecification = new WalletSpecification(walletCriteria);
		Page<Wallet> wallets = walletRepository.findAll(walletSpecification, pageable);
		return wallets;
	}

	@Override
	public Wallet readById(@Valid Long id) throws EntityNotFoundException {
		Optional<Wallet> walletToRead = walletRepository.findById(id);
		if (walletToRead.isEmpty())
			throw new EntityNotFoundException("Il n'existe pas de wallet pour cet adhérent");

		return walletToRead.get();
	}
}
