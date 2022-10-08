package com.PayMyBuddy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
   @GetMapping("/home")
   public String home() {
       return "home";
   }
   
   @RequestMapping("/*")
   public String getUser()
   {
      return "Welcome User";
   }

}
