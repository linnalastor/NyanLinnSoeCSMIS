package com.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.csmis.entity.Staff;
import com.csmis.service.PassBCryptor;
import com.csmis.service.StaffService;

@Controller
public class LoginController {
	@Autowired
	PassBCryptor passBCryptor;
	@Autowired
	StaffService staffService;
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		return "fancy-login";

	}

	@GetMapping("/welcome")
	public String dashboard(Authentication auth, Model theModel) {
		String role=null;

		if(auth!=null) {
			role=auth.getAuthorities().toArray()[0].toString();
			Staff loginStaff = staffService.findByID(auth.getName());
			theModel.addAttribute("userName",loginStaff.getName());
		}

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
