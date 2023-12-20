package com.dat.bit.csmis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/showMyLoginPage")
	public String LoginPage() {
		
		return "login/login";
	}
	
	// add request mapping for /access-denied
	
		@GetMapping("/access-denied")
		public String showAccessDenied() {
			
			return "access-denied";
			
		}

}
