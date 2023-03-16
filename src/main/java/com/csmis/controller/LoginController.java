package com.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.csmis.service.PassBCryptor;

@Controller
public class LoginController {
	@Autowired
	PassBCryptor passBCryptor;
	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		return "fancy-login";

	}
	
	@GetMapping("/dashboard")
	public String dashboard(Authentication auth) {
		System.out.println(auth.getAuthorities());
		return "operator/User_Dashboard";

	}

	// add request mapping for /access-denied

	@GetMapping("/access-denied")
	public String showAccessDenied() {

		return "error404";

	}
}
