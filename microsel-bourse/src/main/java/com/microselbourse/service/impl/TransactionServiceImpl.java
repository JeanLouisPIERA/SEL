package com.microselbourse.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.ITransactionRepository;
import com.microselbourse.dao.IWalletRepository;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumEchangeAvis;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Transaction;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.ITransactionService;
import com.microselbourse.service.IWalletService;

@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	IReponseRepository reponseRepository;

	@Autowired
	IEchangeRepository echangeRepository;

	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	IWalletRepository walletRepository;

	@Autowired
	IMicroselUsersProxy usersProxy;

	@Autowired
	IWalletService walletService;

	@Override
	public Transaction createTransaction(Long echangeId) throws EntityNotFoundException, EntityAlreadyExistsException {

		Optional<Echange> echangeToTransaction = echangeRepository.findById(echangeId);
		if (echangeToTransaction.isEmpty())
			throw new EntityNotFoundException(
					"L'échange qui sert de base à la création de votre transaction n'existe pas");

		Optional<Transaction> transactionAlreadyCreated = transactionRepository.findById(echangeId);
		if (!transactionAlreadyCreated.isEmpty())
			throw new EntityAlreadyExistsException("La transaction que vous voulez enregistrer existe déjà");

		Optional<Reponse> reponseFromEchange = reponseRepository.findById(echangeId);
		if (reponseFromEchange.isEmpty())
			throw new EntityNotFoundException(
					"Le détail des informations concernant votre transaction n'est pas accessible");

		Transaction transactionToCreate = new Transaction();
		transactionToCreate.setId(echangeId);
		transactionToCreate.setDateTransaction(LocalDate.now());
		transactionToCreate.setMontant(reponseFromEchange.get().getValeur());
		transactionToCreate.setTitreEchange(reponseFromEchange.get().getTitre());
		transactionToCreate.setEmetteurUsername(echangeToTransaction.get().getEmetteurUsername());
		transactionToCreate.setRecepteurUsername(echangeToTransaction.get().getRecepteurUsername());
		transactionToCreate.setEmetteurId(echangeToTransaction.get().getEmetteurId());
		transactionToCreate.setRecepteurId(echangeToTransaction.get().getRecepteurId());

		UserBean emetteur = usersProxy.consulterCompteAdherent(echangeToTransaction.get().getEmetteurId());
		UserBean recepteur = usersProxy.consulterCompteAdherent(echangeToTransaction.get().getRecepteurId());

		// **********************************************************************************************************************************

		// SCENARIO 1 L'ECHANGE EST EN STATUT LITIGE

		// Cela signifie que le WALLET COUNTERPART va être utilisé au moins 1 fois

		if (echangeToTransaction.get().getStatutEchange().equals(EnumStatutEchange.LITIGE)) {

			// 3 POSSIBILIES :
			// EMETTEUR VALIDE ET RECEPTEUR FORCEMENT REFUS (SCENARIO 1EV-RR),
			// EMETTEUR REFUS ET RECEPTEUR VALIDE (SCENARIO 1ER-RV)
			// EMETTEUR ET RECEPTEUR REFUS (SCENARIO 1ER-RR)

			// SCENARIO 1EV-RR - PAS BESOIN DE VERIFIER QUE AVIS RECEPTEUR = REFUS PUISQUE
			// ECHANGE = LITIGE
			if (echangeToTransaction.get().getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {

				Optional<Wallet> walletEmetteur = walletRepository
						.readByTitulaireId(echangeToTransaction.get().getEmetteurId());
				if (walletEmetteur.isEmpty()) {
					Wallet walletEmetteurToCreate = walletService
							.createWallet(echangeToTransaction.get().getEmetteurId());

					Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId((String) "000.000.000");
					if (walletRecepteur.isEmpty())
						throw new EntityNotFoundException(
								"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

					List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteurToCreate,
							walletRecepteur.get());
					transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

					Transaction transactionSaved = transactionRepository.save(transactionToCreate);

					walletService.enregistrerTransaction(transactionSaved.getId());

					return transactionSaved;
				}

				Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId((String) "000.000.000");
				if (walletRecepteur.isEmpty())
					throw new EntityNotFoundException(
							"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

				List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
						walletRecepteur.get());
				transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

				Transaction transactionSaved = transactionRepository.save(transactionToCreate);

				walletService.enregistrerTransaction(transactionSaved.getId());

				return transactionSaved;

			}

			// SCENARIO 1ER - .....
			else if (echangeToTransaction.get().getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)) {
				// SCENARIO 1ER-RV
				if (echangeToTransaction.get().getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {

					Optional<Wallet> walletRecepteur = walletRepository
							.readByTitulaireId(echangeToTransaction.get().getRecepteurId());
					if (walletRecepteur.isEmpty()) {
						Wallet walletRecepteurToCreate = walletService
								.createWallet(echangeToTransaction.get().getRecepteurId());

						Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId((String) "000.000.000");
						if (walletRecepteur.isEmpty())
							throw new EntityNotFoundException(
									"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

						List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
								walletRecepteurToCreate);
						transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

						Transaction transactionSaved = transactionRepository.save(transactionToCreate);

						walletService.enregistrerTransaction(transactionSaved.getId());

						return transactionSaved;
					}

					Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId((String) "000.000.000");
					if (walletRecepteur.isEmpty())
						throw new EntityNotFoundException(
								"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

					List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
							walletRecepteur.get());
					transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

					Transaction transactionSaved = transactionRepository.save(transactionToCreate);

					walletService.enregistrerTransaction(transactionSaved.getId());

					return transactionSaved;

				} else {
					// SCENARION 1ER-RR - Puisqu'il y a 2 refus, la transaction se fait au débit et
					// au crédit par le compte COUNTERPART = double anomalie
					Optional<Wallet> walletCounterpart = walletRepository.readByTitulaireId((String) "000.000.000");
					if (walletCounterpart.isEmpty())
						throw new EntityNotFoundException(
								"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

					List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletCounterpart.get());
					transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

					Transaction transactionSaved = transactionRepository.save(transactionToCreate);
					walletService.enregistrerTransaction(transactionSaved.getId());

					return transactionSaved;
				}
			}
		}

		// *******************************************************************************************************************************

		// SCENARIO 2 L'ECHANge EST STATUT CLOTURE : cela signifie que le WALLET
		// COUNTERPART ne sera pas utilisé
		// 1 SEULE POSSIBILITE : EMETTEUR ET RECEPTEUR VALIDE (SCENARIO 2EV-RV)

		else if (echangeToTransaction.get().getStatutEchange().equals(EnumStatutEchange.CLOTURE)) {

			Optional<Wallet> walletEmetteur = walletRepository
					.readByTitulaireId(echangeToTransaction.get().getEmetteurId());
			if (walletEmetteur.isEmpty())

			{
				Wallet walletEmetteurToCreate = walletService.createWallet(echangeToTransaction.get().getEmetteurId());

				Optional<Wallet> walletRecepteur = walletRepository
						.readByTitulaireId(echangeToTransaction.get().getRecepteurId());
				if (walletRecepteur.isEmpty())

				{
					Wallet walletRecepteurToCreate = walletService
							.createWallet(echangeToTransaction.get().getRecepteurId());

					List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteurToCreate,
							walletRecepteurToCreate);
					transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

					Transaction transactionSaved = transactionRepository.save(transactionToCreate);

					walletService.enregistrerTransaction(transactionSaved.getId());

					return transactionSaved;
				}
				List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteurToCreate,
						walletRecepteur.get());
				transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

				Transaction transactionSaved = transactionRepository.save(transactionToCreate);

				walletService.enregistrerTransaction(transactionSaved.getId());

				return transactionSaved;
			}

			Optional<Wallet> walletRecepteur = walletRepository
					.readByTitulaireId(echangeToTransaction.get().getRecepteurId());
			if (walletRecepteur.isEmpty())

			{
				Wallet walletRecepteurToCreate = walletService
						.createWallet(echangeToTransaction.get().getRecepteurId());

				List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
						walletRecepteurToCreate);
				transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

				Transaction transactionSaved = transactionRepository.save(transactionToCreate);

				walletService.enregistrerTransaction(transactionSaved.getId());

				return transactionSaved;
			}

			List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
					walletRecepteur.get());
			transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

			Transaction transactionSaved = transactionRepository.save(transactionToCreate);

			walletService.enregistrerTransaction(transactionSaved.getId());

			return transactionSaved;
		}

		// *******************************************************************************************************************************

		// SCENARIO 3 L'ECHANGE EST EN STATUT FORCEVALID

		// Cela signifie que le WALLET COUNTERPART va être utilisé au moins 1 fois

		else if (echangeToTransaction.get().getStatutEchange().equals(EnumStatutEchange.FORCEVALID)) {

			// 2 POSSIBILIES :
			// EMETTEUR VALIDE ET RECEPTEUR FORCEMENT SANS (SCENARIO 1EV-RR),
			// EMETTEUR SANS ET RECEPTEUR VALIDE (SCENARIO 1ER-RV)

			// SCENARIO 1EV-RR - PAS BESOIN DE VERIFIER QUE AVIS RECEPTEUR = SANS PUISQUE
			// ECHANGE = FORCEVALID
			if (echangeToTransaction.get().getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {

				Optional<Wallet> walletEmetteur = walletRepository
						.readByTitulaireId(echangeToTransaction.get().getEmetteurId());
				if (walletEmetteur.isEmpty()) {
					Wallet walletEmetteurToCreate = walletService
							.createWallet(echangeToTransaction.get().getEmetteurId());

					Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId((String) "000.000.000");
					if (walletRecepteur.isEmpty())
						throw new EntityNotFoundException(
								"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

					List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteurToCreate,
							walletRecepteur.get());
					transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

					Transaction transactionSaved = transactionRepository.save(transactionToCreate);

					walletService.enregistrerTransaction(transactionSaved.getId());

					return transactionSaved;
				}

				Optional<Wallet> walletRecepteur = walletRepository.readByTitulaireId((String) "000.000.000");
				if (walletRecepteur.isEmpty())
					throw new EntityNotFoundException(
							"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

				List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
						walletRecepteur.get());
				transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

				Transaction transactionSaved = transactionRepository.save(transactionToCreate);

				walletService.enregistrerTransaction(transactionSaved.getId());

				return transactionSaved;

			} else if (echangeToTransaction.get().getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
				// SCENARIO 1ER-RV

				Optional<Wallet> walletRecepteur = walletRepository
						.readByTitulaireId(echangeToTransaction.get().getRecepteurId());
				if (walletRecepteur.isEmpty()) {
					Wallet walletRecepteurToCreate = walletService
							.createWallet(echangeToTransaction.get().getRecepteurId());

					Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId((String) "000.000.000");
					if (walletRecepteur.isEmpty())
						throw new EntityNotFoundException(
								"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

					List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
							walletRecepteurToCreate);
					transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

					Transaction transactionSaved = transactionRepository.save(transactionToCreate);

					walletService.enregistrerTransaction(transactionSaved.getId());

					return transactionSaved;
				}

				Optional<Wallet> walletEmetteur = walletRepository.readByTitulaireId((String) "000.000.000");
				if (walletRecepteur.isEmpty())
					throw new EntityNotFoundException(
							"Transaction impossible : le portefeuille COUNTERPART n'existe pas ");

				List<Wallet> transactionToCreateInvolvedWallets = Arrays.asList(walletEmetteur.get(),
						walletRecepteur.get());
				transactionToCreate.setWallets(transactionToCreateInvolvedWallets);

				Transaction transactionSaved = transactionRepository.save(transactionToCreate);

				walletService.enregistrerTransaction(transactionSaved.getId());

				return transactionSaved;

			}
		}
		return null;
	}

	@Override
	public Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable) throws EntityNotFoundException {
		Optional<Wallet> walletFound = walletRepository.findById(walletId);
		if (!walletFound.isPresent())
			throw new EntityNotFoundException("Il n'existe aucun portefeuille enregistré pour l'identifiant indiqué");

		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > walletFound.get().getTransactions().size()
				? walletFound.get().getTransactions().size()
				: (start + pageable.getPageSize()));

		Page<Transaction> transactions = new PageImpl<Transaction>(
				walletFound.get().getTransactions().subList(start, end), pageable,
				walletFound.get().getTransactions().size());

		return transactions;
	}

}
