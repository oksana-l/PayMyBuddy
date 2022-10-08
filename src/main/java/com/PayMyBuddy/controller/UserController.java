package com.PayMyBuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.repository.UserRepository;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="/registration.html")
	public String login(@RequestParam(name="email", required=false)
							String email, String password, Model model) {
		model.addAttribute("email", model.getAttribute("email"));
		model.addAttribute("password", model.getAttribute("password"));
		User user = new User(email, password, 0);
		userRepository.save(user);
		return "registration+j";
	}
}
