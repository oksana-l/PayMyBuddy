package com.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/login")
@SessionAttributes("account")
public class LoginController {
	
	@GetMapping
	public String login() {
		return "login";
	}
}
