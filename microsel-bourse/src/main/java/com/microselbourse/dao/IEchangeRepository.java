package com.microselbourse.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumEchangeAvis;
import com.microselbourse.entities.EnumStatutEchange;


public interface IEchangeRepository extends JpaRepository<Echange, Long>, JpaSpecificationExecutor<Echange>{

	@Query("select echange from Echange echange where (echange.dateEcheance < ?1)" 
	+ "AND (echange.statutEchange = ?2)"
	+ "AND (echange.avisEmetteur = ?3" 
	+ "AND (echange.avisRecepteur = ?4))")
	Optional<List<Echange>> findByDateEcheanceBeforeThisDateAndStatutEchangeConfirmeAnd2AvisEchange(
			LocalDate now,
			EnumStatutEchange confirme, 
			EnumEchangeAvis avisEmetteur,
			EnumEchangeAvis avisRecepteur);
	
	@Query("select echange from Echange echange where (echange.id = ?1)" 
			+ "AND (echange.emetteurId = ?2) or (echange.recepteurId = ?2)" )
	Optional<Echange> findByIdAndAdherentId(Long id, Long adherentId);
	

}
