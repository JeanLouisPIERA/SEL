package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microselbourse.entities.Categorie;

public interface ICategorieRepository extends JpaRepository<Categorie, Long>{
	
	Optional<Categorie> findById(Long id);

}
