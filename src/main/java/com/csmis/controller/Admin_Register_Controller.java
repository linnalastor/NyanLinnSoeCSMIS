package com.csmis.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csmis.entity.ConsumerList;
import com.csmis.entity.Staff;
import com.csmis.service.AdminConsumerListService;
import com.csmis.service.AdminRegisterService;
import com.csmis.service.HolidayService;
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

	@Autowired
	AdminConsumerListService consumerListService;
	
	@Autowired
	AdminRegisterService adminRegisterService;
	@Autowired
	HolidayService holidayService;

	// start consumer list today
	@GetMapping("/consumer_list/today")
	public String consumerToday(@RequestParam(name = "search", required = false) String search, Model theModel) {
//			search= search.toLowerCase();
		// get today date
		LocalDate today = LocalDate.now();

		// Staff Lists
		List<Staff> staffList = new ArrayList<>();
		List<Staff> searchStaffList = new ArrayList<>();
		List<String> staff_id_list;
		List<String> staffConfirmation = new ArrayList<>();
		int count = 0;

		String month_year = op.get_Month_Year_Weekly(count);
		System.out.println("get Month Year >> " + month_year);
		String year = month_year.substring(3);

		// consumer lists
		List<ConsumerList> consumerLists = consumerListService.getAllConsumerLists();

		System.out.println("get now date >> " + today);
		Integer temp = today.getDayOfMonth();

		String day = temp.toString();
		if (day.length() < 2)
			day = "0" + day;
		System.out.println("Date >> " + day);

		staff_id_list = adminRegisterService.getStaffIdList(consumerLists, month_year);
		System.out.println("consumer list " + consumerLists);
		System.out.println("month_year list " + month_year);
		System.out.println(".................." + staff_id_list);

		// bind staff
		for (String id : staff_id_list) {
			staffList.add(staffService.findByID(id));
			count++;

		}
		boolean checker = true;
		if (search == null || search == "")
			checker = false;

//			search staff as you like
		if (checker) {
			search = search.toLowerCase();
			for (Staff s : staffList) {
				if (s.getId().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getName().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getDepartment().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getDivision().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getTeam().toLowerCase().contains(search))
					searchStaffList.add(s);
			}
			staffList = searchStaffList;
		}

		System.out.println("------------" + staffList);

		theModel.addAttribute("consumerList", consumerLists);// Add the consumer lists to the model
		theModel.addAttribute("day", today);// add date to the model
		theModel.addAttribute("staffList", staffList);// Add Staff Lists to the model
		theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);
		theModel.addAttribute("confirmation_id", staffConfirmation);
		theModel.addAttribute("count", count);
		return "admin/consumer-list/consumer_today";
	}

	// end consumer list today

	// ----------------------------------------------------

	@GetMapping("/consumer_list/by_day")
	public String consumerByDays() {
		return "admin/consumer-list/consumer_bydays";
	}

	// ----------------------------------------------------

	// Start Consumer List Monthly
	@GetMapping("/consumer_list/by_month")
	public String ConsumerListMonthly(@RequestParam(name = "search", required = false) String search, Model theModel) {
		LocalDate date = LocalDate.now();
		date = date.plusMonths(1);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;

		// for holiday
		List<String> holidays = holidayService.getThisMonthHoliday(date);
		

		// for mordel addAttribute
		String month_year = op.get_Month_Year_Monthly(1);
		String year = month_year.substring(3);

		// set staff already selected dates into selection
		List<String> list = op.get_Monthly_Dates();
		List<String> notRegisterDateList;

//			List<String> holidayList = op.get_Monthly_Dates(1);

//			search staff as you like
		List<Staff> staffList = staffService.findAll();
		List<Staff> searchStaffList = new ArrayList<>();
		boolean checker = true;

		if (search == null || search == "")
			checker = false;
		System.out.println(search);
		System.out.println(checker);

		if (checker) {
			search = search.toLowerCase();

			for (Staff s : staffList) {
				if (s.getId().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getName().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getDepartment().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getDivision().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getTeam().toLowerCase().contains(search))
					searchStaffList.add(s);
			}
			staffList = searchStaffList;
		}

		System.out.println("........................." + staffList);

		// date bind with staff id
		ArrayList<List<String>> notJsonRegisterDateList = new ArrayList<>();
		for (Staff s : staffService.findAll()) {
			notRegisterDateList = adminRegisterService.getAdminMonthlyNotRegisteredDate(month_year + "|" + s.getId());
//				System.out.println("Confirmation ID==>"+month_year+ "|" +s.getId());
//				System.out.println("Not Registered Date List==>"+notRegisterDateList);
			notJsonRegisterDateList.add(notRegisterDateList);
		}

		// holiday date bind with staff id
		ArrayList<List<String>> holidaysList = new ArrayList<>();
		for (Staff s : staffService.findAll()) {
			List<String> staffHolidays = new ArrayList<>();
			for (String holiday : holidays) {
				String holidayId = holiday + s.getId();
				staffHolidays.add(holidayId);

			}
			holidaysList.add(staffHolidays);

		}
//			System.out.println(holidaysList);

		try {
			// add selected dates into json file
			json = objectMapper.writeValueAsString(notJsonRegisterDateList);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidaysList);

		} catch (JsonProcessingException e) {
		}

		theModel.addAttribute("arrayJson", json);
		theModel.addAttribute("jsonHoliday", jsonHoliday);

		theModel.addAttribute("list", list);
		theModel.addAttribute("staffList", staffList);
//			search=null;
		theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);

		return "admin/consumer-list/consumer_monthly";
	}

	@GetMapping("/consumer_list/monthly_register")
	public String monthly_register_staffId(@RequestParam(value = "list", required = false) List<String> list,
			@RequestParam("staffId") String id, Model theModel) {

		ConsumerList consumerList = new ConsumerList();

		// get confirmation of user updated dates
		String confirmation = op.getMonthlyConfirmation(list);

		// set consumer and save
		consumerList.setConfirmation(confirmation);
		consumerList.setConsumer_information_id(op.get_Month_Year_Monthly(1) + "|" + id);
		System.out.println("consumer List >> " + consumerList);
		op.saveConsumerMonthlyRegistration(consumerList);

		return "redirect:by_month";
	}
	// End Consumer List Monthly

	// -------------------------------------------

	// preparing for Lunch Plan By week page
	@GetMapping("/consumer_list/by_week")
	public String ConsumerListWeekly(@RequestParam(name = "search", required = false) String search, Model theModel) {
		LocalDate date = LocalDate.now();
		date = date.plusMonths(1);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;

		LocalDate today = LocalDate.now();
		Integer monthValue = today.getMonthValue() + 1;
		
		while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
			today = today.plusDays(1);
		}

		int count = 0;
		
		boolean monthchecker = false;

		if (today.getMonthValue() == monthValue) {
			monthchecker = true;
			count = 1;
		}

		// set holiday
		// for holiday
		List<String> holidays = holidayService.getThisMonthHoliday(date);

		// get string of month and year of this week
		String month_year = op.get_Month_Year_Weekly(count);

		// get this week days
		List<String> dates = op.getWeeklyDate();

		// for model
		Integer month = Integer.parseInt(month_year.substring(0, 2));
		String year = month_year.substring(3);
		String day_to_day = "( " + "From " + dates.get(0) + " To " + dates.get(dates.size() - 1) + " )";
		// get dates within this week
		

		List<String> notRegisterDateListWeek;

		List<Staff> staffList = staffService.findAll();
		List<Staff> searchStaffList = new ArrayList<>();
		boolean checker = true;

		if (search == null || search == "")
			checker = false;
//			search staff as you like
		
		if (checker) {
			search = search.toLowerCase();
			for (Staff s : staffList) {
				if (s.getId().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getName().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getDepartment().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getDivision().toLowerCase().contains(search))
					searchStaffList.add(s);
				else if (s.getTeam().toLowerCase().contains(search))
					searchStaffList.add(s);
			}
			staffList = searchStaffList;
		}


		// date bind with staff id
		ArrayList<List<String>> notWeeklyRegisterDateList = new ArrayList<>();
		for (Staff s : staffService.findAll()) {
			notRegisterDateListWeek = adminRegisterService.getAdminWeeklyConfirmDate(month_year + "|" + s.getId(),count);
			notWeeklyRegisterDateList.add(notRegisterDateListWeek);
		}
		System.out.println("List of not registered string : "+notWeeklyRegisterDateList);

		// holiday date bind with staff id
		ArrayList<List<String>> holidaysList = new ArrayList<>();
		for (Staff s : staffService.findAll()) {
			List<String> staffHolidays = new ArrayList<>();
			for (String holiday : holidays) {
				String holidayId = holiday + s.getId();
				staffHolidays.add(holidayId);

			}
			holidaysList.add(staffHolidays);

		}

		try {
			// add selected dates into json file
			json = objectMapper.writeValueAsString(notWeeklyRegisterDateList);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidaysList);

		} catch (JsonProcessingException e) {
		}

		theModel.addAttribute("month", Month.of(month) + " / " + year);// get this month and year
		theModel.addAttribute("month", Month.of(month));// get this month
		theModel.addAttribute("day_to_day", day_to_day);// get from day to day between this week
		theModel.addAttribute("listweeklydate", dates);// get dates between this week
		theModel.addAttribute("arrayJson", json);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("staffList", staffList);
//			search=null;
		return "admin/consumer-list/consumer_weekly";
	}

	@GetMapping("/lunch_plan/register_by_week")
	public String weekly_register(@RequestParam(value = "list", required = false) List<String> check_list,
			@RequestParam("staffId") String id,
			Model theModel) {

		boolean checker = false;
		
		LocalDate today= LocalDate.now();
		Integer monthValue = today.getMonthValue()+1;
		while(today.getDayOfWeek() != DayOfWeek.MONDAY) {
			today = today.plusDays(1);
		}
		

		if(today.getMonthValue()==monthValue) checker =true;
		
		List<String> this_month = new ArrayList<>();
		List<String> next_month = new ArrayList<>();
		ConsumerList consumerList = new ConsumerList();


		// get this week days
		List<String> list = op.getWeeklyDate();
		
		int i = 0;
		// check if this week contain next month's days
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

				if (Integer.parseInt(check_list.get(i)) < 10)
					next_month.add(check_list.get(i));
				else
					this_month.add(check_list.get(i));
				i++;
			}

			consumerList.setConfirmation(op.getWeeklConfirmation(this_month));
			consumerList.setConsumer_information_id(op.get_Month_Year_Weekly(0) + "|" + id );
			op.saveConsumerMonthlyRegistration(consumerList);

			consumerList.setConfirmation(op.getMonthlyConfirmation(next_month));
			consumerList.setConsumer_information_id(op.get_Month_Year_Monthly(1) + "|" + id);
			op.saveConsumerMonthlyRegistration(consumerList);
		} else {
			consumerList.setConfirmation(op.getWeeklConfirmation(check_list));
			consumerList.setConsumer_information_id(op.get_Month_Year_Weekly(0) + "|" + id);
			op.saveConsumerMonthlyRegistration(consumerList);
		}
		return "redirect:/admin/consumer_list/by_week";
	}

}
