package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IEvaluationRepository;
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
import com.microselbourse.service.IWalletService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

	@Mock
	private IMicroselUsersProxy userProxy;

	@Mock
	private ITransactionRepository transactionRepository;

	@Mock
	private IEchangeRepository echangeRepository;

	@Mock
	private IReponseRepository reponseRepository;

	@Mock
	private IWalletRepository walletRepository;

	@Mock
	private IWalletService walletService;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	private UserBean user;
	private Echange echange;
	private Transaction transaction;
	private Transaction transactionTest;
	private Reponse reponse;
	private Pageable pageable;
	private Wallet wallet;
	private Wallet wallet1;
	private List<Wallet> wallets;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		user = new UserBean();
		echange = new Echange();
		transaction = new Transaction();
		transactionTest = new Transaction();
		reponse = new Reponse();
		wallet = new Wallet();
		wallet1 = new Wallet();
		wallets = new ArrayList<Wallet>();

		when(userProxy.consulterCompteAdherent((String) "A")).thenReturn(user);
		when(userProxy.consulterCompteAdherent((String) "B")).thenReturn(user);

		when(echangeRepository.findById((long) 1)).thenReturn(Optional.of(echange));
		when(echangeRepository.findById((long) 0)).thenReturn(Optional.empty());

		when(reponseRepository.findById((long) 1)).thenReturn(Optional.of(reponse));
		when(reponseRepository.findById((long) 0)).thenReturn(Optional.empty());

		when(transactionRepository.findById((long) 1)).thenReturn(Optional.of(transaction));
		when(transactionRepository.findById((long) 0)).thenReturn(Optional.empty());

		when(walletRepository.findById((long) 1)).thenReturn(Optional.of(wallet));
		when(walletRepository.findById((long) 0)).thenReturn(Optional.empty());

		when(echangeRepository.findById((long) 2)).thenReturn(Optional.of(echange));
		when(reponseRepository.findById((long) 2)).thenReturn(Optional.of(reponse));
		when(transactionRepository.findById((long) 2)).thenReturn(Optional.empty());

		when(walletRepository.readByTitulaireId("A")).thenReturn(Optional.of(wallet));
		when(walletRepository.readByTitulaireId("000.000.000")).thenReturn(Optional.of(wallet1));
		when(walletRepository.readByTitulaireId("B")).thenReturn(Optional.empty());

		try {
			Mockito.doNothing().when(walletService).enregistrerTransaction((long) 2);
		} catch (EntityNotFoundException e) {
		}

		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		try {
			when(walletService.createWallet("B")).thenReturn(wallet);
		} catch (EntityAlreadyExistsException e) {
		}

	}

	@Test
	public void createTransaction_whenEntityNotFoundException_withNoEchange() {

		echange.setId((long) 0);

		try {
			transactionTest = transactionService.createTransaction((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'échange qui sert de base à la création de votre transaction n'existe pas");
		}

	}

	@Test
	public void createTransaction_whenEntityAlreadyExistsException() {

		echange.setId((long) 1);
		transaction.setId((long) 1);

		try {
			transactionTest = transactionService.createTransaction((long) 1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
					.hasMessage("La transaction que vous voulez enregistrer existe déjà");
		}
	}

	@Test
	public void findAllByWalletId_withEntityNotFoundException() {

		pageable = PageRequest.of(0, 6);

		try {
			Page<Transaction> transactions = transactionService.findAllByWalletId((long) 0, pageable);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Il n'existe aucun portefeuille enregistré pour l'identifiant indiqué");
		}

	}

	@Test
	public void createTransaction_withoutException() throws EntityNotFoundException, EntityAlreadyExistsException {

		echange.setId((long) 2);
		reponse.setId((long) 2);
		transaction.setId((long) 2);

		echange.setStatutEchange(EnumStatutEchange.LITIGE);///--------------------------------------> SCENARIO 1
		
		echange.setAvisEmetteur(EnumEchangeAvis.VALIDE);
		echange.setEmetteurId("A");
		wallets.add(wallet);
		wallets.add(wallet1);

		transactionTest = transactionService.createTransaction((long) 2);
		verify(transactionRepository, times(1)).save(any(Transaction.class));
		Assert.assertTrue(transactionTest.equals(transaction));

		echange.setEmetteurId("B");
		wallets.add(wallet);
		wallets.add(wallet1);

		transactionTest = transactionService.createTransaction((long) 2);
		verify(transactionRepository, times(2)).save(any(Transaction.class));
		Assert.assertTrue(transactionTest.equals(transaction));

		echange.setAvisEmetteur(EnumEchangeAvis.REFUSE);
		echange.setAvisRecepteur(EnumEchangeAvis.VALIDE);
		echange.setRecepteurId("B");
		wallets.add(wallet);
		wallets.add(wallet1);

		transactionTest = transactionService.createTransaction((long) 2);
		verify(transactionRepository, times(3)).save(any(Transaction.class));
		Assert.assertTrue(transactionTest.equals(transaction));

		echange.setRecepteurId("A");
		wallets.add(wallet);
		wallets.add(wallet1);

		transactionTest = transactionService.createTransaction((long) 2);
		verify(transactionRepository, times(4)).save(any(Transaction.class));
		Assert.assertTrue(transactionTest.equals(transaction));

		echange.setAvisEmetteur(EnumEchangeAvis.REFUSE);
		echange.setAvisRecepteur(EnumEchangeAvis.REFUSE);
		wallets.add(wallet);
		wallets.add(wallet1);
		
		transactionTest = transactionService.createTransaction((long) 2);
		verify(transactionRepository, times(5)).save(any(Transaction.class));
		Assert.assertTrue(transactionTest.equals(transaction));

	}

}
