package com.microselbourse.mapper.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.dto.PropositionUpdateDTO;
import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IPropositionUpdateMapper;



@Service
public class PropositionUpdateMapperImpl implements IPropositionUpdateMapper{
	
	@Value("${application.dateTimezone}")
	private Integer dateTimezone;

	
	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	
	@Override
	public PropositionUpdateDTO propositionTopropositionUpdateDTO(Proposition entity) {
		
		if ( entity == null ) {
            return null;
        }

        PropositionUpdateDTO propositionUpdateDTO = new PropositionUpdateDTO();
        
		/*
		 * propositionUpdateDTO.setEmetteurId(entity.getEmetteurId());
		 * propositionUpdateDTO.setEmetteurUsername(entity.getEmetteurUsername());
		 * propositionUpdateDTO.setEnumTradeTypeCode(
		 * entity.getEnumTradeType().getCode() );
		 */
        propositionUpdateDTO.setCodePostal( entity.getCodePostal() );
        propositionUpdateDTO.setDescription( entity.getDescription() );
        propositionUpdateDTO.setImage( entity.getImage() );
        propositionUpdateDTO.setTitre(entity.getTitre());
        propositionUpdateDTO.setValeur(entity.getValeur());
        propositionUpdateDTO.setVille(entity.getVille());
        //propositionDTO.setDateFin(entity.getDateFin().format(formatter));
        //propositionDTO.setDateEcheance(entity.getDateEcheance().format(formatter));

        return propositionUpdateDTO;
		
	}


	@Override
	public Proposition propositionUpdateDTOToProposition(PropositionUpdateDTO dto) {
		 if ( dto == null ) {
	            return null;
	        }

	        Proposition proposition = new Proposition();
	        
			/*
			 * proposition.setEmetteurId(dto.getEmetteurId());
			 * proposition.setEmetteurUsername(dto.getEmetteurUsername());
			 * proposition.setEnumTradeType(EnumTradeType.getEnumTradeTypeByCode(dto.
			 * getEnumTradeTypeCode()).get());
			 */
	        proposition.setCodePostal( dto.getCodePostal() );
	        proposition.setDescription( dto.getDescription() );
	        proposition.setImage( dto.getImage() );
	        proposition.setTitre(dto.getTitre());
	        proposition.setValeur(dto.getValeur());
	        proposition.setVille(dto.getVille());
	        //proposition.setDateFin(LocalDate.parse(dto.getDateFin(), formatter));
	        //proposition.setDateEcheance(LocalDate.parse(dto.getDateEcheance(), formatter));
	        proposition.setDateFin(LocalDate.parse(dto.getDateFin()).plusDays(dateTimezone));
	        proposition.setDateEcheance(LocalDate.parse(dto.getDateEcheance()).plusDays(dateTimezone));

	        return proposition;
	}
	
	
}