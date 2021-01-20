/*
* L'énumération RoleEnum permet d'identifier les différents profils d'autorisation donnés à l'entité Role
*/

package com.microseladherent.entities;

public enum RoleEnum {
	
	ADHERENT ("ADHERENT", "ADHERENT"),
	BUREAU ("BUREAU", "BUREAU"),
	ADMIN ("ADMIN", "ADMIN");
	
	 private String code;
	  private String text;
	  
	private RoleEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static RoleEnum getRoleEnumByCode(String code) {
		for (RoleEnum roleEnum : RoleEnum.values()) {
			if(roleEnum.code.equals(code)){
				return roleEnum;
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
		if(this==ADHERENT) {
			return"Adhérent";
		}else if (this==BUREAU) {
			return"Membre du Bureau";
		}else if (this==ADMIN) {
			return"Administrateur";
		}
		return super.toString();
	}   
}
