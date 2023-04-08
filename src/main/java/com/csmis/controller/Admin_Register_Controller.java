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
import com.csmis.service.AdminConsumerListService;
import com.csmis.service.AdminRegisterService;
import com.csmis.service.DateService;
import com.csmis.service.HolidayService;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.Prefix_ID_Service;
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
	@Autowired
	DateService dateService;
	@Autowired
	Prefix_ID_Service prefix_id_service;

	// start consumer list today
	@GetMapping("/consumer_list/today")
	public String consumerToday(@RequestParam(name = "search", required = false) String search, Model theModel,Authentication auth) {

		// get today date
		LocalDate today = LocalDate.now().plusDays(1);

		// Staff Lists
		List<Staff> staffList = new ArrayList<>();
		List<Staff> searchStaffList = new ArrayList<>();
		List<String> staff_id_list;
		List<String> staffConfirmation = new ArrayList<>();
		int count = 0;

		String month_year = prefix_id_service.getPrefix_ID(today);
		String year = month_year.substring(3);

		// consumer lists
		List<ConsumerList> consumerLists = consumerListService.getAllConsumerLists();

		Integer temp = today.getDayOfMonth();

		String day = temp.toString();
		if (day.length() < 2)
			day = "0" + day;

		staff_id_list = adminRegisterService.getStaffIdList(consumerLists, month_year);

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

		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("consumerList", consumerLists);// Add the consumer lists to the model
		theModel.addAttribute("day", today);// add date to the model
		theModel.addAttribute("staffList", staffList);// Add Staff Lists to the model
		theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);
		theModel.addAttribute("confirmation_id", staffConfirmation);
		theModel.addAttribute("count", count);
		return "admin/consumer-list/consumer_today";
	}

	// end consumer list today

	// --------------------------------------------------------

	// preparing for Lunch Plan By week page
	@GetMapping("/consumer_list/by_week")
	public String ConsumerListWeekly(@RequestParam(name = "search", required = false) String search, Model theModel,Authentication auth) {

		// used for  holidayService.getThisMonthHoliday()
		LocalDate date = LocalDate.now();

		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;

		//for checking if all day in next week is in next month
		LocalDate today = LocalDate.now();
		Integer monthValue = today.getMonthValue() + 1;

		//get next monday date in today
		while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
			today = today.plusDays(1);
		}

		// for service method usages
		int count = 0;

		// check if all day in next week is in next month
		if (today.getMonthValue() == monthValue) {
			count = 1;
			date = date.plusMonths(1);
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

		List<Staff> staffList = staffService.findAll();

		List<String> notRegisterDateListWeek;
		List<Staff> searchStaffList = new ArrayList<>();

		boolean checker = true;

		if (search == null || search == "")
			checker = false;
//				search staff as you like

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
			notRegisterDateListWeek = adminRegisterService.getAdminWeeklyConfirmDate(month_year + "|" + s.getId(),
					count);
			notWeeklyRegisterDateList.add(notRegisterDateListWeek);
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

		try {
			// add selected dates into json file
			json = objectMapper.writeValueAsString(notWeeklyRegisterDateList);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidaysList);

		} catch (JsonProcessingException e) {
		}

		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("month", Month.of(month) + " / " + year);// get this month and year
		theModel.addAttribute("month", Month.of(month));// get this month
		theModel.addAttribute("day_to_day", day_to_day);// get from day to day between this week
		theModel.addAttribute("listweeklydate", dates);// get dates between this week
		theModel.addAttribute("arrayJson", json);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("staffList", staffList);
		return "admin/consumer-list/consumer_weekly";
	}

	@GetMapping("/lunch_plan/register_by_week")
	public String weekly_register(@RequestParam(value = "list", required = false) List<String> check_list,
			@RequestParam("staffId") String id, Model theModel) {

		boolean checker = false;
		boolean contain2month=false;

		LocalDate today = LocalDate.now();
		Integer nextmonthValue = today.getMonthValue() + 1;
		if(today.getDayOfWeek() == DayOfWeek.MONDAY) today=today.plusDays(1);
		while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
			today = today.plusDays(1);
		}

		if (today.getMonthValue() == nextmonthValue)
			checker = true;

		List<String> this_month = new ArrayList<>();
		List<String> next_month = new ArrayList<>();
		ConsumerList consumerList = new ConsumerList();

		// get this week days
		List<String> uncheckedList = dateService.getWeeklyDate(today);

		String thisMonth_id =op.get_Month_Year_Weekly(0) + "|" + id;
		String nextMonth_id =op.get_Month_Year_Weekly(1) + "|" + id;

		int i = 0;
		// check if this week contain next month's days
		while (i < uncheckedList.size() - 1) {
			if (Integer.parseInt(uncheckedList.get(i)) > Integer.parseInt(uncheckedList.get(++i))) {
				contain2month = true;
				break;
			}
			i++;
		}
		// if checker is 'true' this week contain dates of next month

		i = 0;

		// set and save staff info according to checker condition
		if (contain2month) {
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
		return "redirect:/admin/consumer_list/by_week";
	}

	// ----------------------------------------------------

	// Start Consumer List Monthly
	@GetMapping("/consumer_list/by_month")
	public String ConsumerListMonthly(@RequestParam(name = "search", required = false) String search, Model theModel, Authentication auth) {
		LocalDate date = LocalDate.now();
		date = date.plusMonths(1);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;

		// for holiday
		List<String> holidays = holidayService.getThisMonthHoliday(date);
		for(int i=0;i<holidays.size();i++) {
			if(holidays.get(i).length()<2) holidays.set(i, "0"+holidays.get(i));
		}

		// for mordel addAttribute
		String month_year = prefix_id_service.getPrefix_ID(date);
		String year = month_year.substring(3);

		// set staff already selected dates into selection
		List<String> list = dateService.getMonthlyDates(date);
		List<String> notRegisterDateList;


//			search staff as you like
		List<Staff> staffList = staffService.findAll();
		List<Staff> searchStaffList = new ArrayList<>();
		boolean checker = true;

		if (search == null || search == "")
			checker = false;

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
		ArrayList<List<String>> notJsonRegisterDateList = new ArrayList<>();
		for (Staff s : staffService.findAll()) {
			notRegisterDateList = adminRegisterService.getAdminMonthlyNotRegisteredDate(month_year + "|" + s.getId());
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

		try {
			// add selected dates into json file
			json = objectMapper.writeValueAsString(notJsonRegisterDateList);
			// add holidays into json file
			jsonHoliday = objectMapper.writeValueAsString(holidaysList);

		} catch (JsonProcessingException e) {
		}

		Staff loginStaff = staffService.findByID(auth.getName());

		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("arrayJson", json);
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("list", list);
		theModel.addAttribute("staffList", staffList);
		theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);

		return "admin/consumer-list/consumer_monthly";
	}

	@GetMapping("/consumer_list/monthly_register")
	public String monthly_register_staffId(@RequestParam(value = "list", required = false) List<String> list,
			@RequestParam("staffId") String id, Model theModel) {
		LocalDate date = LocalDate.now().plusMonths(1);
		ConsumerList consumerList = new ConsumerList();

		// get confirmation of user updated dates
		String confirmation = op.getMonthlyConfirmation(list);

		// set consumer and save
		consumerList.setConfirmation(confirmation);
		consumerList.setConsumer_information_id(prefix_id_service.getPrefix_ID(date) + "|" + id);
		op.saveConsumerMonthlyRegistration(consumerList);

		return "redirect:by_month";
	}
	// End Consumer List Monthly

	@GetMapping("/consumer_list/by_day")
	public String consumerByDays() {
		return "admin/consumer-list/consumer_bydays";
	}

	// ----------------------------------------------------

}
