package com.csmis.controller;


import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.ConsumerList;
import com.csmis.entity.Staff;

import com.csmis.service.Operator_Register_Service;
import com.csmis.service.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin")
public class Admin_Register_Controller {

	@Autowired
	Operator_Register_Service op;
	

	@Autowired
	StaffService staffService;

	/* ConsumerList */
	@GetMapping("/consumer_list/by_day")
	public String consumerByDays() {
		return "admin/consumer-list/consumer_bydays";
	}

	// Start Consumer List Monthly
	@GetMapping("/consumer_list/by_month")

	public String consumerMonthly(Model theModel) {

		String[] holidays = { "05", "26" };
		List<Staff> staffList;

		List<String> list = op.getMonthlyNotRegisteredDate("1234567");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;
		try {
			json = objectMapper.writeValueAsString(list);
			jsonHoliday = objectMapper.writeValueAsString(holidays);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		theModel.addAttribute("arrayJson", json);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("list", op.get_Monthly_Dates());
		theModel.addAttribute("staff", staffService.findByID("05-00003"));
		theModel.addAttribute("staffList", staffService.findAll());
		op.getMonthlyNotRegisteredDate("1234567");

		return "admin/consumer-list/consumer_monthly";
	}

	@GetMapping("monthly_register")
	public String monthly_register(@RequestParam(value = "list", required = false) List<String> list, Model theModel) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;
		ConsumerList consumerList = new ConsumerList();


		// for mordel addAttribute
		String month_year = op.get_Month_Year_Monthly();
		String year = month_year.substring(3);

		// get confirmation of user updated dates
		String confirmation = op.getMonthlyConfirmation(list);
		
		// for holiday
				String[] holidays = { "05", "26" };

				// set staff already selected not registered dates into selection
				list = op.getMonthlyNotRegisteredDate(op.get_Month_Year_Monthly());
				try {
					// add selected dates into json file
					json = objectMapper.writeValueAsString(list);
					// add holidays into json file
					jsonHoliday = objectMapper.writeValueAsString(holidays);

				} catch (JsonProcessingException e) {
				}

				theModel.addAttribute("arrayJson", json);
				theModel.addAttribute("jsonHoliday", jsonHoliday);
				theModel.addAttribute("list", op.get_Monthly_Dates());
//				theModel.addAttribute("staff", staffService.findByID("05-00003"));
				theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);


		consumerList.setConfirmation(confirmation);
		consumerList.setConsumer_information_id("1234567");

		op.saveConsumerMonthlyRegistration(consumerList);

		return "admin/consumer-list/consumer_monthly";
	}
	// End Consumer List Monthly

	@GetMapping("/consumer_list/by_week")
	public String consumerWeekly() {
		return "admin/consumer-list/consumer_weekly";
	}

	@GetMapping("/consumer_list/today")
	public String consumerToday() {
		return "admin/consumer-list/consumer_today";
	}
}
