package com.microselbourse.mapper;

import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Proposition;

public interface IPropositionMapper {

	PropositionDTO propositionTopropositionDTO(Proposition entity);

	Proposition propositionDTOToProposition(PropositionDTO dto);

}
