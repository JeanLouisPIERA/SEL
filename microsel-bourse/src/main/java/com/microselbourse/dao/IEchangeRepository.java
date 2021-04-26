package com.microselbourse.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumEchangeAvis;
import com.microselbourse.entities.EnumStatutEchange;

@Repository
public interface IEchangeRepository extends JpaRepository<Echange, Long>, JpaSpecificationExecutor<Echange> {

	@Query("select echange from Echange echange where (echange.dateEcheance BETWEEN ?1 AND ?2)"+"AND (echange.statutEchange = ?3)" + "AND (echange.avisEmetteur = ?4)" + "AND (echange.avisRecepteur = ?5)")
	Optional<List<Echange>> findByDateEcheanceBetween2DatesAndStatutEchangeConfirmeAnd2AvisEchange(LocalDate dateNow1,LocalDate dateNow2,
			EnumStatutEchange confirme, EnumEchangeAvis avisEmetteur, EnumEchangeAvis avisRecepteur);

	@Query("select echange from Echange echange where (echange.id = ?1)"
			+ "AND (echange.emetteurId = ?2) or (echange.recepteurId = ?2)")
	Optional<Echange> findByIdAndAdherentId(Long id, String adherentId);

	Optional<List<Echange>> findByAvisEmetteur(EnumEchangeAvis sans);

	
	

}
