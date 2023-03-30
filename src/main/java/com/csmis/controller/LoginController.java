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

	@GetMapping("/welcome")
	public String dashboard(Authentication auth) {
		String role=null;
		if(auth!=null) role=auth.getAuthorities().toArray()[0].toString();

		if(role==null) return "fancy-login";
		else if(role.equals("ROLE_ADMIN")) {
			return "admin/admin_dashboard";
		}
		else {
			return "operator/User_Dashboard";
		}



	}

	// add request mapping for /access-denied

	@GetMapping("/access-denied")
	public String showAccessDenied() {

		return "error404";

	}
}
