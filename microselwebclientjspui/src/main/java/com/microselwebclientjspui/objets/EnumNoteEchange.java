package com.microselwebclientjspui.objets;

public enum EnumNoteEchange {

	PASSATISFAISANT("PASSATISFAISANT", "PASSATISFAISANT"), PEUSATISFAISANT("PEUSATISFAISANT", "PEUSATISFAISANT"),
	CORRECT("CORRECT", "CORRECT"), SATISFAISANT("SATISFAISANT", "SATISFAISANT"),
	TRESSATISFAISANT("TRESSATISFAISANT", "TRESSATISFAISANT"), INCONNUE("INCONNUE", "INCONNUE");

	private String code;
	private String text;

	private EnumNoteEchange(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public static EnumNoteEchange getEnumNoteEchangeByCode(String code) {
		for (EnumNoteEchange enumNoteEchange : EnumNoteEchange.values()) {
			if (enumNoteEchange.code.equals(code)) {
				return enumNoteEchange;
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
		if (this == PASSATISFAISANT) {
			return "Expérience pas du tout satisfaisante";
		} else if (this == PEUSATISFAISANT) {
			return "Expérience pas trés satisfaisante";
		} else if (this == CORRECT) {
			return "Expérience moyennement satisfaisante";
		} else if (this == SATISFAISANT) {
			return "Expérience tout à fait satisfaisante";
		} else if (this == TRESSATISFAISANT) {
			return "Expérience extrêmement satisfaisante";
		}
		return super.toString();
	}

	public static EnumNoteEchange fromValueCode(String code) {
		try {
			return valueOf(code);
		} catch (IllegalArgumentException e) {
			return EnumNoteEchange.INCONNUE;
		}
	}

}
