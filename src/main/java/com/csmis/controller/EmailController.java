package com.csmis.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.email.EmailSender;
import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service.StaffService;
import com.csmis.service_interface.StaffDetailsServiceInterface;


@Controller
public class EmailController {
	private StaffDetailsServiceInterface staffDetailsServiceInterface;
	private StaffService staffService;
	
	@Autowired
	public EmailController(StaffDetailsServiceInterface theStaffDetailsServiceInterface,StaffService staffService) {
		staffDetailsServiceInterface = theStaffDetailsServiceInterface;
		this.staffService=staffService;
	}
	
	@Autowired
	 private EmailSender emailSender;

	@PostMapping("/emailSending")
	public String sendEmail(@RequestParam("message")String message,
							@RequestParam("subject")String subject) {
		List<StaffDetails> staffDetails = staffDetailsServiceInterface.findByEmailStatus();
		List<String> emailAddresses = new ArrayList<>();
		
		for(StaffDetails s : staffDetails) {
	        Staff staff = staffService.findByID(s.getId());
	        if(staff != null) {
	            emailAddresses.add(staff.getEmail());
	        }
	    }
		
		String[] addresses = emailAddresses.toArray(new String[emailAddresses.size()]);
		
		
		emailSender.sendEmail(subject,message,addresses);
		return "/admin/email/email";
	}
}
