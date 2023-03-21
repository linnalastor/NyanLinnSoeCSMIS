package com.csmis.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.service.Operator_Register_Service;
import com.csmis.service.Operator_Report_Service;
import com.csmis.service.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/operator")
public class Lunch_Report_Controller {

	@Autowired
	Operator_Report_Service op;
	
	@Autowired
	StaffService staffService;
	
	@GetMapping("/report/yesterday")
	public String ConsumerReportToday(Model theModel,Authentication auth) {
		// substract here
		LocalDate today=LocalDate.now().minusDays(1);
		Integer temp=today.getDayOfMonth();
		String day=temp.toString();
		String active=null;
		if(day.length()<2)day="0"+day;
		String reportStatus=op.getLunch_Report(op.get_Month_Year_ReportMonthly(0)+"|"+auth.getName()).getReport_status();
		for(String s:op.getNotPickedDays(reportStatus)) {
			if(day.equals(s))  active="notactive";
		}
		theModel.addAttribute("day",today);
		theModel.addAttribute("active",active);
		theModel.addAttribute("staff",staffService.findByID(auth.getName()));
		return "operator/register-detail/ConsumerReportToday";
	}

	@GetMapping("/report/last_week")
	public String ConsumerReportWeekly(Model theModel,Authentication auth) throws JsonProcessingException {

		LocalDate today=LocalDate.now().minusMonths(1);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String notpickeddays=null;
		
		String reportStatus=op.getLunch_Report(op.get_Month_Year_ReportMonthly(0)+"|"+auth.getName()).getReport_status();
		List<String> notPickedDates=op.getNotPickedDays(reportStatus);
		
		notpickeddays=objectMapper.writeValueAsString(notPickedDates);
		
		List<String> list=op.getWeeklyDate();
		theModel.addAttribute("Month_Year",today.getMonth().toString()+" / "+today.getYear());
		theModel.addAttribute("day_to_day",list.get(0)+" to "+list.get(list.size()-1));
		theModel.addAttribute("notpickeddays",notpickeddays);
		theModel.addAttribute("staff",staffService.findByID(auth.getName()));
		theModel.addAttribute("listweeklydate",list);
		return "operator/register-detail/ConsumerReportWeekly";
	}

	@GetMapping("/report/last_month")
	public String ConsumerReportMonthly(Model theModel,Authentication auth) throws JsonProcessingException {
		LocalDate today=LocalDate.now().minusMonths(1);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String notpickeddays=null;
		
		String reportStatus=op.getLunch_Report(op.get_Month_Year_ReportMonthly(1)+"|"+auth.getName()).getReport_status();
		List<String> notPickedDates=op.getNotPickedDays(reportStatus);
		
		notpickeddays=objectMapper.writeValueAsString(notPickedDates);

		theModel.addAttribute("notpickeddays",notpickeddays);
		theModel.addAttribute("Month_Year",today.getMonth().toString()+" / "+today.getYear());
		theModel.addAttribute("staff",staffService.findByID(auth.getName()));
		theModel.addAttribute("listweeklydate",op.get_Monthly_Dates(1));
		return "operator/register-detail/ConsumerReportMonthly";
		}
}
