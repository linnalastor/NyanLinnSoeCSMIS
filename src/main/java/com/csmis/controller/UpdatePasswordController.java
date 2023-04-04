package com.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service.StaffService;
import com.csmis.service_interface.StaffDetailsServiceInterface;

@Controller
@RequestMapping("/operator")
public class UpdatePasswordController {
	StaffDetailsServiceInterface staffDetailsServiceInterface;
	StaffService staffService;

	@Autowired
	public UpdatePasswordController(StaffDetailsServiceInterface theStaffDetailsServiceInterface,
			StaffService staffService) {
		staffDetailsServiceInterface = theStaffDetailsServiceInterface;
		this.staffService = staffService;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/oldPassword")
	public String oldPassword(@RequestParam("oldPassword") String oldPassword, Model model, Authentication auth) {
		// System.out.println("input old password is >>>> " + oldPassword);

		StaffDetails staffDetail = staffDetailsServiceInterface.getStaffDetailByID(auth.getName());
		// System.out.println(staffDetail);
		String staffPassword = staffDetail.getPassword();
		// System.out.println("orginal password is >>>>> " + staffPassword);

		boolean passwordsMatch = passwordEncoder.matches(oldPassword, staffPassword);
		// System.out.println(passwordsMatch);

		if (passwordsMatch) {
			model.addAttribute("success", true);
			model.addAttribute("status", false);
		} else {
			model.addAttribute("error", "The old password is incorrect.");
			model.addAttribute("status", true);
		}
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName", loginStaff.getName());

		return "/operator/account-status/update";
	}

	@PostMapping("/update-password")
	public String updatePassword(@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, Model model, Authentication auth) {
		// System.out.println(newPassword);
		// System.out.println(confirmPassword);
		StaffDetails staffDetail = staffDetailsServiceInterface.getStaffDetailByID(auth.getName());
		/*
		 * if(newPassword.equals(confirmPassword)) { model.addAttribute("success",
		 * true); }else { model.addAttribute("error",
		 * "New Password and Confirm Password is not match!"); }
		 */

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String staffNewPassword = encoder.encode(newPassword);
		// System.out.println(staffNewPassword);

		staffDetail.setPassword(staffNewPassword);
		staffDetailsServiceInterface.save(staffDetail);
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName", loginStaff.getName());
		return "/operator/account-status/index";
	}
}
