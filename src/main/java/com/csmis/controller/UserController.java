package com.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.csmis.service.OperatorService;
import com.csmis.service.StaffService;

@Controller
@RequestMapping("/operator")
public class UserController {

	@Autowired
	OperatorService op;
	
	@Autowired
	StaffService staffService;

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
	public String UserMenu(Model theModel) {

		return "operator/User_Menu";
	}

	
	//End Consumer ListWeekly

	@GetMapping("/lunch_plan/today")
	public String ConsumerListToday(Model theModel) {

		return "operator/register/ConsumerListToday";
	}

	@GetMapping("/ConsumerReportMonthly")
	public String ConsumerReportMonthly(Model theModel) {

		return "operator/register-detail/ConsumerReportMonthly";
	}

	@GetMapping("/ConsumerReportToday")
	public String ConsumerReportToday(Model theModel) {

		return "operator/register-detail/ConsumerReportToday";
	}

	@GetMapping("/ConsumerReportWeekly")
	public String ConsumerReportWeekly(Model theModel) {

		return "operator/register-detail/ConsumerReportWeekly";
	}

	@GetMapping("/account")
	public String account(Model theModel) {
		theModel.addAttribute("status", 0);
		return "/operator/account-status/index";
	}

	@PostMapping("/account_status")
	public String accountStatus(@RequestParam("myHiddenParam") int status, Model theModel) {
		theModel.addAttribute("status", status);

		return "/operator/account-status/index";
	}

	@GetMapping("/update")
	public String updateAccount() {
		return "/operator/account-status/update";
	}
}
