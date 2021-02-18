package com.microselbourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.microselbourse.entities.Echange;


public interface IEchangeRepository extends JpaRepository<Echange, Long>, JpaSpecificationExecutor<Echange>{

}
