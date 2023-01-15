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

import com.PayMyBuddy.model.dto.AccountDTO;
import com.PayMyBuddy.service.AccountService;

@Controller
@RequestMapping("/registration")
@SessionAttributes("account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@ModelAttribute("account")
    public AccountDTO accountDto() {
        return new AccountDTO();
    }
	
	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("account") AccountDTO accountDto,
			BindingResult result, Model model) throws Exception  {
		
		if (accountService.ifUserExist(accountDto)) {
			FieldError error = new FieldError("account", "email", "L'utilisateur existe déjà");
			result.addError(error);
		}
		if (!result.hasErrors()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
			accountDto.setPassword(encodedPassword);
			accountService.save(accountDto);
			return "redirect:/login";
		}
		model.addAttribute(accountDto);
		return "registration";
	}
	
}
