package com.microseladherent.errors;

import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe permet au RestErrorControllerAdvice d'identifier les fields error et de processer la validation de l'erreur 
 * qui sera générera une MethodArgumentNotValidException 
 * @author jeanl
 *
 */
public class ValidationErrorDTO {
	 
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
 
    public ValidationErrorDTO() {
 
    }
 
    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

	
	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}

	
	public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
    
    

}