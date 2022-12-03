package com.PayMyBuddy.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.service.ConnectionService;
import com.PayMyBuddy.service.UserService;

@Controller
@RequestMapping("/myConnections")
public class ConnectionController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ConnectionService connectionService;
	
	@GetMapping
	public String showFormAddConnection(Authentication auth, Model model,
			@RequestParam (value="field", required=false, defaultValue="date") String field) {

		view(auth, field, model, new AddConnectionDTO());		
		return "myConnections";
	}
	
	@PostMapping
	public String addConnection(@ModelAttribute("connection") AddConnectionDTO addConnectionDto,
			@RequestParam (value="field", required=false, defaultValue="date") String field,
			Authentication auth, Model model, BindingResult result) {
		
		if (!connectionService.isUserExist(addConnectionDto.getEmail())) {
			FieldError error = new FieldError("connection", "email", "Cet utilisateur n'existe pas");
			result.addError(error);
		}
		if (!result.hasErrors()) {
			connectionService.save(auth, addConnectionDto);
		}

		view(auth, field, model, result.hasErrors() ? addConnectionDto : new AddConnectionDTO());
		
		return "myConnections";
	}
	
	private void view(Authentication auth, String field, Model model, 
			AddConnectionDTO addConnectionDTO) {
		User user = userService.findUserByEmail(auth.getName());
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
		model.addAttribute("connection", addConnectionDTO);
		model.addAttribute("field", field);
	}
}
