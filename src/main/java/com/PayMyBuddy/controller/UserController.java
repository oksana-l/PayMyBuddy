package com.PayMyBuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.model.exception.AccountExistsException;
import com.PayMyBuddy.service.UserService;

@Controller
@RequestMapping("/registration")
@SessionAttributes("user")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
    public UserDTO userDto() {
        return new UserDTO();
    }
	
	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserDTO userDto,
			BindingResult result, Model model) throws AccountExistsException  {
		
		if (userService.ifUserExist(userDto)) {
			FieldError error = new FieldError("user", "email", "L'utilisateur existe déjà");
			result.addError(error);
		}
		if (!result.hasErrors()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(userDto.getPassword());
			userDto.setPassword(encodedPassword);
			userService.save(userDto);
			return "redirect:/login";
		}
		model.addAttribute(userDto);
		return "registration";
	}
	
}
