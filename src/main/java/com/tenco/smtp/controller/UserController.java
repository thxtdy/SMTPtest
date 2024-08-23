package com.tenco.smtp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/user")
@Controller
public class UserController {
	
	@GetMapping("sign-up")
	public String signUpPage( ) {
		return "/user/signUp";
	}
	
}
