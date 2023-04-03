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
import com.csmis.entity.HeadCount;
import com.csmis.entity.Lunch_Report;
import com.csmis.entity.Staff;
import com.csmis.service.AdminLunch_ReportService;
import com.csmis.service.AdminReportService;
import com.csmis.service.DateService;
import com.csmis.service.HolidayService;
import com.csmis.service.Operator_Register_Service;
import com.csmis.service.Operator_Report_Service;
import com.csmis.service.Prefix_ID_Service;
import com.csmis.service.StaffService;
import com.csmis.service_interface.HeadCountServiceInterface;
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
	Operator_Report_Service operatorReportService;
	@Autowired
	AdminLunch_ReportService adminLunch_ReportService;
	@Autowired
	StaffService staffService;
	@Autowired
	HolidayService holidayService;
	@Autowired
	Prefix_ID_Service prefix_ID_Service;
	@Autowired
	DateService dateService;
	
	@Autowired
	HeadCountServiceInterface headCountService;
	
	
	/* Report List Today */
	@GetMapping("/report/today")
	public String reportToday(@RequestParam(name = "search", required = false) String search,Model theModel) {
		// get today date
				LocalDate yesterday = LocalDate.now().minusDays(1);
				
				// Staff Lists
				List<Staff> staffList = new ArrayList<>();
				List<Staff> searchStaffList = new ArrayList<>();
				List<String> staff_id_list;
				List<String> staffConfirmation = new ArrayList<>();
				int count = 0;

				String month_year = prefix_ID_Service.getPrefix_ID(yesterday);
				String year = month_year.substring(3);

				// consumer report lists
				List<Lunch_Report> lunchReprotList = adminLunch_ReportService.getAllLunch_Reports();

				Integer temp = yesterday.getDayOfMonth();

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
				String date = ""+yesterday;
				HeadCount headcount= null;
				try {
					headcount = headCountService.find_by_id(date);
				} catch (Exception e) {	}
				if(headcount == null) {
					headcount = new HeadCount();
				}
				
				theModel.addAttribute("headcount",headcount);
				theModel.addAttribute(headcount);
				theModel.addAttribute("lunchReportList", lunchReprotList);
				theModel.addAttribute("day", yesterday);// add date to the model
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
		LocalDate date = LocalDate.now().withDayOfMonth(1).minusMonths(1);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonHoliday = null;
		String jasonNotRegisterWeeklyDateLists = null;
		String jasonNotPickedWeeklyDate = null;
		String jasonPickedWeeklyDateLists = null;

		// for holiday
		List<String> holidays = holidayService.getThisMonthHoliday(date);

		// for mordel addAttribute
		String prefix_id = prefix_ID_Service.getPrefix_ID(date);
		String year = prefix_id.substring(3,7);
		int month = Integer.parseInt(prefix_id.substring(0,2));

		// set staff already selected dates into selection
		List<String> dates = dateService.getMonthlyDates(date);

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
		
		
		// for adding model attributes to html for show data
//		==========================================================================================================				
		List<List<String>> notRegisterWeeklyDateLists = adminReportService.getNotRegisteredDateLists(prefix_id,date,dates);
		List<List<String>> notPickedWeeklyDate = adminReportService.getNotPickedDateLists(prefix_id,date,dates);
		List<List<String>> pickedWeeklyDateLists = adminReportService.getPickedDateLists(prefix_id,date,dates);

		System.out.println(date);
		System.out.println("notRegisterWeeklyDateLists==> "+notRegisterWeeklyDateLists);
		System.out.println("notPickedWeeklyDate==> "+notPickedWeeklyDate);
		System.out.println("pickedWeeklyDateLists==> "+pickedWeeklyDateLists);
		
		try {
			jasonNotRegisterWeeklyDateLists = objectMapper.writeValueAsString(notRegisterWeeklyDateLists);
			jasonNotPickedWeeklyDate = objectMapper.writeValueAsString(notPickedWeeklyDate);
			jasonPickedWeeklyDateLists = objectMapper.writeValueAsString(pickedWeeklyDateLists);
			jsonHoliday = objectMapper.writeValueAsString(holidaysList);
		} catch (JsonProcessingException e) {
		}

		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("jasonNotRegisterWeeklyDateLists", jasonNotRegisterWeeklyDateLists);
		theModel.addAttribute("jasonNotPickedWeeklyDate", jasonNotPickedWeeklyDate);
		theModel.addAttribute("jasonPickedWeeklyDateLists", jasonPickedWeeklyDateLists);
		theModel.addAttribute("list", dates);
		theModel.addAttribute("staffList", staffList);
		theModel.addAttribute("month", Month.of(month) + " / " + year);
		theModel.addAttribute("listweeklydate", adminReportService.get_Monthly_Dates(1));
		
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

				ObjectMapper objectMapper = new ObjectMapper();
				String jsonHoliday = null;
				String jasonNotRegisterWeeklyDateLists = null;
				String jasonNotPickedWeeklyDate = null;
				String jasonPickedWeeklyDateLists = null;

				//for checking if all day in next week is in next month
				LocalDate today = LocalDate.now();
				
				if(today.getDayOfWeek() == DayOfWeek.MONDAY) 
					today = today.minusDays(1);

				//get next monday date in today
				while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
					today = today.minusDays(1);
				}
				
				// for service method usages
				int count = 0;


				// for holiday
				List<String> holidays = holidayService.getThisMonthHoliday(today);

				// get string of month and year of this week
				String month_year = prefix_ID_Service.getPrefix_ID(today);
				
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
				
				// for adding model attributes to html for show data
//				==========================================================================================================				
				List<String> dates = dateService.getWeeklyDate(today);
				List<List<String>> notRegisterWeeklyDateLists = adminReportService.getNotRegisteredDateLists(month_year,today,dates);
				List<List<String>> notPickedWeeklyDate = adminReportService.getNotPickedDateLists(month_year,today,dates);
				List<List<String>> pickedWeeklyDateLists = adminReportService.getPickedDateLists(month_year,today,dates);

				System.out.println("notRegisterWeeklyDateLists==> "+notRegisterWeeklyDateLists);
				System.out.println("notPickedWeeklyDate==> "+notPickedWeeklyDate);
				System.out.println("pickedWeeklyDateLists==> "+pickedWeeklyDateLists);
				
				try {
					jasonNotRegisterWeeklyDateLists = objectMapper.writeValueAsString(notRegisterWeeklyDateLists);
					jasonNotPickedWeeklyDate = objectMapper.writeValueAsString(notPickedWeeklyDate);
					jasonPickedWeeklyDateLists = objectMapper.writeValueAsString(pickedWeeklyDateLists);
					jsonHoliday = objectMapper.writeValueAsString(holidaysList);
				} catch (JsonProcessingException e) {
				}

				
				// for model
				Integer month = Integer.parseInt(month_year.substring(0, 2));
				String year = month_year.substring(3);
				String day_to_day = "( " + "From " + dates.get(0) + " To " + dates.get(dates.size() - 1) + " )";

				theModel.addAttribute("month", Month.of(month) + " / " + year);// get this month and year
				theModel.addAttribute("month", Month.of(month));// get this month
				theModel.addAttribute("day_to_day", day_to_day);// get from day to day between this week
				theModel.addAttribute("listweeklydate", dates);// get dates between this week
				theModel.addAttribute("jasonNotRegisterWeeklyDateLists", jasonNotRegisterWeeklyDateLists);
				theModel.addAttribute("jasonNotPickedWeeklyDate", jasonNotPickedWeeklyDate);
				theModel.addAttribute("jasonPickedWeeklyDateLists", jasonPickedWeeklyDateLists);
				theModel.addAttribute("jsonHoliday", jsonHoliday);
				theModel.addAttribute("listweeklydate",adminReportService.getWeeklyDate());
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
