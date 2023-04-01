package com.csmis.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.ConsumerList;
import com.csmis.service.DateService;
import com.csmis.service.HolidayService;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.Prefix_ID_Service;
import com.csmis.service_interface.StaffServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/operator")
public class Lunch_Plan_Register_Controller {

	@Autowired
	StaffServiceInterface staffService;

	@Autowired
	Operator_Register_Service op;

	@Autowired
	Prefix_ID_Service prefix_ID_Service;

	@Autowired
	DateService dateService;

	@Autowired
	HolidayService holidayService;


	// Start Consumer List Monthly
	@GetMapping("/lunch_plan/by_month")
	public String ConsumerListMonthly(Model theModel, Authentication auth) {

		// set date to next month's 1st date
		LocalDate date= LocalDate.now();
		date = date.withDayOfMonth(1).plusMonths(1);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonUncheckedDates = null;
		String jsonHoliday = null;

		// get holidays in this month
		List<String> holidays = null;
		try {
			holidays = holidayService.getThisMonthHoliday(date);
			for(int i=0; i<holidays.size(); i++) {
				if(holidays.get(i).length()<2) holidays.set(i, "0"+holidays.get(i));
			}
		} catch (Exception e1) { }


		// for mordel addAttribute
		String month_year = prefix_ID_Service.getPrefix_ID(date);
		String year = month_year.substring(3);

		// set staff already selected dates into selection
		List<String> list = dateService.getMonthlyNotRegisteredDate(month_year+ "|" + auth.getName(), date);

		try {
			// add selected dates into json file
			jsonUncheckedDates = objectMapper.writeValueAsString(list);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidays);
		} catch (JsonProcessingException e) {
		}

		theModel.addAttribute("arrayJson", jsonUncheckedDates);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("list", op.get_Monthly_Dates(1));
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);

		return "operator/register/ConsumerListMonthly";
	}

	@GetMapping("/lunch_plan/register_by_month")
	public String monthly_register(@RequestParam(value = "list", required = false) List<String> list, Model theModel,
			Authentication auth) {

		ConsumerList consumerList = new ConsumerList();

		// get confirmation of user updated dates
		String confirmation = op.getMonthlyConfirmation(list);

		// set consumer and save
		consumerList.setConfirmation(confirmation);
		consumerList.setConsumer_information_id(op.get_Month_Year_Monthly(1) + "|" + auth.getName());
		op.saveConsumerMonthlyRegistration(consumerList);

		return "redirect:/operator/lunch_plan/by_month";
	}

	// preparing for Lunch Plan By week page
	@GetMapping("/lunch_plan/by_week")
	public String ConsumerListWeekly(Model theModel, Authentication auth) {

		//get today date
		LocalDate today= LocalDate.now();

		String jsonUncheckedDates = null;
		String jsonHoliday = null;

		// add days to today till monday
		while(today.getDayOfWeek() != DayOfWeek.MONDAY) {
			today = today.plusDays(1);
		}

		// get string of month and year of this week
		String month_year = prefix_ID_Service.getPrefix_ID(today);

		// get holidays in this month
		List<String> holidays = null;
		try {
			holidays = holidayService.getThisMonthHoliday(today);
			for(int i=0; i<holidays.size(); i++) {
				if(holidays.get(i).length()<2) holidays.set(i, "0"+holidays.get(i));
			}
		} catch (Exception e1) { }

		// get this week days
		List<String> dates = op.getWeeklyDate();

		// for model
		Integer month = Integer.parseInt(month_year.substring(0, 2));
		String year = month_year.substring(3);
		String day_to_day = "( " + dates.get(0) + " - " + dates.get(dates.size() - 1) + " )";

		// set confirmation of user into list
		List<String> list = dateService.getUncheckedList(month_year + "|" + auth.getName(), today);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// add selected dates into json file
			jsonUncheckedDates = objectMapper.writeValueAsString(list);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidays);

		} catch (JsonProcessingException e) {
		}

		theModel.addAttribute("month", Month.of(month) + " / " + year);
		theModel.addAttribute("day_to_day", day_to_day);
		theModel.addAttribute("listweeklydate", dates);
		theModel.addAttribute("arrayJson", jsonUncheckedDates);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		return "operator/register/ConsumerListWeekly";
	}

	@GetMapping("/lunch_plan/register_by_week")
	public String weekly_register(@RequestParam(value = "listweeklydate", required = false) List<String> check_list,
			Model theModel, Authentication auth) {

		LocalDate today= LocalDate.now();
		LocalDate nextMonthDay = today.plusMonths(1);
		Integer monthValue = nextMonthDay.getMonthValue();

		while(today.getDayOfWeek() != DayOfWeek.MONDAY) {
			today = today.plusDays(1);
		}

		List<String> this_month = new ArrayList<>();
		List<String> next_month = new ArrayList<>();
		ConsumerList consumerList = new ConsumerList();
		int i = 0;

		// get this week days
		List<String> uncheckedList = dateService.getWeeklyDate();

		// check if this week contain next month's days
		boolean checker = false;
		boolean check2month = false;

		String thisMonth_id = prefix_ID_Service.getPrefix_ID(today)+ "|" + auth.getName();
		String nextMonth_id = prefix_ID_Service.getPrefix_ID(nextMonthDay)+ "|" + auth.getName();

		if(today.getMonthValue()==monthValue) checker =true;

		while (i < uncheckedList.size() - 1) {
			if (Integer.parseInt(uncheckedList.get(i)) > Integer.parseInt(uncheckedList.get(++i))) {
				check2month = true;
				break;
			}
			i++;
		}
		// if checker is 'true' this week contain dates of next month

		i = 0;

		// set and save staff info according to checker condition
		if (check2month) {
			while (i < check_list.size()) {

				if (Integer.parseInt(check_list.get(i)) < 10)
					next_month.add(check_list.get(i));
				else
					this_month.add(check_list.get(i));
				i++;
			}

			consumerList.setConfirmation(op.getWeeklConfirmation(this_month,uncheckedList,thisMonth_id));
			consumerList.setConsumer_information_id(thisMonth_id);
			op.saveConsumerMonthlyRegistration(consumerList);

			consumerList.setConfirmation(op.getWeeklConfirmation(next_month,uncheckedList,nextMonth_id));
			consumerList.setConsumer_information_id(thisMonth_id);
			op.saveConsumerMonthlyRegistration(consumerList);
		}else if(checker){
			consumerList.setConfirmation(op.getWeeklConfirmation(check_list,uncheckedList,nextMonth_id));
			consumerList.setConsumer_information_id(nextMonth_id);
			op.saveConsumerMonthlyRegistration(consumerList);
		}
			else {
			consumerList.setConfirmation(op.getWeeklConfirmation(check_list,uncheckedList,thisMonth_id));
			consumerList.setConsumer_information_id(thisMonth_id);
			op.saveConsumerMonthlyRegistration(consumerList);
			}

		return "redirect:/operator/lunch_plan/by_week";
	}

}
