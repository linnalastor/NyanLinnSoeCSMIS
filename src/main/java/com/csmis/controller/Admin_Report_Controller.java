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

import com.csmis.entity.Lunch_Report;
import com.csmis.entity.Staff;
import com.csmis.service.AdminLunch_ReportService;
import com.csmis.service.AdminReportService;
import com.csmis.service.HolidayService;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin")
public class Admin_Report_Controller {
	
	@Autowired
	AdminReportService adminReportService;
	
	@Autowired
	Operator_Register_Service operatorRegisterService;
	
	@Autowired
	AdminLunch_ReportService adminLunch_ReportService;

	@Autowired
	StaffService staffService;
	
	
	@Autowired
	HolidayService holidayService;
	
	
	/* Report List Today */
	@GetMapping("/report/today")
	public String reportToday(@RequestParam(name = "search", required = false) String search,Model theModel) {
		// get today date
				LocalDate today = LocalDate.now().minusDays(1);
				
				System.out.println("search >> "+ search);

				// Staff Lists
				List<Staff> staffList = new ArrayList<>();
				List<Staff> searchStaffList = new ArrayList<>();
				List<String> staff_id_list;
				List<String> staffConfirmation = new ArrayList<>();
				int count = 0;

				String month_year = operatorRegisterService.get_Month_Year_Weekly(count);
				String year = month_year.substring(3);

				// consumer report lists
				List<Lunch_Report> lunchReprotList = adminLunch_ReportService.getAllLunch_Reports();
				System.out.println("lunch report lsit >> "+ lunchReprotList);

				Integer temp = today.getDayOfMonth();

				String day = temp.toString();
				if (day.length() < 2)
					day = "0" + day;

				staff_id_list = adminReportService.getStaffIdList(lunchReprotList, month_year);
				

				// bind staff
				if(staff_id_list != null) {

					for (String id : staff_id_list) {
						staffList.add(staffService.findByID(id));
						count++;
					}
				}
				
				
				boolean checker = true;
				if (search == null || search == "")
					checker = false;
				System.out.println("serach "+ search);

//					search staff as you like
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
				System.out.println("staff List >> "+ staffList);
				

				theModel.addAttribute("lunchReportList", lunchReprotList);
				theModel.addAttribute("day", today);// add date to the model
				theModel.addAttribute("staffList", staffList);// Add Staff Lists to the model
				theModel.addAttribute("month", Month.of(Integer.parseInt(month_year.substring(0, 2))) + " / " + year);
				theModel.addAttribute("confirmation_id", staffConfirmation);
				theModel.addAttribute("count", count);

		return "admin/report-list/today-report-list/todayreport";
	}

	@GetMapping("/report/today/nonregistered")
	public String reportTodayNoRegister() {
		return "admin/report-list/today-report-list/todaynoregister";
	}

	@GetMapping("/report/today/not_picked")
	public String reportTodayNoConsumer() {
		return "admin/report-list/today-report-list/todaynoconsumer";
	}
	//------------------------------------------------------------------
	
	/* Report List by day */
	@GetMapping("/report/by_days")
	public String reportList() {
		return "admin/report-list/byday-report-list/report";
	}

	@GetMapping("/report/by_days/nonregistered")
	public String nonRegister() {
		return "admin/report-list/byday-report-list/nonregister";
	}

	@GetMapping("/report/by_days/not_picked")
	public String nonConsumer() {
		return "admin/report-list/byday-report-list/nonconsumer";
	}
	//------------------------------------------------------------------
	/* Report List Monthly */
	@GetMapping("/report/by_month")
	public String reportListMonth(@RequestParam(name = "search", required = false) String search,Model theModel) {
		LocalDate date = LocalDate.now();
		date = date.plusMonths(1);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String jsonHoliday = null;

		// for holiday
		List<String> holidays = holidayService.getThisMonthHoliday(date);

		// for mordel addAttribute
		String month_year = operatorRegisterService.get_Month_Year_Monthly(1);
		String year = month_year.substring(3);

		// set staff already selected dates into selection
		List<String> list = operatorRegisterService.get_Monthly_Dates(1);
		List<String> notRegisterDateList;

//			List<String> holidayList = op.get_Monthly_Dates(1);

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
//		ArrayList<List<String>> notJsonRegisterDateList = new ArrayList<>();
//		for (Staff s : staffService.findAll()) {
//			notRegisterDateList = adminRegisterService.getAdminMonthlyNotRegisteredDate(month_year + "|" + s.getId());
//			notJsonRegisterDateList.add(notRegisterDateList);
//		}

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
//			json = objectMapper.writeValueAsString(notJsonRegisterDateList);
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

		
		return "admin/report-list/month-report-list/report";
	}

	@GetMapping("/report/by_month/nonregisteredr")
	public String reportMonthNoRegister() {
		return "admin/report-list/month-report-list/monthnonregister";
	}

	@GetMapping("/report/by_month/not_picked")
	public String reportMonthNoConsumer() {
		return "admin/report-list/month-report-list/monthnoconsumer";
	}
	//------------------------------------------------------------------
	/* Report List Weekly */
	@GetMapping("/report/by_week")
	public String reportListWeek(@RequestParam(name = "search", required = false) String search, Model theModel) {
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
				String month_year = operatorRegisterService.get_Month_Year_Weekly(count);

				// get this week days
				List<String> dates = operatorRegisterService.getWeeklyDate();

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
//						search staff as you like

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
//				ArrayList<List<String>> notWeeklyRegisterDateList = new ArrayList<>();
//				for (Staff s : staffService.findAll()) {
//					notRegisterDateListWeek = adminRegisterService.getAdminWeeklyConfirmDate(month_year + "|" + s.getId(),
//							count);
//					notWeeklyRegisterDateList.add(notRegisterDateListWeek);
//				}

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
//					json = objectMapper.writeValueAsString(notWeeklyRegisterDateList);
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
		
		return "admin/report-list/week-report-list/weekreport";
	}

	@GetMapping("/report/by_week/nonregistered")
	public String reportWeekNoRegister() {
		return "admin/report-list/week-report-list/weeknoregister";
	}

	@GetMapping("/report/by_week/not_picked")
	public String reportWeekNoConsumer() {
		return "admin/report-list/week-report-list/weeknoconsumer";
	}

	
}
