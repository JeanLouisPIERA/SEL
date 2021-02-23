package com.microselwebclient_ui.objets;


public enum UserStatutEnum {
	
	ACTIVE ("ACTIVE", "ACTIVE"),
	LOCKED ("LOCKED", "LOCKED"),
	CLOSED("CLOSED", "CLOSED");
	
	
	 private String code;
	  private String text;
	  
	private UserStatutEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static UserStatutEnum getUserStatutEnumByCode(String code) {
		for (UserStatutEnum userStatutEnum : UserStatutEnum.values()) {
			if(userStatutEnum.code.equals(code)){
				return userStatutEnum;
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
		if(this==ACTIVE) {
			return"Compte Adhérentactif";
		}
		else if (this==LOCKED) {
			return"Compte Adhérent Bloqué";	
		}
		else if (this==CLOSED) {
			return"Compte Adhérent clôturé";	
		}
		return super.toString();
	}   
}