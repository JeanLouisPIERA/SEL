package com.microselreferentiels.restController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselreferentiels.criteria.ArticleCriteria;
import com.microselreferentiels.dto.ArticleDTO;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.exceptions.DeniedAccessException;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.service.IArticleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/referentiels")
@Validated
public class ArticleRestController {
	
	@Autowired
	private IArticleService articleService;
	
	@ApiOperation(value = "Enregistrement d'un article",  response = Article.class)
	  @ApiResponses(value = {
	  @ApiResponse(code = 201, message = 
			  "L'article a été créée"),
	  @ApiResponse(code = 400, message = 
			  "Les informations fournies ne sont pas correctes"),
	  @ApiResponse(code = 409, message = 
			  "Un autre article existe déjà avec ces attributs"), })

	@PostMapping("/articles")
	public ResponseEntity<Article> createArticle(@Valid @RequestBody ArticleDTO articleDTO) throws EntityAlreadyExistsException, EntityNotFoundException {
	  return new ResponseEntity<Article>(articleService.createArticle(articleDTO), HttpStatus.OK);
	}
	
	 @ApiOperation(value = "Recherche multi-critères d'un ou plusieurs articles", response = Article.class)  
	  @ApiResponses(value = {
	  @ApiResponse(code = 200, message = 
			  "La recherche a été réalisée avec succés"), })
	  
	  @GetMapping(value="/articles", produces="application/json") 
	  public ResponseEntity<Page<Article>> searchAllArticlesByCriteria(
			  @PathParam("articleCriteria") ArticleCriteria articleCriteria,
			  @RequestParam(name = "page", defaultValue= "0") int page, @RequestParam(name="size", defaultValue= "10") int size) { 
	  	  
	  	  Page<Article> articles = articleService.searchAllArticlesByCriteria(articleCriteria, PageRequest.of(page, size)); 	
		  return new ResponseEntity<Page<Article>>(articles, HttpStatus.OK); 
	  }
 
	  @ApiOperation(value = "Consultation du contenu d'un article", response = Article.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "L'article recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'article que vous voulez consulter n'existe pas"), })
	 
	  @GetMapping("/articles/{id}")
	  public ResponseEntity<Article> readArticle(@PathVariable("id") @Valid Long id) throws EntityNotFoundException {
		return new ResponseEntity<Article>(articleService.readArticle(id), HttpStatus.OK);  
	  }
	  
	  @ApiOperation(value = "Publication d'un article", response = Article.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "L'article recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'article que vous voulez publier n'existe pas"), })
	 
	  @PutMapping("/admin/articles/publication/{id}")
	  public ResponseEntity<Article> publierArticle(@PathVariable("id") @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		return new ResponseEntity<Article>(articleService.publierArticle(id), HttpStatus.OK);  
	  }
	  
	  
	  @ApiOperation(value = "Modération d'un article", response = Article.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "L'article recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'article que vous voulez consulter n'existe pas"), })
	 
	  @PutMapping("/admin/articles/moderation/{id}")
	  public ResponseEntity<Article> modererArticle(@PathVariable("id") @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		return new ResponseEntity<Article>(articleService.modererArticle(id), HttpStatus.OK);  
	  }
	  
	  @ApiOperation(value = "Archivage d'un article", response = Article.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "L'article recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'article que vous voulez consulter n'existe pas"), })
	 
	  @PutMapping("/admin/articles/archivage/{id}")
	  public ResponseEntity<Article> archiverArticle(@PathVariable("id") @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		return new ResponseEntity<Article>(articleService.archiverArticle(id), HttpStatus.OK);  
	  }
	  
	  @ApiOperation(value = "Consultation du contenu d'un article", response = Article.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "L'article recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'article que vous voulez consulter n'existe pas"), })
	 
	  @GetMapping("/articles/typeArticle/{id}")
	  public ResponseEntity<Article> readArticleByTypeArticle(@PathVariable("id") @Valid Long typearticleId) throws EntityNotFoundException {
		return new ResponseEntity<Article>(articleService.readArticleByTypeArticle(typearticleId), HttpStatus.OK);  
	  }
	  
	 
	 
}
