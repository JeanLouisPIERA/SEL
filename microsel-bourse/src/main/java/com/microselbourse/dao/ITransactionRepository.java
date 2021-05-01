package com.microselbourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Transaction;

/**
 * * Classe permettant d'implémenter l'interface JPA pour les relations ORM de la classe Transaction
 * @author jeanl
 *
 */
@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

}
