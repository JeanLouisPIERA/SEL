package com.microselbourse.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselbourse.entities.Transaction;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface ITransactionService {
	
	Transaction createTransaction(Long echangeId) throws EntityNotFoundException, EntityAlreadyExistsException;
	
	Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable) throws EntityNotFoundException;

}
