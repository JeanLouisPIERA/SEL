package com.microselwebclientjspui.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebclientjspui.criteria.UserCriteria;
import com.microselwebclientjspui.errors.ConvertToExceptionMessage;
import com.microselwebclientjspui.objets.Article;
import com.microselwebclientjspui.objets.Document;
import com.microselwebclientjspui.objets.Role;
import com.microselwebclientjspui.objets.User;
import com.microselwebclientjspui.service.IArticleService;
import com.microselwebclientjspui.service.IDocumentService;
import com.microselwebclientjspui.service.IRoleService;
import com.microselwebclientjspui.service.IUserService;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IDocumentService documentService;

	@Autowired
	private ConvertToExceptionMessage convertToExceptionMessage;

	@Autowired
	private IArticleService articleService;

	@Value("${application.document7Id}")
	private Long document7Id;

	@Value("${application.document1Id}")
	private Long document1Id;

	@Value("${application.document2Id}")
	private Long document2Id;

	@Value("${application.document3Id}")
	private Long document3Id;

	@Value("${application.document4Id}")
	private Long document4Id;

	@Value("${application.document5Id}")
	private Long document5Id;

	@Value("${application.document6Id}")
	private Long document6Id;

	@GetMapping("/accueil")
	public String accueil(Model model) {

		try {

			Document document7 = documentService.searchByTypeDocumentId(document7Id);
			model.addAttribute("document7", document7);
			Document document1 = documentService.searchByTypeDocumentId(document1Id);
			model.addAttribute("document1", document1);
			Document document2 = documentService.searchByTypeDocumentId(document2Id);
			model.addAttribute("document2", document2);
			Document document3 = documentService.searchByTypeDocumentId(document3Id);
			model.addAttribute("document3", document3);
			Document document4 = documentService.searchByTypeDocumentId(document4Id);
			model.addAttribute("document4", document4);
			Document document5 = documentService.searchByTypeDocumentId(document5Id);
			model.addAttribute("document5", document5);
			Document document6 = documentService.searchByTypeDocumentId(document6Id);
			model.addAttribute("document6", document6);
			
			Integer numberOfArticles=0;

			try {
				List<Article> articles = articleService.select4ArticlesToBePublished();
				model.addAttribute("article1", articles.get(0));
				model.addAttribute("article2", articles.get(1));
				model.addAttribute("article3", articles.get(2));
				model.addAttribute("article4", articles.get(3));
				numberOfArticles = articles.size();
				System.out.println("la liste A" + numberOfArticles);
			} catch (Exception e) {
				System.out.println("la liste B" + numberOfArticles);
				/*
				 * try { List<Document> documents =
				 * documentService.selectDocumentToCompleteArticles(numberOfArticles);
				 * if(numberOfArticles==1) { model.addAttribute("article4", documents.get(0)); }
				 * } catch (Exception e1) { }
				 */
				
			}

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";

		}

		return "accueil";
	}

	@GetMapping("/accueilAdmin")
	public String accueilAdmin() {

		return "accueilAdmin";
	}

	@GetMapping("/accounts/account")
	public String selIndex(Model model) {

		User adherent = userService.consulterCompteAdherent();

		List<Role> roles = roleService.getRolesByUserId();

		model.addAttribute("adherent", adherent);
		model.addAttribute("roles", roles);

		return "users/userView";
	}

	@GetMapping("/accounts/{id}")
	public String searchById(Model model, @PathVariable String id) {

		User adherent = userService.searchById(id);

		List<Role> roles = roleService.getRolesByUserId(id);

		model.addAttribute("adherent", adherent);
		model.addAttribute("roles", roles);

		return "users/userPageView";
	}

	/**
	 * Permet d'afficher une sélection les portefeuilles sous forme de page
	 */
	@GetMapping(value = "/accounts")
	public String searchByCriteria(Model model, @PathParam(value = "userCriteria") UserCriteria userCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("userCriteria", new UserCriteria());

		Page<User> users = userService.searchByCriteria(userCriteria, PageRequest.of(page, size));

		model.addAttribute("users", users.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", users.getNumber());
		model.addAttribute("totalPages", users.getTotalPages());
		model.addAttribute("totalElements", users.getTotalElements());
		model.addAttribute("size", users.getSize());

		List<Role> roles = roleService.getRoles();
		model.addAttribute("roles", roles);

		return "users/usersPage";

	}

}