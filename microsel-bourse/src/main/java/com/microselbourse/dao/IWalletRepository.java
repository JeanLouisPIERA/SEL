package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microselbourse.entities.Wallet;

public interface IWalletRepository extends JpaRepository<Wallet, Long>{
	
	Optional<Wallet> readByTitulaireId (Long titulaireId);

}
