
package com.csmis.controller;

import com.csmis.dao.StaffDetailsRepository;
import com.csmis.entity.Cost;
import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service.OTPSenderService;
import com.csmis.service.StaffService;
import com.csmis.service_interface.CostServiceInterface;
import com.csmis.service_interface.StaffDetailsServiceInterface;
import com.csmis.service_interface.StaffServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
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
	public String showMyLoginPage(Model model) {
//		List<Staff> staff=staffService.findAll();
//		staffService.saveStaffs(staff);
//		staff=staffService.findAll();
		model.addAttribute("email", "");
		model.addAttribute("status", true);
		model.addAttribute("pass", true);
		model.addAttribute("OTPCode","");
		
//		  model.addAttribute("otp", staff);
		return "/admin/OTPTest";

	}

	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, Model model)
			throws InvalidKeyException, NoSuchAlgorithmException {
		// Staff staff=new Staff();
		Staff staff = null;
		System.out.println(email);
		try {
			staff = staffService.findByEmail(email);
		} catch (Exception e) {

		}
		System.out.println(staff);
		// Find staff by email

		if (staff != null) { // Check if staff with the given email exists

			String otp = oTPSenderService.generateOTP();
			this.matchOtp = otp;
			oTPSenderService.sendOTP(email, otp);
			model.addAttribute("email", email);
			model.addAttribute("sta", true);
			model.addAttribute("pass", true);
			
			model.addAttribute("otp", otp);
			System.out.println(otp);
			return "/admin/OTPTest";

		} else {

			model.addAttribute("emailerror", "Your email is not register.");
			model.addAttribute("pass", true);
			return "/admin/OTPTest";
		}
	}

//   
	@PostMapping("/password")
	public String OTPCheckt(@RequestParam("otp") String otp, @RequestParam("otpemail") String email, Model model) {

		Staff staff = staffService.findByEmail(email);
		model.addAttribute("id", staff.getId());
//    	   
		// Check if the OTP is correct
		if (otp.equals(matchOtp)) {
			model.addAttribute("email", email);
			model.addAttribute("sta", true);
			model.addAttribute("otpStatus", true);
			model.addAttribute("status", true);
			model.addAttribute("OTPCode",otp);
			// TODO: validate the passwords and update the user's password
			return "/admin/OTPTest";
		}

		else {
			model.addAttribute("otperror", "Your otp is wrong,Please Next enter your email.");
			model.addAttribute("email", email);
			model.addAttribute("sta", true);
			model.addAttribute("pass", true);
			model.addAttribute("OTPCode","");
			model.addAttribute("otpStatus", false);
			
			return "/admin/OTPTest";
		}

	}

//	}
	@PostMapping("/change-password")
	public String Changpass(@RequestParam("id") String id, @RequestParam("confirmPassword") String confirmPassword,
			Model model, @RequestParam("otpemail") String email) {
		
		model.addAttribute("email", email);
		StaffDetails staffDetails = staffDetailsService.getByID(id);

		staffDetails.setPassword(confirmPassword);

		System.out.println(id);
		System.out.println(confirmPassword);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		
		staffDetails.setPassword(encoder.encode(confirmPassword));
		
		staffDetailsService.save(staffDetails);
		return "/fancy-login";

	}

}