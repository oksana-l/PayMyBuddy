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
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.service.ConnectionServiceImpl;
import com.PayMyBuddy.service.UserServiceImpl;

@Controller
@RequestMapping("/myConnections")
public class ConnectionController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private ConnectionServiceImpl connectionService;
	
	@GetMapping
	public String showFormAddConnection(Authentication auth, Model model) {
		User user = userService.findUserByEmail(auth.getName());
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
		model.addAttribute("connection", new AddConnectionDTO());
		return "myConnections";
	}
	
	@PostMapping
	public String addConnection(@ModelAttribute("connection") AddConnectionDTO addConnectionDto,
			Authentication auth, Model model) {
		User user = connectionService.save(auth, addConnectionDto);
		model.addAttribute("connections", user.getConnections().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList()));
		model.addAttribute("connection", new AddConnectionDTO());
		return "myConnections";
	}
}
