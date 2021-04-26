package com.microselbourse.mapper;

import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.dto.PropositionUpdateDTO;
import com.microselbourse.entities.Proposition;

public interface IPropositionUpdateMapper {

	PropositionUpdateDTO propositionTopropositionUpdateDTO(Proposition entity);

	Proposition propositionUpdateDTOToProposition(PropositionUpdateDTO dto);

}
