package com.microselbourse.mapper.impl;

import org.springframework.stereotype.Service;

import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.mapper.IPropositionMapper;



@Service
public class PropositionMapperImpl implements IPropositionMapper{

	
	@Override
	public PropositionDTO propositionTopropositionDTO(Proposition entity) {
		
		if ( entity == null ) {
            return null;
        }

        PropositionDTO propositionDTO = new PropositionDTO();
        
        propositionDTO.setEmetteurId(entity.getEmetteurId());
        propositionDTO.setCodePostal( entity.getCodePostal() );
        propositionDTO.setDescription( entity.getDescription() );
        propositionDTO.setEnumTradeTypeCode( entity.getEnumTradeType().getCode() );
        propositionDTO.setImage( entity.getImage() );
        propositionDTO.setTitre(entity.getTitre());
        propositionDTO.setValeur(entity.getValeur());
        propositionDTO.setVille(entity.getVille());
        propositionDTO.setDateFin(entity.getDateFin());
        propositionDTO.setDateEcheance(entity.getDateEcheance());
        
        return propositionDTO;
		
	}


	@Override
	public Proposition propositionDTOToProposition(PropositionDTO dto) {
		 if ( dto == null ) {
	            return null;
	        }

	        Proposition proposition = new Proposition();
	        
	        proposition.setEmetteurId(dto.getEmetteurId());
	        proposition.setCodePostal( dto.getCodePostal() );
	        proposition.setDescription( dto.getDescription() );
	        proposition.setEnumTradeType(EnumTradeType.getEnumTradeTypeByCode(dto.getEnumTradeTypeCode()).get());
	        proposition.setImage( dto.getImage() );
	        proposition.setTitre(dto.getTitre());
	        proposition.setValeur(dto.getValeur());
	        proposition.setVille(dto.getVille());
	        proposition.setDateFin(dto.getDateFin());
	        proposition.setDateEcheance(dto.getDateEcheance());

	        return proposition;
	}

}