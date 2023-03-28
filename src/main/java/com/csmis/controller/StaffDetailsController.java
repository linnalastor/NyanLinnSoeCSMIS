package com.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.StaffDetails;
import com.csmis.service_interface.StaffDetailsServiceInterface;

@Controller
@RequestMapping("/operator")
public class StaffDetailsController {
	StaffDetailsServiceInterface staffDetailsServiceInterface;

	@Autowired
	public StaffDetailsController(StaffDetailsServiceInterface theStaffDetailsServiceInterface) {
		staffDetailsServiceInterface = theStaffDetailsServiceInterface;
	}
	
	@PostMapping("/save_avoidMeats")
	public String saveAvoidMeats(@RequestParam("selectedMeats")String selectedMeats, Authentication auth,Model model) {
		StaffDetails staffDetail=staffDetailsServiceInterface.getStaffDetailByID(auth.getName());
		staffDetail.setDescription(selectedMeats);
		staffDetailsServiceInterface.save(staffDetail);
		
		return "redirect:/operator/account"; 
	}
}
