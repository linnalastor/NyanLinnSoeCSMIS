package com.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String dashboard() {
		return "admin/admin_dashboard";

	}

	// add request mapping for /access-denied

	@GetMapping("/access-denied")
	public String showAccessDenied() {

		return "error404";

	}
}
