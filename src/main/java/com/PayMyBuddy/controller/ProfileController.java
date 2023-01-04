package com.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/profile")
@SessionAttributes("account")
public class ProfileController {

	   @GetMapping
	   public String profile() {
	       return "profile";
	   }
}
