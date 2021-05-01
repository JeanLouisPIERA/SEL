package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumCategorie;

/**
 * * Classe permettant d'implémenter l'interface JPA pour les relations ORM de la classe Catégorie
 * @author jeanl
 *
 */
@Repository
public interface ICategorieRepository extends JpaRepository<Categorie, Long> {

	Optional<Categorie> findById(Long id);

	Optional<Categorie> findByName(EnumCategorie name);

}
