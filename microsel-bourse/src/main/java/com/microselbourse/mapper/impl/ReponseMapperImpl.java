package com.microselbourse.mapper.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Reponse;
import com.microselbourse.mapper.IReponseMapper;

@Service
public class ReponseMapperImpl implements IReponseMapper {

	@Value("${application.dateTimezone}")
	private Integer dateTimezone;

	@Override
	public ReponseDTO reponseToReponseDTO(Reponse entity) {
		if (entity == null) {
			return null;
		}

		ReponseDTO reponseDTO = new ReponseDTO();

		reponseDTO.setCodePostal(entity.getCodePostal());
		reponseDTO.setDescription(entity.getDescription());
		reponseDTO.setImage(entity.getImage());
		reponseDTO.setRecepteurId(entity.getRecepteurId());
		reponseDTO.setRecepteurUsername(entity.getRecepteurUsername());
		reponseDTO.setTitre(entity.getTitre());
		reponseDTO.setValeur(entity.getValeur());
		reponseDTO.setVille(entity.getVille());

		return reponseDTO;
	}

	@Override
	public Reponse reponseDTOToReponse(ReponseDTO dto) {
		if (dto == null) {
			return null;
		}

		Reponse reponse = new Reponse();

		reponse.setCodePostal(dto.getCodePostal());
		reponse.setDateEcheance(LocalDate.parse(dto.getDateEcheance()).plusDays(dateTimezone));
		reponse.setDescription(dto.getDescription());
		reponse.setImage(dto.getImage());
		reponse.setRecepteurId(dto.getRecepteurId());
		reponse.setRecepteurUsername(dto.getRecepteurUsername());
		reponse.setTitre(dto.getTitre());
		reponse.setValeur(dto.getValeur());
		reponse.setVille(dto.getVille());

		return reponse;

	}

}
