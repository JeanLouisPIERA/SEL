package com.microselbourse.entities;


public enum EnumStatutEchange {
	
	ENREGISTRE ("ENREGISTRE", "ENREGISTRE"),
	CONFIRME ("CONFIRME", "CONFIRME"),  
	ANNULE ("ANNULE", "ANNULE"),
	ECHU("ECHU", "ECHU"),
	CLOTURE ("CLOTURE", "CLOTURE"),		
	LITIGE ("LITIGE", "LITIGE"),
	SUPPRIME ("SUPPRIME", "SUPPRIME"),
	FORCEVALID ("FORCEVALID", "FORCEVALID"),
	FORCEREFUS ("FORCEREFUS", "FORCEREFUS"),
	CONFLIT ("CONFLIT", "CONFLIT"), 
	INCONNUE ("INCONNUE", "INCONNUE")
	;
	
	 private String code;
	  private String text;
	  
	private EnumStatutEchange(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static EnumStatutEchange getEnumStatutEchangeByCode(String code) {
		for (EnumStatutEchange enumStatutEchange : EnumStatutEchange.values()) {
			if(enumStatutEchange.code.equals(code)){
				return enumStatutEchange;
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
		if(this==ENREGISTRE) {
			return"Echange enregistré";
		}else if (this==CONFIRME) {
			return"Echange confirmé";
		}else if (this==ANNULE) {
			return"Echange annulé";
		}else if (this==ECHU) {
			return"Echange échu sans confirmation ni annulation";	
		}else if (this==CLOTURE) {
			return"Echange clôturé";
		}else if (this==LITIGE) {
			return"Echange en litige";
		}else if (this==SUPPRIME) {
			return"Echange supprimé";
		}else if (this==FORCEVALID) {
			return"Echange réputé validé";
		}else if (this==FORCEREFUS) {
			return"Echange réputé refusé";
		}else if (this==CONFLIT) {
			return"Echange en conflit";
		}
		return super.toString();
	}   
	
	public static EnumStatutEchange fromValueCode(String code) {
		try {
	        return valueOf(code);
	    } catch (IllegalArgumentException e) {
	        return EnumStatutEchange.INCONNUE;
	    }
	}

}
