package com.microselwebclientjspui.objets;

public enum EnumEchangeAvis {

	ANOMALIE("ANOMALIE", "ANOMALIE"), VALIDE("VALIDE", "VALIDE"), SANS("SANS", "SANS"), REFUSE("REFUSE", "REFUSE"),
	INCONNU("INCONNU", "INCONNU");

	private String code;
	private String text;

	private EnumEchangeAvis(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public static EnumEchangeAvis getEnumEchangeAvisByCode(String code) {
		for (EnumEchangeAvis enumEchangeAvis : EnumEchangeAvis.values()) {
			if (enumEchangeAvis.code.equals(code)) {
				return enumEchangeAvis;
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
		if (this == VALIDE) {
			return "Termes de l'échange validés";
		} else if (this == REFUSE) {
			return "Termes de l'échange refusés";
		} else if (this == SANS) {
			return "Pas d'avis sur les termes de l'échange";
		} else if (this == ANOMALIE) {
			return "La conclusion de l'échange est en anomalie";
		}
		return super.toString();
	}

	public static EnumEchangeAvis fromValueCode(String code) {
		try {
			return valueOf(code);
		} catch (IllegalArgumentException e) {
			return EnumEchangeAvis.INCONNU;
		}
	}

}
