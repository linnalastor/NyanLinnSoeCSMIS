package com.csmis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service.StaffService;
import com.csmis.service_interface.StaffDetailsServiceInterface;

@Controller
@RequestMapping("/admin")
public class EmailListAdminController {
	private StaffDetailsServiceInterface staffDetailsServiceInterface;
	private StaffService staffService;

	@Autowired
	public EmailListAdminController(StaffDetailsServiceInterface theStaffDetailsServiceInterface,StaffService staffService) {
		staffDetailsServiceInterface = theStaffDetailsServiceInterface;
		this.staffService=staffService;
	}

	@GetMapping("/email")
	public String emailList(Model model) {
		List<StaffDetails> staffDetail1 = staffDetailsServiceInterface.findByEmailStatus();
		List<Staff> staffs= new ArrayList<>();
		for(StaffDetails s:staffDetail1) {
			staffs.add( staffService.findByID(s.getId()) );
		}
		model.addAttribute("staffs",staffs);
		return "/admin/email/email";
	}
}
