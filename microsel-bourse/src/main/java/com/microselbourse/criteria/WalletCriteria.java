package com.microselbourse.criteria;

import java.util.List;

import com.microselbourse.entities.Transaction;

public class WalletCriteria {
	
	private Long id;
	
	private Long titulaireId;
	
	private Integer soldeWallet;

	public WalletCriteria() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTitulaireId() {
		return titulaireId;
	}

	public void setTitulaireId(Long titulaireId) {
		this.titulaireId = titulaireId;
	}

	public Integer getSoldeWallet() {
		return soldeWallet;
	}

	public void setSoldeWallet(Integer soldeWallet) {
		this.soldeWallet = soldeWallet;
	}

	

}
