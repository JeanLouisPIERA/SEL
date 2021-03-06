package com.microselreferentiels.restController;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselreferentiels.dto.TypeDocumentDTO;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.service.ITypeDocumentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/referentiels")
@Validated
public class TypeDocumentRestController {

	@Autowired
	private ITypeDocumentService typeDocumentService;
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@ApiOperation(value = "Enregistrement d'un type de document par un admin", response = TypeDocument.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le type de document a été créée"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 409, message = "Un autre type de document existe déjà avec ces attributs"), })

	@PostMapping("/admin/typedocuments")
	public ResponseEntity<TypeDocument> createTypeDocument(@Valid @RequestBody TypeDocumentDTO typeDocumentDTO)
			throws EntityAlreadyExistsException {
		log.info("Enregistrement d'un type de document par un admin");
		return new ResponseEntity<TypeDocument>(typeDocumentService.createTypeDocument(typeDocumentDTO), HttpStatus.OK);
	}

	@ApiOperation(value = "Recherche multi-critères d'un ou plusieurs types de documents", response = TypeDocument.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/admin/typedocuments/Page", produces = "application/json")
	public ResponseEntity<Page<TypeDocument>> getAllTypeDocumentsByCriteria(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		log.info("Recherche multi-critères d'un ou plusieurs types de documents");
		Page<TypeDocument> typeDocumentsPage = typeDocumentService
				.getAllTypeDocumentsPaginated(PageRequest.of(page, size));
		return new ResponseEntity<Page<TypeDocument>>(typeDocumentsPage, HttpStatus.OK);
	}

	@ApiOperation(value = "Recherche de tous les types de documents", response = TypeDocument.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/admin/typedocuments", produces = "application/json")
	public ResponseEntity<List<TypeDocument>> getAllTypeDocuments() {
		log.info("Recherche de tous les types de documents");
		List<TypeDocument> typeDocuments = typeDocumentService.getAllTypeDocuments();
		return new ResponseEntity<List<TypeDocument>>(typeDocuments, HttpStatus.OK);
	}

}
