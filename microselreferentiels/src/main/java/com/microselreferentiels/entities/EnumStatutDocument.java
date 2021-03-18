package com.microselreferentiels.entities;



public enum EnumStatutDocument {
	
	ENCOURS ("ENCOURS","ENCOURS"),
	ARCHIVE ("ARCHIVE","ARCHIVE"),
	INCONNU ("INCONNU","INCONNU")
	;
	
	 private String code;
	  private String text;
	  
	private EnumStatutDocument(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static EnumStatutDocument getEnumStatutDocumentByCode(String code) {
		for (EnumStatutDocument enumStatutDocument : EnumStatutDocument.values()) {
			if(enumStatutDocument.code.equals(code)){
				return enumStatutDocument;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		if(this==ENCOURS) {
			return"Document en-cours";
		}else if (this==ARCHIVE) {
			return"Document archivé";
		}
		return super.toString();
	}   
	
	public static EnumStatutDocument fromValueCode(String code) {
		try {
	        return valueOf(code);
	    } catch (IllegalArgumentException e) {
	        return EnumStatutDocument.INCONNU;
	    }
	}

}
