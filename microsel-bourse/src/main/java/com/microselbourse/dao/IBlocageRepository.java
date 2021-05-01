package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.EnumStatutBlocage;

/**
 * Classe permettant d'implémenter l'interface JPA pour les relations ORM de la classe Blocage
 * @author jeanl
 *
 */
@Repository
public interface IBlocageRepository extends JpaRepository<Blocage, Long>, JpaSpecificationExecutor<Blocage>{

	@Query("select blocage from Blocage blocage where (blocage.adherentId =?1)")
	Optional<Blocage> findByAdherentId(String adherentId);

	@Query("select blocage from Blocage blocage where (blocage.adherentId =?1)" + "AND (blocage.statutBlocage = ?2)")
	Optional<Blocage> findByAdherentIdAndStatutBlocage(String adherentId, EnumStatutBlocage statutBlocage);

}
