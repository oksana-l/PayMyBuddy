package com.PayMyBuddy.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.PayMyBuddy.service.UserServiceImpl;
import com.PayMyBuddy.web.dto.UserDTO;
import com.PayMyBuddy.web.dto.UserExistsException;

@Controller
@RequestMapping("/registration")
@SessionAttributes("user")
public class UserController {

	private UserServiceImpl userServiceImpl;

	public UserController(UserServiceImpl userServiceImpl) {
		super();
		this.userServiceImpl = userServiceImpl;
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
	public String registerUserAccount(@ModelAttribute("user") UserDTO userDto) throws UserExistsException {
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(userDto.getPassword());
			userDto.setPassword(encodedPassword);
			userServiceImpl.save(userDto);
		} catch (UserExistsException e) {
		    if (userDto.getUserName() == null || userDto.getEmail() == null) {
		        throw new UserExistsException(
		          "There is an user with that name or that email adress: ", userDto.getUserName(),
		          userDto.getEmail());
		    }
			e.printStackTrace();
		}
		return "redirect:/login";
	}
	
}
