package com.microselbourse.entities;


public enum EnumTradeType {
	
	OFFRE ("OFFRE", "OFFRE"),
	DEMANDE ("DEMANDE", "DEMANDE");
	
	 private String code;
	  private String text;
	  
	private EnumTradeType(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static EnumTradeType getEnumTradeTypeByCode(String code) {
		for (EnumTradeType enumTradeType : EnumTradeType.values()) {
			if(enumTradeType.code.equals(code)){
				return enumTradeType;
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
		if(this==OFFRE) {
			return"Offre";
		}else if (this==DEMANDE) {
			return"Demande";
		}
		return super.toString();
	}   

}
