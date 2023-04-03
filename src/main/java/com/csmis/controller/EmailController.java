package com.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.email.EmailSender;

@Controller
public class EmailController {
	@Autowired
	 private EmailSender emailSender;
	
	@PostMapping("/emailSending")
	public String sendEmail(@RequestParam("message")String message,
							@RequestParam("subject")String subject) {
		emailSender.sendEmail(subject,message);
		return "/admin/email/email";
	}
}
