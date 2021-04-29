package com.microselreferentiels.restController;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

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

import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.dto.TypeArticleDTO;
import com.microselreferentiels.dto.TypeDocumentDTO;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.TypeArticle;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.service.ITypeArticleService;
import com.microselreferentiels.service.ITypeDocumentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/referentiels")
@Validated
public class TypeArticleRestController {
	
	@Autowired
	private ITypeArticleService typeArticleService;
	
	@ApiOperation(value = "Enregistrement d'un type d'article par un admin",  response = TypeArticle.class)
	  @ApiResponses(value = {
	  @ApiResponse(code = 201, message = 
			  "Le type d'article a été créée"),
	  @ApiResponse(code = 400, message = 
			  "Les informations fournies ne sont pas correctes"),
	  @ApiResponse(code = 409, message = 
			  "Un autre type d'article existe déjà avec ces attributs"), })

	@PostMapping("/admin/typearticles")
	public ResponseEntity<TypeArticle> createTypeArticle(@Valid @RequestBody TypeArticleDTO typeArticleDTO) throws EntityAlreadyExistsException {
	  return new ResponseEntity<TypeArticle>(typeArticleService.createTypeArticle(typeArticleDTO), HttpStatus.OK);
	}
	
	 @ApiOperation(value = "Recherche multi-critères d'un ou plusieurs types d'articles", response = TypeArticle.class)  
	  @ApiResponses(value = {
	  @ApiResponse(code = 200, message = 
			  "La recherche a été réalisée avec succés"), })
	  
	  @GetMapping(value="/admin/typearticles/Page", produces="application/json") 
	  public ResponseEntity<Page<TypeArticle>> getAllTypeArticlesByCriteria(
			  @RequestParam(name = "page", defaultValue= "0") int page, @RequestParam(name="size", defaultValue= "10") int size) { 
	  	  
	  	  Page<TypeArticle> typeArticlesPage = typeArticleService.getAllTypeArticlesPaginated(PageRequest.of(page, size)); 	
		  return new ResponseEntity<Page<TypeArticle>>(typeArticlesPage, HttpStatus.OK); 
	  }
	 
	 @ApiOperation(value = "Recherche de tous les types d'article", response = TypeDocument.class)  
	  @ApiResponses(value = {
	  @ApiResponse(code = 200, message = 
			  "La recherche a été réalisée avec succés"), })
	  
	  @GetMapping(value="/typearticles", produces="application/json") 
	  public ResponseEntity<List<TypeArticle>> getAllTypeArticles() { 
	  	  List<TypeArticle> typeArticles = typeArticleService.getAllTypeArticles(); 	
		  return new ResponseEntity<List<TypeArticle>>(typeArticles, HttpStatus.OK); 
	  }
 

}
