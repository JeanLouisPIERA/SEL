package com.microselbourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microselbourse.entities.Transaction;


public interface ITransactionRepository extends JpaRepository<Transaction, Long>{

}
