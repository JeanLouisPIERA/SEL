package com.microselreferentiels.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microselreferentiels.dao.ITypeArticleRepository;
import com.microselreferentiels.dto.TypeArticleDTO;
import com.microselreferentiels.entities.TypeArticle;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.service.ITypeArticleService;

@Service
@Transactional
public class TypeArticleServiceImpl implements ITypeArticleService {

	@Autowired
	private ITypeArticleRepository typeArticleRepository;

	@Override
	public TypeArticle createTypeArticle(@Valid TypeArticleDTO typeArticleDTO) throws EntityAlreadyExistsException {

		Optional<TypeArticle> typeArticleToCreate = typeArticleRepository.findByTypeName(typeArticleDTO.getTypeName());
		if (typeArticleToCreate.isPresent())
			throw new EntityAlreadyExistsException("Ce type d'article a déjà été enregistré avec le même nom");

		TypeArticle typeArticleCreated = new TypeArticle();

		typeArticleCreated.setTypeName(typeArticleDTO.getTypeName());
		typeArticleCreated.setDescription(typeArticleDTO.getDescription());
		typeArticleCreated.setDateCreation(LocalDate.now());

		return typeArticleRepository.save(typeArticleCreated);
	}

	@Override
	public Page<TypeArticle> getAllTypeArticlesPaginated(Pageable pageable) {

		Page<TypeArticle> typeArticlesPage = typeArticleRepository.findAll(pageable);

		return typeArticlesPage;
	}

	@Override
	public List<TypeArticle> getAllTypeArticles() {
		List<TypeArticle> typeArticles = typeArticleRepository.findAll();
		return typeArticles;
	}

}
