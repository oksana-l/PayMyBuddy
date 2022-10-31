package com.PayMyBuddy.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.TransactionDTO;
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
		TransactionDTO transaction = new TransactionDTO();
		transaction.setSender(user);
		model.addAttribute("transaction", new TransactionDTO());
		model.addAttribute("connections", user.getConnections());
		model.addAttribute("transactions", 
			transactionService.findTransactionsBySenderId(user.getId()));
		return "transfer";
	}

	@PostMapping
	@Transactional
	public String saveTransaction(@ModelAttribute("transaction") TransactionDTO form, 
			Authentication auth, Model model) {
		User user = userService.findUserByEmail(auth.getName());
		form.setSender(user);
	    transactionService.save(auth, form);
		model.addAttribute("transaction", new TransactionDTO());
		model.addAttribute("connections", user.getConnections());
	    model.addAttribute("transactions", 
	    		transactionService.findTransactionsBySenderId(user.getId()));
	    return "transfer";
	}
}
