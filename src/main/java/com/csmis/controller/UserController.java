package com.csmis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/operator")
public class UserController {

	@GetMapping("/dashboard")
	public String Dashboard(Model theModel) {

		return "operator/User_Dashboard";
	}
	
	@GetMapping("/email_status")
	public String emailing(@RequestParam("myHiddenParam") String s, Model theModel) {
		System.out.println("Email status: " + s);
		return "operator/user_login";
	}

	@GetMapping("/menu")
	public String UserMenu(Model theModel) {

		return "operator/User_Menu";
	}

	@GetMapping("/lunch_plan/monthly")
	public String ConsumerListMonthly(Model theModel) {

		return "operator/register/ConsumerListMonthly";
	}

	@GetMapping("/lunch_plan/by_week")
	public String ConsumerListWeekly(Model theModel) {

		return "operator/register/ConsumerListWeekly";
	}

	@GetMapping("/lunch_plan/today")
	public String ConsumerListToday(Model theModel) {

		return "operator/register/ConsumerListToday";
	}

	@GetMapping("/report/last_month")
	public String ConsumerReportMonthly(Model theModel) {

		return "operator/register-detail/ConsumerReportMonthly";
	}

	@GetMapping("/report/yesterday")
	public String ConsumerReportToday(Model theModel) {

		return "operator/register-detail/ConsumerReportToday";
	}

	@GetMapping("/report/last_week")
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
		System.out.println(status);
		theModel.addAttribute("status", status);
		return "/operator/account-status/index";
	}

	@GetMapping("/account_status/update")
	public String updateAccount() {
		return "/operator/account-status/update";
	}
}
