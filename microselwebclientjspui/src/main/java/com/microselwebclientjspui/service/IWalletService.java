package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.WalletCriteria;
import com.microselwebclientjspui.objets.Wallet;

public interface IWalletService {

	Wallet searchByAdherentId(Long adherentId);

	Page<Wallet> searchByCriteria(WalletCriteria walletCriteria, Pageable pageable);

	Wallet searchById(Long id);

}