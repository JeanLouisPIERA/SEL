package com.microseladherent.errors;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class RestErrorHandlerControllerAdvice {

 
	//Cette première partie permet de gérer et de customiser le message d'erreur du @Valid Error sur le DTO ***************
	//@RequestBody
	
    private MessageSource messageSource;
 
    @Autowired
    public RestErrorHandlerControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
 
        return processFieldErrors(fieldErrors);
    }
    
	/*
	 * @ExceptionHandler(ConstraintViolationException.class)
	 * 
	 * @ResponseStatus(code = HttpStatus.BAD_REQUEST)
	 * 
	 * @ResponseBody public FieldErrorDTO processconstraintVioationError(
	 * ConstraintViolationException ex1) { BindingResult result =
	 * ((ConstraintViolationException) ex1).getBindingResult(); List<FieldError>
	 * fieldErrors = result.getFieldErrors();
	 * 
	 * return processFieldErrors(fieldErrors);
	 * 
	 * }
	 */
    
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public FieldErrorDTO processconstraintViolationError(IllegalStateException ex1) {
        
    	String field = ex1.getCause().toString();
    	String message =ex1.getMessage();
    	FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(field, message);
 
        return fieldErrorDTO;
    }
    
  
    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();
 
        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }
 
        return dto;
    }
 
    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
 

        //Si le message d'erreur n'est pas personnalisé par l'annotation du DTO, 
        //on retourne le message d'erreur par défaut de SpringBoot Validation
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
 
        return localizedErrorMessage;
    }
    
    // Cette 2ème partie permet de gérer et de customiser le message d'erreur @Validated de la classe controller
    //@RequestParam et @PathVariable
    
	
	  @ExceptionHandler(ConstraintViolationException.class) 
	  public void handleConstraintViolationError(HttpServletResponse response) throws
	  IOException { response.sendError(HttpStatus.BAD_REQUEST.value()); }
	 
    
    }
    
    
    

