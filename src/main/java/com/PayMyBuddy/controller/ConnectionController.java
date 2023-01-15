package com.PayMyBuddy.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.service.AccountService;
import com.PayMyBuddy.service.ConnectionService;

@Controller
@RequestMapping("/myConnections")
public class ConnectionController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ConnectionService connectionService;
	
	@GetMapping
	public String showFormAddConnection(Authentication auth, Model model,
			@RequestParam(value="pageNumber", required=false, defaultValue="1") int currentPage,
			@RequestParam (value="field", required=false, defaultValue="user_name") String field,
            @RequestParam(value="sortDir", required=false, defaultValue="asc") String sortDir) {

		view(auth, field, model, currentPage, sortDir, new AddConnectionDTO());		
		return "myConnections";
	}
	
	@PostMapping
	public String addConnection(@ModelAttribute("connection") AddConnectionDTO addConnectionDto,
			@RequestParam(value="pageNumber", required=false, defaultValue="1") int currentPage,
			@RequestParam (value="field", required=false, defaultValue="user_name") String field,
            @RequestParam(value="sortDir", required=false, defaultValue="asc") String sortDir,
			Authentication auth, Model model, BindingResult result) {
		
		if (!connectionService.isAccountExists(addConnectionDto.getEmail())) {
			FieldError error = new FieldError("connection", "email", "Cet utilisateur n'existe pas");
			result.addError(error);
		}
		else if (connectionService.isConnectedAccountExists(addConnectionDto.getEmail(), auth.getName())) {
			FieldError error = new FieldError("connection", "email", "Cet utilisateur est déjà enregistré");
			result.addError(error);
		}
		else if (addConnectionDto.getEmail().equals(auth.getName())) {
			FieldError error = new FieldError("connection", "email", "Vous ne pouvez pas ajouter cet utilisateur");
			result.addError(error);
		}
		if (!result.hasErrors()) {
			connectionService.save(auth, addConnectionDto);
			return "redirect:/myConnections";
		}
		else { 
			view(auth, field, model, currentPage, sortDir,
				 result.hasErrors() ? addConnectionDto : new AddConnectionDTO());
			return "myConnections";
		}
	}
	
	private void view(Authentication auth, String field, Model model, int currentPage,
			String sortDir, AddConnectionDTO addConnectionDTO) {
		
		Account account = accountService.findAccountByEmail(auth.getName());
		
	    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
	            Sort.by(field).ascending(): Sort.by(field).descending();
	    Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
		Page<Account> page = connectionService.findConnections(pageable, account.getId());
	    int totalPages = page.getTotalPages();
	    long totalItems = page.getTotalElements();
	    
	    List<ConnectionDTO> connections = connectionService.getConnectionsDTO(page);
	    
		model.addAttribute("connections", connections);
		model.addAttribute("connection", addConnectionDTO);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("field", field);
	    model.addAttribute("sortDir", sortDir);
	}
}
