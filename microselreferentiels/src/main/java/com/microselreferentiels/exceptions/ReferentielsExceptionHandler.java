package com.microselreferentiels.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReferentielsExceptionHandler {
	
	/**
	 * Cette méthode permet de gérer l'exception métier personnalisée "Entity Not Found"
	 * Https Status NOT FOUND 413
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ReferentielsExceptionReponse handleCustomException(EntityNotFoundException ex1) {
		ReferentielsExceptionReponse exceptionReponse = new ReferentielsExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex1.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		return exceptionReponse;
	}
	
		
	/**
	 * Cette méthode permet de gérer l'exception personnalisée " Entity Already Exists "
	 *HttpStatus CONFLICT 409
	 * @param ex2
	 * @return
	 */
	
	@ExceptionHandler (EntityAlreadyExistsException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ReferentielsExceptionReponse handleCustomException(EntityAlreadyExistsException ex4) {
		ReferentielsExceptionReponse exceptionReponse = new ReferentielsExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex4.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.CONFLICT.value());
		return exceptionReponse;
		
	}
	
	/**
	 * Cette méthode permet de gérer l'exception personnalisée " Entity Already Exists"
	 * Http Status LOCKED CODE 423
	 * L’opération ne peut avoir lieu, car la ressource est verrouillée
	 * @param ex2
	 * @return
	 */
	
	@ExceptionHandler (DeniedAccessException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.LOCKED)
	public ReferentielsExceptionReponse handleCustomException(DeniedAccessException ex5) {
		ReferentielsExceptionReponse exceptionReponse = new ReferentielsExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex5.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.LOCKED.value());
		return exceptionReponse;
		
	}
	

}
