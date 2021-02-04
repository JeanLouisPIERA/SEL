package com.microseladherent.errors;
/**
 * Cette classe est utilisée pour la identifier le champ du DTO non validé dans une @RequestBody
 * @author jeanl
 *
 */
public class FieldErrorDTO {
	 
    private String field;
 
    private String message;
 
    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

	
	public String getField() {
		return field;
	}

	
	public void setField(String field) {
		this.field = field;
	}

	
	public String getMessage() {
		return message;
	}

	
	public void setMessage(String message) {
		this.message = message;
	}
    
    
}