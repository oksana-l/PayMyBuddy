package com.PayMyBuddy.controller;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
import com.PayMyBuddy.service.TransactionServiceImpl;
import com.PayMyBuddy.service.UserServiceImpl;

@Controller
@RequestMapping("/transfer")
public class TransferController {
	
	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping
	public String showTransfer(Authentication auth, Model model) {
		User user = userService.findUserByEmail(auth.getName());
		model.addAttribute("transaction", new TransactionFormDTO());
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
		model.addAttribute("transactions", // a faire la meme chose que plus haut
			transactionService.findTransactionsByUser(user.getId()));
		return "transfer";
	}

	@PostMapping
	public String saveTransaction(@ModelAttribute("transaction") TransactionFormDTO form, 
			Authentication auth, Model model) {
		User user = userService.findUserByEmail(auth.getName());
		transactionService.save(auth.getName(), form);
		model.addAttribute("transaction", new TransactionFormDTO());
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
	    model.addAttribute("transactions", 
	    		transactionService.findTransactionsByUser(user.getId()));
	    return "transfer";
	}
}
