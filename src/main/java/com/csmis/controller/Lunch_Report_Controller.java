package com.csmis.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.entity.ConsumerList;
import com.csmis.entity.Lunch_Report;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.Operator_Report_Service;
import com.csmis.service.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/operator")
public class Lunch_Report_Controller {

	@Autowired
	Operator_Report_Service operatorReportService;
	@Autowired
	Operator_Register_Service operatorRegisterService;

	@Autowired
	StaffService staffService;

	@GetMapping("/report/yesterday")
	public String ConsumerReportToday(Model theModel, Authentication auth) {

		String status = null;

		// get yesterday date
		LocalDate today = LocalDate.now().minusDays(1);
		Integer temp = today.getDayOfMonth();
		String day = temp.toString();
		// got length value 2 month string
		if (day.length() < 2)
			day = "0" + day;

		// get report id for user
		String id = operatorReportService.get_Month_Year_ReportMonthly(0) + "|" + auth.getName();
		// get report status by user id
		String reportStatus = null;
		try {
			Lunch_Report lunchReport= operatorReportService.getLunch_Report(id);
			if(lunchReport!=null)
				reportStatus =lunchReport.getReport_status();
		} catch (Exception e) {
		}
		String lunchConfirmation = null;
		try {
			ConsumerList consumerList= operatorRegisterService.getLunchRegistration_by_ID(id);
			if(consumerList!=null)
				lunchConfirmation =consumerList.getConfirmation();
		} catch (Exception e) {
		}

		// get list of picked days of user
		for (String s : operatorReportService.getPickedDays(reportStatus)) {
			// check if today is not picked day
			if (day.equals(s))
				status = "picked";
		}
		// get list of not picked days of user
		for (String s : operatorReportService.getNotPickedDays(reportStatus, lunchConfirmation)) {
			// check if today is not picked day
			if (day.equals(s))
				status = "notpicked";
		}
		// get list of not registered days of user
		for (String s : operatorReportService.getPickedUpWithoutRegisteredDays(reportStatus)) {
			// check if today is not picked day
			if (day.equals(s))
				status = "notRegistered";
		}

		theModel.addAttribute("day", today);
		theModel.addAttribute("status", status);
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		return "operator/register-detail/ConsumerReportToday";
	}

	@GetMapping("/report/last_week")
	public String ConsumerReportWeekly(Model theModel, Authentication auth) throws JsonProcessingException {

		LocalDate today = LocalDate.now().minusMonths(1);

		ObjectMapper objectMapper = new ObjectMapper();
		String notpickeddays = null;
		String id = operatorReportService.get_Month_Year_ReportMonthly(0) + "|" + auth.getName();

		// get report status by user id
		String reportStatus = null;
		try {
			reportStatus = operatorReportService.getLunch_Report(id).getReport_status();
		} catch (Exception e) {
		}
		String lunchConfirmation = null;
		try {
			lunchConfirmation = operatorRegisterService.getLunchRegistration_by_ID(id).getConfirmation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> notPickedDates = operatorReportService.getNotPickedDays(reportStatus, lunchConfirmation);

		notpickeddays = objectMapper.writeValueAsString(notPickedDates);

		List<String> list = operatorReportService.getWeeklyDate();
		theModel.addAttribute("Month_Year", today.getMonth().toString() + " / " + today.getYear());
		theModel.addAttribute("day_to_day", list.get(0) + " to " + list.get(list.size() - 1));
		theModel.addAttribute("notpickeddays", notpickeddays);
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("listweeklydate", list);
		return "operator/register-detail/ConsumerReportWeekly";
	}

	@GetMapping("/report/last_month")
	public String ConsumerReportMonthly(Model theModel, Authentication auth) throws JsonProcessingException {
		LocalDate today = LocalDate.now().minusMonths(1);

		ObjectMapper objectMapper = new ObjectMapper();
		String notpickeddays = null;

		String id = operatorReportService.get_Month_Year_ReportMonthly(1) + "|" + auth.getName();

		// get report status by user id
		String reportStatus = null;
		try {
			reportStatus = operatorReportService.getLunch_Report(id).getReport_status();
		} catch (Exception e) {
		}
		String lunchConfirmation = null;
		try {
			lunchConfirmation = operatorRegisterService.getLunchRegistration_by_ID(id).getConfirmation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> notPickedDates = operatorReportService.getNotPickedDays(reportStatus, lunchConfirmation);

		notpickeddays = objectMapper.writeValueAsString(notPickedDates);

		theModel.addAttribute("notpickeddays", notpickeddays);
		theModel.addAttribute("Month_Year", today.getMonth().toString() + " / " + today.getYear());
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("listweeklydate", operatorReportService.get_Monthly_Dates(1));
		return "operator/register-detail/ConsumerReportMonthly";
	}
}
