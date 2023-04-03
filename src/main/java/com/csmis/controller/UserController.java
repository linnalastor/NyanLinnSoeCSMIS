package com.csmis.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.StaffDetails;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.PdfService;
import com.csmis.service.StaffDetailsService;
import com.csmis.service.StaffService;
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

	@GetMapping("/emailing")
	public String emailing(@RequestParam("myHiddenParam") String s, Model theModel) {
		return "operator/user_login";
	}

	@GetMapping("/home")
	public String Login(Model theModel) {

		return "operator/user_login";
	}

	@GetMapping("/dashboard")
	public String Dashboard(Model theModel) {

		return "operator/User_Dashboard";
	}


	@GetMapping("/menu")
	public String UserMenu(Model theModel) throws IOException {
		String pdf="ThisWeek.pdf";
		String encodedPdf =pdfService.getPdfAsByteString(pdf);
		theModel.addAttribute("pdf", encodedPdf);

        String next_pdf="NextWeek.pdf";
		String next_encodedPdf =pdfService.getPdfAsByteString(next_pdf);
		theModel.addAttribute("npdf", next_encodedPdf);

		theModel.addAttribute(theModel);
		return "operator/User_Menu";
	}


	//End Consumer ListWeekly

	@GetMapping("/lunch_plan/today")
	public String ConsumerListToday(Model theModel) {

		return "operator/register/ConsumerListToday";
	}

	@GetMapping("/account")
	public String account(Model theModel,Authentication auth) {
		/*
		 * StaffDetails staff = staffDetailsService.getStaffDetailByID(auth.getName());
		 * 
		 * StaffDetails
		 * staffDetail=staffDetailsService.getStaffDetailByID(auth.getName()); String
		 * description = staffDetail.getDescription();
		 * //System.out.println("Desceiptopn ==>"+description); List<String>
		 * descriptionLists = Arrays.asList(description.split(","));
		 * System.out.println("Value: " + descriptionLists.get(0).length());
		 * if(descriptionLists.get(0).length()==0) {
					descriptionLists = new ArrayList<>();
				}
		 * theModel.addAttribute("descriptionLists",descriptionLists);
		 * 
		 * 
		 * //System.out.println("mail noti is :" + staff.getEmail_status());
		 * theModel.addAttribute(staff.getEmail_status()); ObjectMapper objectMapper =
		 * new ObjectMapper(); String json = null; try { json =
		 * objectMapper.writeValueAsString(staff.getEmail_status());
		 * 
		 * }catch(JsonProcessingException e){
		 * 
		 * } theModel.addAttribute("json",json);
		 */
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		return "/operator/account-status/index";
	}

	@PostMapping("/account_status")
	public String accountStatus(Model model) {

		return "/operator/account-status/index";
	}

	@GetMapping("/update")
	public String updateAccount(Model model) {
		model.addAttribute("status",true);
		return "/operator/account-status/update";
	}
}
