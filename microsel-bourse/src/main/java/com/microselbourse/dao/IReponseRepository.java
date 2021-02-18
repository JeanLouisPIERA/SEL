package com.microselbourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.Reponse;

public interface IReponseRepository extends JpaRepository<Reponse, Long>, JpaSpecificationExecutor<Reponse>{
	


}
