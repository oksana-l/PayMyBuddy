package com.PayMyBuddy.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
import com.PayMyBuddy.model.dto.TransactionUserDTO;
import com.PayMyBuddy.service.TransactionServiceImpl;
import com.PayMyBuddy.service.UserServiceImpl;

@Controller
public class TransferController {
	
	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/transfer")
	public String getAllPages(Authentication auth, Model model){
	    return showTransfer(auth, model, 1) ;
	} 
	
	@GetMapping("/transfer/{pageNumber}")
	public String showTransfer(Authentication auth, Model model,
			@PathVariable("pageNumber") int currentPage) {
		User user = userService.findUserByEmail(auth.getName());
		Page<Transaction> page = transactionService.findPage(currentPage, user.getId());
	    int totalPages = page.getTotalPages();
	    long totalItems = page.getTotalElements();

        List<Transaction> transactions = page.getContent();
        model.addAttribute("transaction", new TransactionFormDTO());
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("transactions", transactions.stream()
				.map(t -> new TransactionUserDTO(t)).collect(Collectors.toList()));
		return "transfer";
	}

	@PostMapping("/transfer")
	public String getAllPages(TransactionFormDTO form, Authentication auth, Model model){
	    return saveTransaction(form, auth, model, 1) ;
	} 
	
	@PostMapping("/transfer/{pageNumber}")
	public String saveTransaction(@ModelAttribute("transaction") TransactionFormDTO form, 
		Authentication auth, Model model, @PathVariable("pageNumber") int currentPage) {

		User user = userService.findUserByEmail(auth.getName());
		Page<Transaction> page = transactionService.findPage(currentPage, user.getId());
	    int totalPages = page.getTotalPages();
	    long totalItems = page.getTotalElements();
	    
	    List<Transaction> transactions = page.getContent();
		transactionService.save(auth.getName(), form);
		model.addAttribute("transaction", new TransactionFormDTO());
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
	    model.addAttribute("transactions", transactions.stream()
				.map(t -> new TransactionUserDTO(t)).collect(Collectors.toList()));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
	    return "transfer";
	}
}
