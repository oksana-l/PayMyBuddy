package com.PayMyBuddy.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
import com.PayMyBuddy.model.dto.TransactionUserDTO;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.UserService;

@Controller
public class TransferController {
	
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;
	
	@GetMapping("/transfer")
	public String showTransfer(Authentication auth, Model model,
			@RequestParam(value="pageNumber", required=false, defaultValue="1") int currentPage,
            @RequestParam (value="field", required=false, defaultValue="date")String field,
            @RequestParam(value="sortDir", required=false, defaultValue="desc") String sortDir) {
		
		view(model, auth, currentPage, field, sortDir, new TransactionFormDTO());
		return "transfer";
	}

	@PostMapping("/transfer")
	public String saveTransaction(@Valid @ModelAttribute("transaction") TransactionFormDTO form, 
			@RequestParam(value="pageNumber", required=false, defaultValue="1") int currentPage,
            @RequestParam (value="field", required=false, defaultValue="date") String field,
            @RequestParam(value="sortDir", required=false, defaultValue="desc") String sortDir,
            Authentication auth, Model model, BindingResult result) {
		
		if (! userService.userHasAmount(auth.getName(), form.getAmount())) {
			FieldError error = new FieldError("transaction", "amount", "Le solde n'est pas suffisant");
			result.addError(error);
		}
		if (!result.hasErrors()) {
			transactionService.saveTransaction(auth.getName(), form);
		}	
		
		view(model, auth, currentPage, field, sortDir,
				result.hasErrors() ? form : new TransactionFormDTO());
		
	    return "transfer";
	}
	
	private void view(Model model, Authentication auth, int currentPage, String field,
			String sortDir, TransactionFormDTO form) {
		
		Account account = userService.findAccountByEmail(auth.getName());

	    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
	            Sort.by(field).ascending(): Sort.by(field).descending();
	    Pageable pageable = PageRequest.of(currentPage - 1,5, sort);
		Page<Transaction> page = transactionService.findTransactionWithSorting(pageable, account.getId());
	    int totalPages = page.getTotalPages();
	    long totalItems = page.getTotalElements();
	    
	    List<Transaction> transactions = page.getContent();
	    
		model.addAttribute("transaction", form);
		model.addAttribute("connections", account.getConnections().stream()
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
