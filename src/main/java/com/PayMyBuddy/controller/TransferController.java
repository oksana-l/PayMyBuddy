package com.PayMyBuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.service.TransactionServiceImpl;
import com.PayMyBuddy.service.UserServiceImpl;

@Controller
@RequestMapping("/transfer")
//@SessionAttributes("user")
public class TransferController {
	
	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	private UserServiceImpl userService;
	
//	@ModelAttribute("transaction")
//	public TransactionDTO transactionDto() {
//		return new TransactionDTO();
//	}
	
	@GetMapping
	public String showTransfer(Authentication auth, Model model) {
		User user = userService.findUserByEmail(auth.getName());
		model.addAttribute("transactions", user.getDebits());
		return "transfer";
	}

//	@PostMapping
//	public String saveTransaction(@ModelAttribute TransactionDTO form, Model model) {
//	    transferService.saveAll(form.getSenderId());
//	    model.addAttribute("transactions", transferService.findAll(form.getSenderId()));
//	    return "redirect:/books/all";
//	}
}
