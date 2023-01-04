package com.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/contact")
@SessionAttributes("account")
public class ContactController {

	   @GetMapping
	   public String contact() {
	       return "contact";
	   }
}
