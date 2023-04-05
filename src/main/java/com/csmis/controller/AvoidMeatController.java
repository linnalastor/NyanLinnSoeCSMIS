package com.csmis.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.entity.Avoidmeat;
import com.csmis.entity.Staff;
import com.csmis.entity.StaffDetails;
import com.csmis.service_interface.AvoidmeatServiceInterface;
import com.csmis.service_interface.StaffDetailsServiceInterface;
import com.csmis.service_interface.StaffServiceInterface;

@Controller
@RequestMapping("/admin")
public class AvoidMeatController {

	@Autowired
	StaffDetailsServiceInterface staffDetailsService;
	@Autowired
	AvoidmeatServiceInterface avoidmeatService;
	@Autowired
	StaffServiceInterface staffService;

	@GetMapping("/avoidMeat")
	public String avoidMeatList(Model model, Authentication auth) {
		List<Avoidmeat> avoidMeatlist =avoidmeatService.findAll();
		List<StaffDetails> staffDetailList = staffDetailsService.getStaffDetails();
		List<String> staffID = new ArrayList<>();
		List<String> descriptions = new ArrayList<>();
		List<List<String>> descriptionCount_list = new ArrayList<>();
		List<List<String>> staffDescription_list = new ArrayList<>();

		for(StaffDetails staffDetail : staffDetailList) {
			String description = staffDetail.getDescription();
			String id =staffDetail.getId();
			if(!description.equals(null)&& !description.equals("")) {
				descriptions.add(description);
				staffID.add(id);
				List<String> list = new ArrayList<>();
				list.add(id);
				list.add(description);
				staffDescription_list.add(list);
			}
		}
		for(Avoidmeat avoidMeat : avoidMeatlist) {
			String avoidMeatName =avoidMeat.getName();
			int count =0;
			List<String> descriptionCount = new ArrayList<>();
			for(String description : descriptions) {
				List<String> list =  Arrays.asList(description.split(","));
				for(String des : list) {
					if(avoidMeatName.equals(des)) count++;
				}
			}
			descriptionCount.add(avoidMeatName);
			descriptionCount.add(""+count);
			descriptionCount_list.add(descriptionCount);
		}
		Staff loginStaff = staffService.findByID(auth.getName());

		model.addAttribute("userName",loginStaff.getName());
		model.addAttribute("desList",descriptionCount_list);
		model.addAttribute("staffDesList", staffDescription_list);

		return "admin/consumer-list/avoid_meat_list";
	}

}
