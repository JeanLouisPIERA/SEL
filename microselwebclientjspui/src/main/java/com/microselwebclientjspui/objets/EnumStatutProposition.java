package com.microselwebclientjspui.objets;

import java.util.ArrayList;
import java.util.List;

public enum EnumStatutProposition {

	ENCOURS("ENCOURS", "ENCOURS"), ECHUE("ECHUE", "ECHUE"), CLOTUREE("CLOTUREE", "CLOTUREE"),
	INCONNU("INCONNU", "INCONNU");

	private String code;
	private String text;

	private EnumStatutProposition(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public static EnumStatutProposition getEnumStatutEchangeByCode(String code) {
		for (EnumStatutProposition enumStatutEchange : values()) {
			if (enumStatutEchange.code.equals(code)) {
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
		if (this == ENCOURS) {
			return "Proposition en-cours";
		} else if (this == ECHUE) {
			return "Proposition échue";
		} else if (this == CLOTUREE) {
			return "Proposition clôturée";
		} else if (this == INCONNU) {
			return "Aucun type ne correspon à ce statut de proposition";
		}
		return super.toString();
	}

	public static EnumStatutProposition fromValueCode(String code) {
		try {
			return valueOf(code);
		} catch (IllegalArgumentException e) {
			return EnumStatutProposition.INCONNU;
		}
	}

	public static List<EnumStatutProposition> getListEnumStatutProposition() {

		List<EnumStatutProposition> ListEnumStatutProposition = new ArrayList<EnumStatutProposition>();
		for (EnumStatutProposition enumStatutProposition : values()) {
			ListEnumStatutProposition.add(enumStatutProposition);
		}
		return ListEnumStatutProposition;

	}

}
