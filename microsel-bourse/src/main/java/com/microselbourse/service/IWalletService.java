package com.microselbourse.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselbourse.criteria.WalletCriteria;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IWalletService {
	
	Wallet createWallet(Long adherentId) throws EntityAlreadyExistsException;
	
	void enregistrerTransaction(Long trnsactionId) throws EntityNotFoundException; 
	
	Wallet readByUserId (Long userId) throws EntityNotFoundException;

	Page<Wallet> searchAllWalletsByCriteria(WalletCriteria walletCriteria, Pageable pageable);
 
}
