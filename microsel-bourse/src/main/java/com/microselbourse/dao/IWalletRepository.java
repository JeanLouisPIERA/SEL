package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Wallet;

/**
 * * Classe permettant d'implémenter l'interface JPA pour les relations ORM de la classe Wallet = Portefeuilles
 * @author jeanl
 *
 */
@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {

	Optional<Wallet> readByTitulaireId(String titulaireId);
	
	Optional<Wallet> findByTitulaireId(String titulaireId);

}
