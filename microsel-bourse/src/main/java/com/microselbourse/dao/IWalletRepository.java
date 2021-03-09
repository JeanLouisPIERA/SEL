package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Wallet;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet>{
	
	Optional<Wallet> readByTitulaireId (Long titulaireId);

	
}
