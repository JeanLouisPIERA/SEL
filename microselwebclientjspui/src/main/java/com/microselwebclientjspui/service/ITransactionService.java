package com.microselwebclientjspui.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.objets.Transaction;

public interface ITransactionService {
	
	Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable); 

}
