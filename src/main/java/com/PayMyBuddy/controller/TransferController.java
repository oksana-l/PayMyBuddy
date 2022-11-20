package com.PayMyBuddy.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showTransfer(Authentication auth, Model model,
			@RequestParam(value="pageNumber", required=false, defaultValue="1") int currentPage,
            @RequestParam (value="field", required=false, defaultValue="date")String field,
            @RequestParam(value="sortDir", required=false, defaultValue="desc") String sortDir) {
		
		view(model, auth, currentPage, field, sortDir);
		return "transfer";
	}

	@PostMapping("/transfer")
	public String saveTransaction(@Valid @ModelAttribute("transaction") TransactionFormDTO form, 
			@RequestParam(value="pageNumber", required=false, defaultValue="1") int currentPage,
            @RequestParam (value="field", required=false, defaultValue="date")String field,
            @RequestParam(value="sortDir", required=false, defaultValue="desc") String sortDir,
            Authentication auth, Model model, BindingResult result) {
		
		if (! userService.userHasAmount(auth.getName(), form.getAmount())) {
			ObjectError error = new ObjectError("amount", "Le solde n'est pas suffisant");
			result.addError(error);
		}
		if (!result.hasErrors()) {
			transactionService.save(auth.getName(), form);
		}		
	    if (result.hasErrors()) {
	    	model.addAttribute("transaction", form);
	    }
		view(model, auth, currentPage, field, sortDir);
	    return "transfer";
	}
	
	public void view(Model model, Authentication auth, int currentPage, String field,
			String sortDir) {
		
		User user = userService.findUserByEmail(auth.getName());
		Page<Transaction> page = transactionService.findTransactionWithSorting(field, sortDir, currentPage, user.getId());
	    int totalPages = page.getTotalPages();
	    long totalItems = page.getTotalElements();
	    
	    List<Transaction> transactions = page.getContent();
	    
		model.addAttribute("transaction", new TransactionFormDTO());
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
		model.addAttribute("transactions", transactions.stream()
				.map(t -> new TransactionUserDTO(t)).collect(Collectors.toList()));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("field", field);
	    model.addAttribute("sortDir", sortDir);	
	}
}
