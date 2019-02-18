package com.firmaBudowlana.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/stronaLogowania")
	public String showMyLoginPage(Model theModel) {
		return "login-page";
	}
	
	@GetMapping("/brakDostepu")
	public String accessDenied() {
		return "access-denied";
	}
}
