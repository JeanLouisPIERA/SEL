package com.microselbourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.Reponse;

@Repository
public interface IReponseRepository extends JpaRepository<Reponse, Long>, JpaSpecificationExecutor<Reponse>{
	


}
