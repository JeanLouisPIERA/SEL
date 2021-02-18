package com.microselwebui.microselwebui.objets;

import java.util.Optional;

public enum EnumTradeType {
	
	OFFRE ("OFFRE", "OFFRE"),
	DEMANDE ("DEMANDE", "DEMANDE"),
	INCONNU ("INCONNU", "INCONNU");
	
	 private String code;
	  private String text;
	  
	private EnumTradeType(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static Optional<EnumTradeType> getEnumTradeTypeByCode(String code) {
		for (EnumTradeType enumTradeType : EnumTradeType.values()) {
			if(enumTradeType.code.equals(code)){
				return Optional.of(enumTradeType);
			}
		}
		return Optional.empty();
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
		if(this==OFFRE) {
			return"Offre";
		}else if (this==DEMANDE) {
			return"Demande";
		}
		return super.toString();
	}   
	
	public static EnumTradeType fromValueCode(String code) {
		try {
	        return valueOf(code);
	    } catch (IllegalArgumentException e) {
	        return EnumTradeType.INCONNU;
	    }
	}

}
