package com.microselbourse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Reponse;

/**
 * * Classe permettant d'implémenter l'interface JPA pour les relations ORM de la classe Reponse
 * @author jeanl
 *
 */
@Repository
public interface IReponseRepository extends JpaRepository<Reponse, Long>, JpaSpecificationExecutor<Reponse> {

}
