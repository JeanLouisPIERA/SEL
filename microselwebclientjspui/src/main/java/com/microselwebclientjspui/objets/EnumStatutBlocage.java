package com.microselwebclientjspui.objets;

import java.util.ArrayList;
import java.util.List;

public enum EnumStatutBlocage {
	
	ENCOURS ("ENCOURS", "ENCOURS"),
	ANNULE ("ANNULE", "ANNULE"), 
	INCONNU ("INCONNU", "INCONNU")
	;
	
	 private String code;
	  private String text;
	  
	private EnumStatutBlocage(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static EnumStatutBlocage getEnumStatutBlocageByCode(String code) {
		for (EnumStatutBlocage enumStatutBlocage : EnumStatutBlocage.values()) {
			if(enumStatutBlocage.code.equals(code)){
				return enumStatutBlocage;
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
			return"Blocage en-cours";
		}else if (this==ANNULE) {
			return"Blocage annulé";
		}
		return super.toString();
	}   
	
	public static EnumStatutBlocage fromValueCode(String code) {
		try {
	        return valueOf(code);
	    } catch (IllegalArgumentException e) {
	        return EnumStatutBlocage.INCONNU;
	    }
	}
	
	public static List<EnumStatutBlocage> getListEnumStatutBlocage() {

		List<EnumStatutBlocage> ListEnumStatutBlocage = new ArrayList<EnumStatutBlocage>();
		for (EnumStatutBlocage enumStatutBlocage : values()) {
			ListEnumStatutBlocage.add(enumStatutBlocage);
		}
		return ListEnumStatutBlocage;

	}


}
