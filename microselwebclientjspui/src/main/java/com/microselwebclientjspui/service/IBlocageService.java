package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.microselwebclientjspui.criteria.BlocageCriteria;
import com.microselwebclientjspui.objets.Blocage;

public interface IBlocageService {

	Page<Blocage> searchByCriteria(BlocageCriteria blocageCriteria, Pageable pageable);

	Blocage closeBlocage(Long id);

}
