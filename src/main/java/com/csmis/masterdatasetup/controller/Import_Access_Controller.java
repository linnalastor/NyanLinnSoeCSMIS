package com.csmis.masterdatasetup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.entity.Access;
import com.csmis.service.AccessService;

@Controller
@RequestMapping("/admin")
public class Import_Access_Controller {


	AccessService accessService;

	@Autowired
	public Import_Access_Controller(AccessService accessService) {
		this.accessService = accessService;

	}

	@GetMapping("/Show_AccessADD")
	public String showFormForADD(Model theModel) {
		Access access = new Access();

		theModel.addAttribute("access", access);

		// send over to our form
		return "/admin/Access_Add";
	}
	@PostMapping("/saveAccess")
	public String saveCost(@ModelAttribute("access") Access theaccess,Model theModel) {
		theaccess.setId(1);
        accessService.saveAccess(theaccess);

		return "redirect:/admin/master_data_setup";
	}






}
