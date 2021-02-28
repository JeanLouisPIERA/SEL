package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.EchangeCriteria;
import com.microselwebclientjspui.objets.Echange;

public interface IEchangeService {

	Page<Echange> searchByCriteria(EchangeCriteria echangeCriteria, Pageable pageable);

	Echange searchById(Long id);

	Echange confirmerEchange(Long id);

	Echange annulerEchange(Long id);

	Echange emetteurValiderEchange(Long id);

	Echange recepteurRefuserEchange(Long id);

	Echange emetteurRefuserEchange(Long id);

	Echange recepteurValiderEchange(Long id);

	

}
