package com.microselbourse.entities;

public enum EnumCategorie {
	
	BRICOLAGE ("BRICOLAGE", "BRICOLAGE"),
	JARDINAGE ("JARDINAGE", "JARDINAGE"),  
	ALIMENTATION ("ALIMENTATION", "ALIMENTATION"),		
	BROCANTE ("BROCANTE", "BROCANTE"),		
	COURS ("COURS", "COURS"),
	LANGUE ("LANGUE", "LANGUE"),
	ACCOMPAGNEMENT ("ACCOMPAGNEMENT", "ACCOMPAGNEMENT"),
	COVOITURAGE ("COVOITURAGE", "COVOITURAGE"),
	PRET ("PRET", "PRET"), 
	INCONNUE ("INCONNUE","INCONNUE")
	;
	
	 private String code;
	  private String text;
	  
	private EnumCategorie(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static EnumCategorie getEnumCategorieByCode(String code) {
		for (EnumCategorie enumCategorie : EnumCategorie.values()) {
			if(enumCategorie.code.equals(code)){
				return enumCategorie;
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
		if(this==BRICOLAGE) {
			return"Coup de main en bricolage";
		}else if (this==JARDINAGE) {
			return"Coup de main en jardinage";
		}else if (this==ALIMENTATION) {
			return"Produits frais du jardin ou cuisinés maison";
		}else if (this==BROCANTE) {
			return"Articles d'occasion en bon état";
		}else if (this==COURS) {
			return"Soutien scolaire ou cours spécialisés";
		}else if (this==LANGUE) {
			return"Traduction ou interprétariat ";
		}else if (this==ACCOMPAGNEMENT) {
			return"Baby-sitting ou garde à domicile";
		}else if (this==COVOITURAGE) {
			return"Covoiturage";
		}else if (this==PRET) {
			return"Prêt de matériel ou de livres";
		}
		return super.toString();
	}   
	
	public static EnumCategorie fromValueCode(String code) {
		try {
	        return valueOf(code);
	    } catch (IllegalArgumentException e) {
	        return EnumCategorie.INCONNUE;
	    }
	}

}
