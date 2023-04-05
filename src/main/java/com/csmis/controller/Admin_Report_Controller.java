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
	public String reportToday(@RequestParam(name = "search", required = false) String search,Model theModel, Authentication auth) {
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
					count=0;
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
					count = staffList.size();
				}
				String date = ""+yesterday;
				HeadCount headcount= null;
				try {
					headcount = headCountService.find_by_id(date);
				} catch (Exception e) {	
				}
				if(headcount==null) {
					headcount = new HeadCount();
					headcount.setActual_head_count(0);
					headcount.setNot_picked_head_count(0);
					headcount.setNot_registered_head_count(0);
					headcount.setRegistered_head_count(0);
				}

				Staff loginStaff = staffService.findByID(auth.getName());

				theModel.addAttribute("userName",loginStaff.getName());
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
	public String reportListMonth(@RequestParam(name = "search", required = false) String search,Model theModel, Authentication auth) {
		LocalDate date = LocalDate.now().withDayOfMonth(1).minusMonths(1);
		LocalDate nextMonthDate = date.plusMonths(1);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonHoliday = null;
		String jasonNotRegisterWeeklyDateLists = null;
		String jasonNotPickedWeeklyDate = null;
		String jasonPickedWeeklyDateLists = null;
		
		HeadCount totalHeadCount = new HeadCount();
		totalHeadCount.setActual_head_count(0);
		totalHeadCount.setNot_picked_head_count(0);
		totalHeadCount.setNot_registered_head_count(0);
		totalHeadCount.setRegistered_head_count(0);
		while(date.getMonth()!=nextMonthDate.getMonth()) {
			HeadCount headcount= null;
			try {
				headcount = headCountService.find_by_id(""+date);
			} catch (Exception e) {	
			}
			if(headcount!=null) {
				totalHeadCount.setActual_head_count(totalHeadCount.getActual_head_count()+headcount.getActual_head_count());
				totalHeadCount.setNot_picked_head_count(totalHeadCount.getNot_picked_head_count()+headcount.getNot_picked_head_count());
				totalHeadCount.setNot_registered_head_count(totalHeadCount.getNot_registered_head_count()+headcount.getNot_registered_head_count());
				totalHeadCount.setRegistered_head_count(totalHeadCount.getRegistered_head_count()+headcount.getRegistered_head_count());
			}
			date=date.plusDays(1);
		}
		date = date.minusMonths(1);
		// for holiday
		List<String> holidays = holidayService.getThisMonthHoliday(date);

		// for mordel addAttribute
		String prefix_id = prefix_ID_Service.getPrefix_ID(date);
		String year = prefix_id.substring(3,7);
		int month = Integer.parseInt(prefix_id.substring(0,2));

		// set staff already selected dates into selection
		List<String> dates = dateService.getMonthlyDates(date);
		for(String d : dates) {
			if(d.equals("00")) holidays.remove(d); 
		}

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
		
		try {
			jasonNotRegisterWeeklyDateLists = objectMapper.writeValueAsString(notRegisterWeeklyDateLists);
			jasonNotPickedWeeklyDate = objectMapper.writeValueAsString(notPickedWeeklyDate);
			jasonPickedWeeklyDateLists = objectMapper.writeValueAsString(pickedWeeklyDateLists);
			jsonHoliday = objectMapper.writeValueAsString(holidaysList);
		} catch (JsonProcessingException e) {
		}

		Staff loginStaff = staffService.findByID(auth.getName());
		
		theModel.addAttribute("headcount", totalHeadCount);
		theModel.addAttribute("userName",loginStaff.getName());
		theModel.addAttribute("jsonHoliday", jsonHoliday);
		theModel.addAttribute("jasonNotRegisterWeeklyDateLists", jasonNotRegisterWeeklyDateLists);
		theModel.addAttribute("jasonNotPickedWeeklyDate", jasonNotPickedWeeklyDate);
		theModel.addAttribute("jasonPickedWeeklyDateLists", jasonPickedWeeklyDateLists);
		theModel.addAttribute("list", dates);
		theModel.addAttribute("staffList", staffList);
		theModel.addAttribute("month", Month.of(month) + " / " + year);
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
	public String reportListWeek(@RequestParam(name = "search", required = false) String search, Model theModel, Authentication auth) {
		// used for  holidayService.getThisMonthHoliday()

				ObjectMapper objectMapper = new ObjectMapper();
				String jsonHoliday = null;
				String jasonNotRegisterWeeklyDateLists = null;
				String jasonNotPickedWeeklyDate = null;
				String jasonPickedWeeklyDateLists = null;

				//for checking if all day in next week is in next month
				LocalDate date = LocalDate.now().minusDays(7);

				if(date.getDayOfWeek() == DayOfWeek.MONDAY)
					date = date.minusDays(1);
				

				//get next monday date in today
				while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
					date = date.minusDays(1);
				}

				LocalDate nextMonthDate = date.plusDays(5);
				HeadCount totalHeadCount = new HeadCount();
				totalHeadCount = new HeadCount();
				totalHeadCount.setActual_head_count(0);
				totalHeadCount.setNot_picked_head_count(0);
				totalHeadCount.setNot_registered_head_count(0);
				totalHeadCount.setRegistered_head_count(0);
				while(date.getMonth()!=nextMonthDate.getMonth()) {
					HeadCount headcount= null;
					try {
						headcount = headCountService.find_by_id(""+date);
					} catch (Exception e) {	
					}
					if(headcount!=null) {
						totalHeadCount.setActual_head_count(totalHeadCount.getActual_head_count()+headcount.getActual_head_count());
						totalHeadCount.setNot_picked_head_count(totalHeadCount.getNot_picked_head_count()+headcount.getNot_picked_head_count());
						totalHeadCount.setNot_registered_head_count(totalHeadCount.getNot_registered_head_count()+headcount.getNot_registered_head_count());
						totalHeadCount.setRegistered_head_count(totalHeadCount.getRegistered_head_count()+headcount.getRegistered_head_count());
					}
					date=date.plusDays(1);
				}
				//get next monday date in today
				while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
					date = date.minusDays(1);
				}
				
				// for service method usages
				int count = 0;


				// for holiday
				List<String> holidays = holidayService.getThisMonthHoliday(date);

				// get string of month and year of this week
				String month_year = prefix_ID_Service.getPrefix_ID(date);

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
				List<String> dates = dateService.getWeeklyDate(date);
				List<List<String>> notRegisterWeeklyDateLists = adminReportService.getNotRegisteredDateLists(month_year,date,dates);
				List<List<String>> notPickedWeeklyDate = adminReportService.getNotPickedDateLists(month_year,date,dates);
				List<List<String>> pickedWeeklyDateLists = adminReportService.getPickedDateLists(month_year,date,dates);

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

				Staff loginStaff = staffService.findByID(auth.getName());

				theModel.addAttribute("headcount", totalHeadCount);
				theModel.addAttribute("userName",loginStaff.getName());
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
