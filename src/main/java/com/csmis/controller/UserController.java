package com.csmis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.entity.Avoidmeat;
import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.PdfService;
import com.csmis.service.StaffDetailsService;
import com.csmis.service.StaffService;
import com.csmis.service_interface.AvoidmeatServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/operator")
public class UserController {

	@Autowired
	Operator_Register_Service op;

	@Autowired
	PdfService pdfService;

	@Autowired
	StaffService staffService;

	@Autowired
	StaffDetailsService staffDetailsService;
	
	@Autowired
	AvoidmeatServiceInterface avoidMeatService;

	@GetMapping("/dashboard")
	public String Dashboard(Model theModel, Authentication auth) {
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		return "operator/User_Dashboard";
	}

	@GetMapping("/menu")
	public String UserMenu(Model theModel, Authentication auth) throws IOException {
		String pdf = "ThisWeek.pdf";
		String encodedPdf = pdfService.getPdfAsByteString(pdf);
		theModel.addAttribute("pdf", encodedPdf);

		String next_pdf = "NextWeek.pdf";
		String next_encodedPdf = pdfService.getPdfAsByteString(next_pdf);
		Staff loginStaff = staffService.findByID(auth.getName());
		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("npdf", next_encodedPdf);
		theModel.addAttribute(theModel);
		return "operator/User_Menu";
	}

	// End Consumer ListWeekly

	@GetMapping("/lunch_plan/today")
	public String ConsumerListToday(Model theModel, Authentication auth) {
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		return "operator/register/ConsumerListToday";
	}

	@GetMapping("/account")
	public String account(Model theModel, Authentication auth) {

		StaffDetails staff = staffDetailsService.getStaffDetailByID(auth.getName());

		StaffDetails staffDetail = staffDetailsService.getStaffDetailByID(auth.getName());
		String description = staffDetail.getDescription();
		List<String> descriptionLists = null;
		try {
			descriptionLists = Arrays.asList(description.split(","));
		} catch (Exception e1) {
			descriptionLists = new ArrayList<>();
		}

		if (descriptionLists == null || descriptionLists.isEmpty() || descriptionLists.get(0).trim().equals("")) {
		    descriptionLists = new ArrayList<>();
		}

		theModel.addAttribute("descriptionLists", descriptionLists);

		theModel.addAttribute("emailStatus",staff.getEmail_status());
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(staff.getEmail_status());

		} catch (JsonProcessingException e) {

		}
		
		List<Avoidmeat> avoidMeatObjList = avoidMeatService.findAll();
		List<String> avoidMeatList = new ArrayList<>();
		for(Avoidmeat avoidmeat : avoidMeatObjList) {
			avoidMeatList.add(avoidmeat.getName());
		}
		
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("json", json);
		theModel.addAttribute("avoidMeatList",avoidMeatList);
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		return "/operator/account-status/index";
	}

	@PostMapping("/account_status")
	public String accountStatus(Model model, Authentication auth) {
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		return "/operator/account-status/index";
	}

	@GetMapping("/update")
	public String updateAccount(Model model, Authentication auth) {
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("status", true);
		return "/operator/account-status/update";
	}
}
