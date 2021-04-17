package com.microselbourse.mapper;

import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Reponse;

public interface IReponseMapper {

	ReponseDTO reponseToReponseDTO(Reponse entity);

	Reponse reponseDTOToReponse(ReponseDTO dto);

}
