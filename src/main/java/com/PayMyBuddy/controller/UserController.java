package com.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.PayMyBuddy.service.UserService;
import com.PayMyBuddy.web.dto.UserDTO;

@Controller
@RequestMapping("/registration")
@SessionAttributes("user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
    public UserDTO userDto() {
        return new UserDTO();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserDTO userDto) {
		userService.save(userDto);
		return "redirect:/registration?success";
	}
	
}
