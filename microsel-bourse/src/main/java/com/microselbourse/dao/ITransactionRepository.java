package com.microselbourse.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long>{
	


}
