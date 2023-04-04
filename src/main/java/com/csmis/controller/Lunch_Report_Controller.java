package com.csmis.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csmis.entity.ConsumerList;
import com.csmis.entity.Lunch_Report;
import com.csmis.entity.Staff;
import com.csmis.service.HolidayService;
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
	HolidayService holidayService;

	@Autowired
	StaffService staffService;

	@GetMapping("/report/yesterday")
	public String ConsumerReportToday(Model theModel, Authentication auth) {

		String status = null;
		boolean checker = true;

		// get yesterday date
		LocalDate yesterday = LocalDate.now().minusDays(1);

		List<String> holiday = holidayService.getThisMonthHoliday(yesterday);

		Integer temp = yesterday.getDayOfMonth();
		String month = temp.toString();
		String dayvalue = "" + yesterday.getDayOfMonth();
		// got length value 2 month string
		if (month.length() < 2)
			month = "0" + month;

		// get report id for user
		String id = operatorReportService.get_Month_Year_ReportMonthly(0) + "|" + auth.getName();
		// get report status by user id
		String reportStatus = null;
		try {
			Lunch_Report lunchReport = operatorReportService.getLunch_Report(id);
			if (lunchReport != null)
				reportStatus = lunchReport.getReport_status();
		} catch (Exception e) {
		}
		String lunchConfirmation = null;
		try {
			ConsumerList consumerList = operatorRegisterService.getLunchRegistration_by_ID(id);
			if (consumerList != null)
				lunchConfirmation = consumerList.getConfirmation();
		} catch (Exception e) {
		}
		for (String s : holiday) {
			if (dayvalue.equals(s)) {
				checker = false;
				status = "holiday";
			}

		}

		if (reportStatus != null && checker) {
			// get list of picked days of user
			for (String s : operatorReportService.getPickedDays(reportStatus, 0)) {
				// check if today lunch is picked
				if (dayvalue.equals(s))
					status = "picked";
			}

			if (lunchConfirmation != null) {
				// get list of lunch not picked days of user
				for (String s : operatorReportService.getNotPickedDays(reportStatus, lunchConfirmation, 0)) {
					// check if today is not picked day
					if (dayvalue.equals(s))
						status = "notpicked";
				}
			}
			// get list of lunch not registered days of user
			for (String s : operatorReportService.getPickedUpWithoutRegisteredDays(reportStatus, 0)) {
				// check if today is not picked day
				if (dayvalue.equals(s))
					status = "notregistered";
			}

		} else
			status = "notpicked";

		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("day", yesterday);
		// picked/not picked/not registered/holiday status
		theModel.addAttribute("status", status);

		theModel.addAttribute("staff", staffService.findByID(auth.getName()));

		return "operator/register-detail/ConsumerReportToday";
	}

	@GetMapping("/report/last_week")
	public String ConsumerReportWeekly(Model theModel, Authentication auth) throws JsonProcessingException {

		List<String> holidays = holidayService.getThisMonthHoliday(LocalDate.now());
		List<String> notPickedDates = new ArrayList<>();
		List<String> notRegisteredDates = new ArrayList<>();
		List<String> pickedDates = new ArrayList<>();

		// get today date in last month
		LocalDate today = LocalDate.now().minusMonths(0);

		for (int i = 0; i < holidays.size(); i++) {
			if (holidays.get(i).length() < 2)
				holidays.set(i, "0" + holidays.get(i));
		}

		ObjectMapper objectMapper = new ObjectMapper();
		String notPickedDatesString = null;
		String notRegisteredDatesString = null;
		String pickedDatesString = null;
		String holidayString = null;

		// get report for user
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
		}

		if (reportStatus != null) {
			if (lunchConfirmation != null) {
				pickedDates = operatorReportService.getPickedDays(reportStatus, 0);
				notPickedDates = operatorReportService.getNotPickedDays(reportStatus, lunchConfirmation, 0);
			}
			notRegisteredDates = operatorReportService.getPickedUpWithoutRegisteredDays(reportStatus, 0);
		}
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());

		holidayString = objectMapper.writeValueAsString(holidays);
		notRegisteredDatesString = objectMapper.writeValueAsString(notRegisteredDates);
		pickedDatesString = objectMapper.writeValueAsString(pickedDates);
		notPickedDatesString = objectMapper.writeValueAsString(notPickedDates);

		theModel.addAttribute("holidays", holidayString);
		theModel.addAttribute("notregistereddays", notRegisteredDatesString);
		theModel.addAttribute("pickeddays", pickedDatesString);
		theModel.addAttribute("notpickeddays", notPickedDatesString);

		List<String> list = operatorReportService.getWeeklyDate();
		theModel.addAttribute("Month_Year", today.getMonth().toString() + " / " + today.getYear());
		theModel.addAttribute("day_to_day", list.get(0) + " to " + list.get(list.size() - 1));
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("listweeklydate", list);
		return "operator/register-detail/ConsumerReportWeekly";
	}

	@GetMapping("/report/last_month")
	public String ConsumerReportMonthly(Model theModel, Authentication auth) throws JsonProcessingException {

		List<String> holidays = holidayService.getThisMonthHoliday(LocalDate.now());
		List<String> notPickedDates = new ArrayList<>();
		List<String> notRegisteredDates = new ArrayList<>();
		List<String> pickedDates = new ArrayList<>();

		for (int i = 0; i < holidays.size(); i++) {
			if (holidays.get(i).length() < 2)
				holidays.set(i, "0" + holidays.get(i));
		}

		LocalDate today = LocalDate.now().minusMonths(1);

		ObjectMapper objectMapper = new ObjectMapper();
		String notPickedDatesString = null;
		String notRegisteredDatesString = null;
		String pickedDatesString = null;
		String holidayString = null;

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
		}

		if (reportStatus != null) {
			if (lunchConfirmation != null) {
				pickedDates = operatorReportService.getPickedDays(reportStatus, 0);
				notPickedDates = operatorReportService.getNotPickedDays(reportStatus, lunchConfirmation, 0);
			}
			notRegisteredDates = operatorReportService.getPickedUpWithoutRegisteredDays(reportStatus, 0);
		}

		holidayString = objectMapper.writeValueAsString(holidays);
		notRegisteredDatesString = objectMapper.writeValueAsString(notRegisteredDates);
		pickedDatesString = objectMapper.writeValueAsString(pickedDates);
		notPickedDatesString = objectMapper.writeValueAsString(notPickedDates);

		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("holidays", holidayString);
		theModel.addAttribute("notregistereddays", notRegisteredDatesString);
		theModel.addAttribute("pickeddays", pickedDatesString);
		theModel.addAttribute("notpickeddays", notPickedDatesString);

		theModel.addAttribute("Month_Year", today.getMonth().toString() + " / " + today.getYear());
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("listweeklydate", operatorReportService.get_Monthly_Dates(1));
		return "operator/register-detail/ConsumerReportMonthly";
	}
}