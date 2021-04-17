package com.microselbourse.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselbourse.criteria.WalletCriteria;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IWalletService {

	Wallet createWallet(String adherentId) throws EntityAlreadyExistsException;

	void enregistrerTransaction(Long trnsactionId) throws EntityNotFoundException;

	Wallet readByUserId(String userId) throws EntityNotFoundException;

	Page<Wallet> searchAllWalletsByCriteria(WalletCriteria walletCriteria, Pageable pageable);

	Wallet readById(@Valid Long id) throws EntityNotFoundException;

}
