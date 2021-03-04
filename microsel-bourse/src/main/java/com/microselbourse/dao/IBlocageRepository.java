package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.EnumStatutBlocage;

@Repository
public interface IBlocageRepository extends JpaRepository<Blocage, Long>{
	
	@Query("select blocage from Blocage blocage where (blocage.adherentId =?1)") 
	Optional<Blocage> findByAdherentId (Long adherentId);
	
	@Query("select blocage from Blocage blocage where (blocage.adherentId =?1)"+ "AND (blocage.statutBlocage = ?2)") 
	Optional<Blocage> findByAdherentIdAndStatutBlocage(Long adherentId, EnumStatutBlocage statutBlocage);

}
