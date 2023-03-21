package com.csmis.controller;

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
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/operator")
public class Lunch_Plan_Register_Controller {

	@Autowired
	Operator_Register_Service op;

	@Autowired
	StaffService staffService;

	// Start Consumer List Monthly
	@GetMapping("/lunch_plan/by_month")
	public String ConsumerListMonthly(Model theModel, Authentication auth) {

		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;

		// for holiday
		String[] holidays = { "05", "26" };

		// for mordel addAttribute
		String month_year = op.get_Month_Year_Monthly();
		String year = month_year.substring(3);

		// set staff already selected dates into selection
		List<String> list = op.getMonthlyNotRegisteredDate(month_year + "|" + auth.getName());

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
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);

		return "operator/register/ConsumerListMonthly";
	}

	@GetMapping("/lunch_plan/register_by_month")
	public String monthly_register(@RequestParam(value = "list", required = false) List<String> list, Model theModel,
			Authentication auth) {

		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;
		ConsumerList consumerList = new ConsumerList();

		// for mordel addAttribute
		String month_year = op.get_Month_Year_Monthly();
		String year = month_year.substring(3);

		// get confirmation of user updated dates
		String confirmation = op.getMonthlyConfirmation(list);

		// set consumer and save
		consumerList.setConfirmation(confirmation);
		consumerList.setConsumer_information_id(op.get_Month_Year_Monthly() + "|" + auth.getName());
		op.saveConsumerMonthlyRegistration(consumerList);

		// for holiday
		String[] holidays = { "05", "26" };

		// set staff already selected not registered dates into selection
		list = op.getMonthlyNotRegisteredDate(op.get_Month_Year_Monthly() + "|" + auth.getName());
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
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);

		return "operator/register/ConsumerListMonthly";
	}
	// End Consumer ListMonthly

	// preparing for Lunch Plan By week page
	@GetMapping("/lunch_plan/by_week")
	public String ConsumerListWeekly(Model theModel, Authentication auth) {

		String json = null;
		String jsonHoliday = null;

		// set holiday
		String[] holidays = { "24", "23" };

		// get string of month and year of this week
		String month_year = op.get_Month_Year_Weekly();

		// get this week days
		List<String> dates = op.getWeeklyDate();

		// for model
		Integer month = Integer.parseInt(month_year.substring(0, 2));
		String year = month_year.substring(3);
		String day_to_day = "( " + dates.get(0) + " - " + dates.get(dates.size() - 1) + " )";

		// set confirmation of user into list
		List<String> list = op.getWeeklyConfirmDate(month_year + "|" + auth.getName());

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// add selected dates into json file
			json = objectMapper.writeValueAsString(list);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidays);

		} catch (JsonProcessingException e) {
		}

		theModel.addAttribute("month", Month.of(month) + " / " + year);
		theModel.addAttribute("day_to_day", day_to_day);
		theModel.addAttribute("listweeklydate", dates);
		theModel.addAttribute("arrayJson", json);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		return "operator/register/ConsumerListWeekly";
	}

	@GetMapping("/lunch_plan/register_by_week")
	public String weekly_register(@RequestParam(value = "listweeklydate", required = false) List<String> check_list,
			Model theModel, Authentication auth) {

		List<String> this_month = new ArrayList<>();
		List<String> next_month = new ArrayList<>();
		ConsumerList consumerList = new ConsumerList();
		int i = 0;

		// get this week days
		List<String> list = op.getWeeklyDate();

		// check if this week contain next month's days
		boolean checker = false;
		while (i < list.size() - 1) {
			if (Integer.parseInt(list.get(i)) > Integer.parseInt(list.get(++i))) {
				checker = true;
				break;
			}
			i++;
		}
		// if checker is 'true' this week contain dates of next month

		i = 0;

		// set and save staff info according to checker condition
		if (checker) {
			while (i < check_list.size()) {
				
				if (Integer.parseInt(check_list.get(i)) < 5)
					next_month.add(check_list.get(i));
				else
					this_month.add(check_list.get(i));
				i++;
			}

			consumerList.setConfirmation(op.getWeeklConfirmation(this_month));
			consumerList.setConsumer_information_id(op.get_Month_Year_Weekly() + "|" + auth.getName());
			op.saveConsumerMonthlyRegistration(consumerList);

			consumerList.setConfirmation(op.getMonthlyConfirmation(next_month));
			consumerList.setConsumer_information_id(op.get_Month_Year_Monthly() + "|" + auth.getName());
			op.saveConsumerMonthlyRegistration(consumerList);
		} else {
			consumerList.setConfirmation(op.getWeeklConfirmation(check_list));
			consumerList.setConsumer_information_id(op.get_Month_Year_Weekly() + "|" + auth.getName());
			op.saveConsumerMonthlyRegistration(consumerList);
		}

		// for model data add attribute
		String json = null;
		String jsonHoliday = null;

		// set holiday
		String[] holidays = { "24", "23" };

		// get string of month and year of this week
		String month_year = op.get_Month_Year_Weekly();

		// get this week days
		List<String> dates = op.getWeeklyDate();

		// for model
		Integer month = Integer.parseInt(month_year.substring(0, 2));
		String year = month_year.substring(3);
		String day_to_day = "( " + dates.get(0) + " - " + dates.get(dates.size() - 1) + " )";

		// set confirmation of user into list
		list = op.getWeeklyConfirmDate(month_year + "|" + auth.getName());

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// add selected dates into json file
			json = objectMapper.writeValueAsString(list);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidays);

		} catch (JsonProcessingException e) {
		}
		// model data add attribute ends here

		theModel.addAttribute("month", Month.of(month) + " / " + year);
		theModel.addAttribute("day_to_day", day_to_day);
		theModel.addAttribute("listweeklydate", dates);
		theModel.addAttribute("arrayJson", json);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("staff", staffService.findByID(auth.getName()));
		return "operator/register/ConsumerListWeekly";
	}

}
