package com.microselbourse.mapper.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.mapper.IPropositionMapper;



@Service
public class PropositionMapperImpl implements IPropositionMapper{

	
	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	
	@Override
	public PropositionDTO propositionTopropositionDTO(Proposition entity) {
		
		if ( entity == null ) {
            return null;
        }

        PropositionDTO propositionDTO = new PropositionDTO();
        
        propositionDTO.setEmetteurId(entity.getEmetteurId());
        propositionDTO.setEmetteurUsername(entity.getEmetteurUsername());
        propositionDTO.setCodePostal( entity.getCodePostal() );
        propositionDTO.setDescription( entity.getDescription() );
        propositionDTO.setEnumTradeTypeCode( entity.getEnumTradeType().getCode() );
        propositionDTO.setImage( entity.getImage() );
        propositionDTO.setTitre(entity.getTitre());
        propositionDTO.setValeur(entity.getValeur());
        propositionDTO.setVille(entity.getVille());
        //propositionDTO.setDateFin(entity.getDateFin().format(formatter));
        //propositionDTO.setDateEcheance(entity.getDateEcheance().format(formatter));

        return propositionDTO;
		
	}


	@Override
	public Proposition propositionDTOToProposition(PropositionDTO dto) {
		 if ( dto == null ) {
	            return null;
	        }

	        Proposition proposition = new Proposition();
	        
	        proposition.setEmetteurId(dto.getEmetteurId());
	        proposition.setEmetteurUsername(dto.getEmetteurUsername());
	        proposition.setCodePostal( dto.getCodePostal() );
	        proposition.setDescription( dto.getDescription() );
	        proposition.setEnumTradeType(EnumTradeType.getEnumTradeTypeByCode(dto.getEnumTradeTypeCode()).get());
	        proposition.setImage( dto.getImage() );
	        proposition.setTitre(dto.getTitre());
	        proposition.setValeur(dto.getValeur());
	        proposition.setVille(dto.getVille());
	        //proposition.setDateFin(LocalDate.parse(dto.getDateFin(), formatter));
	        //proposition.setDateEcheance(LocalDate.parse(dto.getDateEcheance(), formatter));
	        proposition.setDateFin(LocalDate.parse(dto.getDateFin()));
	        proposition.setDateEcheance(LocalDate.parse(dto.getDateEcheance()));

	        return proposition;
	}
	
	
}