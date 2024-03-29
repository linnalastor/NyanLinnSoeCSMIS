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
import com.csmis.entity.Staff;
import com.csmis.service.DateService;
import com.csmis.service.HolidayService;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.Prefix_ID_Service;
import com.csmis.service_interface.AccessServiceInterface;
import com.csmis.service_interface.StaffServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/operator")
public class Lunch_Plan_Register_Controller {

	@Autowired
	StaffServiceInterface staffService;

	@Autowired
	Operator_Register_Service operatorRegisterService;

	@Autowired
	Prefix_ID_Service prefix_ID_Service;

	@Autowired
	DateService dateService;

	@Autowired
	HolidayService holidayService;

	@Autowired
	AccessServiceInterface accessService;


	// Start Consumer List Monthly
	@GetMapping("/lunch_plan/by_month")
	public String ConsumerListMonthly(Model theModel, Authentication auth) {
		
		// set date to next month's 1st date
		LocalDate date= LocalDate.now();
		date = date.withDayOfMonth(1).plusMonths(1);
		
		boolean access = accessService.checkMonthlyAccess();
		theModel.addAttribute("access",access);

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
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("arrayJson", jsonUncheckedDates);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("list", dateService.getMonthlyDates(date));
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("month", date.getMonth() + " / " + year);

		return "operator/register/ConsumerListMonthly";
	}

	@GetMapping("/lunch_plan/register_by_month")
	public String monthly_register(@RequestParam(value = "list", required = false) List<String> list, Model theModel,
			Authentication auth) {

		ConsumerList consumerList = new ConsumerList();

		// get confirmation of user updated dates
		String confirmation = operatorRegisterService.getMonthlyConfirmation(list);

		// set consumer and save
		consumerList.setConfirmation(confirmation);
		consumerList.setConsumer_information_id(operatorRegisterService.get_Month_Year_Monthly(1) + "|" + auth.getName());
		operatorRegisterService.saveConsumerMonthlyRegistration(consumerList);

		return "redirect:/operator/lunch_plan/by_month";
	}

	// preparing for Lunch Plan By week page
	@GetMapping("/lunch_plan/by_week")
	public String ConsumerListWeekly(Model theModel, Authentication auth) {

		//get today date
		LocalDate today= LocalDate.now();

		String jsonUncheckedDates = null;
		String jsonHoliday = null;
		
		boolean access = accessService.checkWeeklyAccess();
		theModel.addAttribute("access",access);

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
		List<String> dates = operatorRegisterService.getWeeklyDate();

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
		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
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

		LocalDate date= LocalDate.now();
		LocalDate nextMonthDay = date.plusMonths(1);
		Integer monthValue = nextMonthDay.getMonthValue();

		while(date.getDayOfWeek() != DayOfWeek.MONDAY) {
			date = date.plusDays(1);
		}

		List<String> this_month = new ArrayList<>();
		List<String> next_month = new ArrayList<>();
		ConsumerList consumerList = new ConsumerList();
		int i = 0;

		// get this week days
		List<String> uncheckedList = dateService.getWeeklyDate(date);

		// check if this week contain next month's days
		boolean checker = false;
		boolean check2month = false;

		String thisMonth_id = prefix_ID_Service.getPrefix_ID(date)+ "|" + auth.getName();
		String nextMonth_id = prefix_ID_Service.getPrefix_ID(nextMonthDay)+ "|" + auth.getName();

		if(date.getMonthValue()==monthValue) checker =true;

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

			consumerList.setConfirmation(operatorRegisterService.getWeeklConfirmation(this_month,uncheckedList,thisMonth_id));
			consumerList.setConsumer_information_id(thisMonth_id);
			operatorRegisterService.saveConsumerMonthlyRegistration(consumerList);

			consumerList.setConfirmation(operatorRegisterService.getWeeklConfirmation(next_month,uncheckedList,nextMonth_id));
			consumerList.setConsumer_information_id(thisMonth_id);
			operatorRegisterService.saveConsumerMonthlyRegistration(consumerList);
		}else if(checker){
			consumerList.setConfirmation(operatorRegisterService.getWeeklConfirmation(check_list,uncheckedList,nextMonth_id));
			consumerList.setConsumer_information_id(nextMonth_id);
			operatorRegisterService.saveConsumerMonthlyRegistration(consumerList);
		}
			else {
			consumerList.setConfirmation(operatorRegisterService.getWeeklConfirmation(check_list,uncheckedList,thisMonth_id));
			consumerList.setConsumer_information_id(thisMonth_id);
			operatorRegisterService.saveConsumerMonthlyRegistration(consumerList);
			}

		return "redirect:/operator/lunch_plan/by_week";
	}

	@GetMapping("/lunch_plan/today")
	public String ConsumerListToday(Model theModel, Authentication auth) {
		LocalDate date = LocalDate.now();
		String month_year = prefix_ID_Service.getPrefix_ID(date);
		String id = month_year+"|"+auth.getName();
		ConsumerList consumer = null;
		String status = "";
		try {
			consumer = operatorRegisterService.getLunchRegistration_by_ID(id);
			operatorRegisterService.getTodayStatus(consumer.getConfirmation());
		} catch (Exception e) {	}
		if(status =="") status = "Your Lunch Register status is not active";
		Staff staff = staffService.findByID(auth.getName());
		theModel.addAttribute("status",status);
		theModel.addAttribute("userName",staff.getName());
		theModel.addAttribute("staff", staff);
		return "operator/register/ConsumerListToday";
	}

}
