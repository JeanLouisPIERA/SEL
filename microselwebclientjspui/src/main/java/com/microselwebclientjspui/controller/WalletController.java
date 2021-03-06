package com.microselwebclientjspui.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.criteria.WalletCriteria;
import com.microselwebclientjspui.errors.WalletExceptionMessage;
import com.microselwebclientjspui.objets.Echange;
import com.microselwebclientjspui.objets.EnumCategorie;
import com.microselwebclientjspui.objets.EnumStatutProposition;
import com.microselwebclientjspui.objets.EnumTradeType;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Transaction;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.ITransactionService;
import com.microselwebclientjspui.service.IWalletService;

@Controller
public class WalletController {
	
	@Autowired
	private IWalletService walletService;
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private WalletExceptionMessage walletExceptionMessage;
	
	/**
     * Permet d'afficher une sélection les portefeuilles sous forme de page
     */
    @GetMapping(value="/wallets")
    public String searchByCriteria(Model model, @PathParam(value = "walletCriteria") WalletCriteria walletCriteria, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="6") int size){
    	
    	model.addAttribute("walletCriteria", new WalletCriteria());
    	
    	Page<Wallet> wallets = walletService.searchByCriteria(walletCriteria, PageRequest.of(page, size));
    	
    	model.addAttribute("wallets", wallets.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", wallets.getNumber());
        model.addAttribute("totalPages", wallets.getTotalPages());
        model.addAttribute("totalElements", wallets.getTotalElements());
        model.addAttribute("size", wallets.getSize());
        
        return "wallets/walletsPage";
        
    }
	

    /**
     * Permet de lire les transactions d'un portefeuille
     */
    @GetMapping("/wallets/transactions/{id}")
    public String readWallet(Model model, @PathVariable("id") Long id, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="6") int size) {
    	
    	try {
			Wallet readWallet = walletService.searchById(id);
			model.addAttribute("readWallet", readWallet);
			//model.addAttribute("transactions", readWallet.getTransactions());
		
	    	
	    	Page<Transaction> transactions = transactionService.findAllByWalletId(id, PageRequest.of(page, size));
	    	
	    	model.addAttribute("transactions", transactions.getContent());
	    	model.addAttribute("page", Integer.valueOf(page));
			model.addAttribute("number", transactions.getNumber());
	        model.addAttribute("totalPages", transactions.getTotalPages());
	        model.addAttribute("totalElements", transactions.getTotalElements());
	        model.addAttribute("size", transactions.getSize());
        
    	} catch (HttpClientErrorException e) {
			String errorMessage = walletExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			model.addAttribute("error", errorMessage);
			return"/error";
		}
        
    	return "wallets/walletView";
    }

}
