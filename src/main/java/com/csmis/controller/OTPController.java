
package com.csmis.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service.OTPSenderService;
import com.csmis.service_interface.StaffDetailsServiceInterface;
import com.csmis.service_interface.StaffServiceInterface;

@Controller
public class OTPController {
//	StaffService staffService2;
	String matchOtp;
	StaffServiceInterface staffService;

	StaffDetailsServiceInterface staffDetailsService;

	private OTPSenderService oTPSenderService;

	@Autowired
	public OTPController(StaffServiceInterface staffService, StaffDetailsServiceInterface staffDetailsService,
			OTPSenderService oTPSenderService) {
		this.staffService = staffService;
		this.staffDetailsService = staffDetailsService;
		this.oTPSenderService = oTPSenderService;
	}

	@GetMapping("/showPage")
	public String showMyLoginPage(Model model, Authentication auth) {
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("email", "");
		model.addAttribute("status", true);
		model.addAttribute("pass", true);
		model.addAttribute("OTPCode","");
		model.addAttribute("otpStatus", true);

		model.addAttribute("chPass", true);
		return "/OTPTest";

	}

	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, Model model, Authentication auth)
			throws InvalidKeyException, NoSuchAlgorithmException {
		Staff staff = null;
		try {
			staff = staffService.findByEmail(email);
		} catch (Exception e) {

		}
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		if (staff != null) { // Check if staff with the given email exists
			String otp = oTPSenderService.generateOTP();
			this.matchOtp = otp;
			oTPSenderService.sendOTP(email, otp);
			model.addAttribute("email", email);
			model.addAttribute("sta", true);
			model.addAttribute("pass", true);

			model.addAttribute("chPass", true);
			model.addAttribute("otp", otp);
			return "/OTPTest";

		} else {

			model.addAttribute("emailerror", "Your email is not register.");
			model.addAttribute("pass", true);
			return "/OTPTest";
		}
	}

//
	@PostMapping("/password")
	public String OTPCheckt(@RequestParam("otp") String otp, @RequestParam("otpemail") String email, Model model, Authentication auth) {

		Staff staff = staffService.findByEmail(email);
		model.addAttribute("id", staff.getId());
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
//
		// Check if the OTP is correct
		if (otp.equals(matchOtp)) {
			model.addAttribute("email", email);
			model.addAttribute("sta", true);
			model.addAttribute("otpStatus", true);
			model.addAttribute("status", true);
			model.addAttribute("OTPCode",otp);
			// TODO: validate the passwords and update the user's password
			return "/OTPTest";
		}

		else {
			model.addAttribute("otperror", "Your otp is wrong,Please Next enter your email.");
			model.addAttribute("email", email);
			model.addAttribute("sta", true);
			model.addAttribute("pass", true);
			model.addAttribute("OTPCode","");
			model.addAttribute("otpStatus", false);

			return "/OTPTest";
		}

	}

//	}
	@PostMapping("/change-password")
	public String Changpass(@RequestParam("id") String id, @RequestParam("confirmPassword") String confirmPassword,
			Model model, @RequestParam("otpemail") String email, Authentication auth) {

		model.addAttribute("email", email);
		StaffDetails staffDetails = staffDetailsService.getByID(id);

		staffDetails.setPassword(confirmPassword);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


		staffDetails.setPassword(encoder.encode(confirmPassword));

		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		staffDetailsService.save(staffDetails);
		return "/fancy-login";

	}

}