package com.microselwebui.microselwebui.objets;



public class Categorie {
	
	private Long id;
	
	private EnumCategorie name;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnumCategorie getName() {
		return name;
	}

	public void setName(EnumCategorie name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Categorie [id=" + id + ", name=" + name + "]";
	}
	
	
	
	

}
