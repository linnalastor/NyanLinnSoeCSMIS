package com.csmis.masterdatasetup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.entity.Authorities;
import com.csmis.service.AuthoritiesService;

@Controller
@RequestMapping("/admin")
public class Import_Admin_Controller {

	AuthoritiesService authoritiesService;

	@Autowired
	public Import_Admin_Controller(AuthoritiesService authoritiesService) {
		this.authoritiesService = authoritiesService;

	}

	@GetMapping("/Show_AdminADD")
	public String showFormForAdd(Model theModel) {
		Authorities authorities = new Authorities();

		theModel.addAttribute("authorities", authorities);

		// send over to our form
		return "/admin/Add_AdminRole";
	}

	@PostMapping("/saveAdmin")
	public String saveAdmin(@ModelAttribute("authorities") Authorities theAuthorities, Model theModel) {
		Authorities authorities=null;
		try {
			System.out.println("here");
			authorities = authoritiesService.findById(theAuthorities.getId());
		} catch (Exception e) {
		}

		if(authorities!=null) {
			theAuthorities.setAuthority("ROLE_ADMIN");
			authoritiesService.saveAuthorities(theAuthorities);
		}
		return "redirect:/admin/master_data_setup";
	}

}
