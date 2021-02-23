package com.microselbourse.service;

import com.microselbourse.entities.Transaction;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface ITransactionService {
	
	Transaction createTransaction(Long echangeId) throws EntityNotFoundException, EntityAlreadyExistsException;

}
