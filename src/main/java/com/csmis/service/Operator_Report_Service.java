package com.csmis.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.LunchReportRepository;
import com.csmis.entity.Lunch_Report;
import com.csmis.service_interface.OperatorRegisterServiceInterface;
import com.csmis.service_interface.OperatorReportServiceInterface;

@Service
public class Operator_Report_Service implements OperatorReportServiceInterface {

	boolean isweekend;

	@Autowired
	OperatorRegisterServiceInterface operatorRegisterService;
	@Autowired
	DateService dateService;

	LunchReportRepository lunchReportRepository;

	@Autowired
	public Operator_Report_Service(LunchReportRepository lunchReportRepository) {
		this.lunchReportRepository = lunchReportRepository;
	}

	@Override
	public void saveLunchReportMonthlyRegistration(Lunch_Report lunch_report) {
		lunchReportRepository.save(lunch_report);
	}

	@Override
	public Lunch_Report getLunch_Report(String id) {
		Lunch_Report lunch_report;
		try {
			lunch_report = lunchReportRepository.getLunchReport_By_ID(id);
		} catch (Exception e) {
			lunch_report = new Lunch_Report();
		}
		return lunch_report;
	}

	@Override
	public List<Lunch_Report> findAll_Monthly(String prefix_id) {
		return lunchReportRepository.findAll_Monthly(prefix_id);
	}

	@Override
	public void saveAll(List<Lunch_Report> lunch_report_list) {
		lunchReportRepository.saveAll(lunch_report_list);
	}

	// ==========================================> overwrite method finished here

	// get dates of next month
	public List<String> get_Monthly_Dates(int count) {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

		Integer temp;

		LocalDate day = today.withDayOfMonth(1).minusMonths(count);

		List<String> days = new ArrayList<>();

		// input first week of the month
		if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
			isweekend = true;

		if (!isweekend) {
			if (day.getDayOfWeek() == DayOfWeek.MONDAY) {
				temp = day.getDayOfMonth();
				days.add(temp.toString());
				day = day.plusDays(1);
			} else
				days.add("00");

			if (day.getDayOfWeek() == DayOfWeek.TUESDAY) {
				temp = day.getDayOfMonth();
				days.add(temp.toString());
				day = day.plusDays(1);
			} else
				days.add("00");

			if (day.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
				temp = day.getDayOfMonth();
				days.add(temp.toString());
				day = day.plusDays(1);
			} else
				days.add("00");

			if (day.getDayOfWeek() == DayOfWeek.THURSDAY) {
				temp = day.getDayOfMonth();
				days.add(temp.toString());
				day = day.plusDays(1);
			} else
				days.add("00");

			if (day.getDayOfWeek() == DayOfWeek.FRIDAY) {
				temp = day.getDayOfMonth();
				days.add(temp.toString());
				day = day.plusDays(1);
			} else
				days.add("00");
		}

		// input other week days of the month
		while (day.getMonthValue() == today.getMonthValue() - count) {
			isweekend = false;
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				isweekend = true;
			if (!isweekend) {
				temp = day.getDayOfMonth();
				days.add(temp.toString());
			}
			day = day.plusDays(1);
		}
		for (int i = 0; i < days.size(); i++) {
			if (days.get(i).length() < 2)
				days.set(i, "0" + days.get(i));
		}
		return days;
	}

	// Weekly Date Real Page
	public List<String> getWeeklyDate() {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

		List<String> days = new ArrayList<>();
		Integer temp;

		// input first week of month
		while (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
			today = today.minusDays(1);
		}
		today = today.minusDays(5);
		while (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
			today = today.plusDays(1);
			temp = today.getDayOfMonth();
			days.add(temp.toString());
			for (int i = 0; i < days.size(); i++) {
				if (days.get(i).length() < 2)
					days.set(i, "0" + days.get(i));
			}
		}

		return days;
	}

	public String getStatus(String report, String confirmation, String day) {

		return day;
	}

	//get picked days by user report
	public List<String> getPickedDays(String report,int count) {

		List<String> days = new ArrayList<>();

		// get dates of last month
		List<String> monthly_dates = get_Monthly_Dates(count);
		// remove '00' from monthly_dates
		monthly_dates = dateService.removeExtraStringInList(monthly_dates);

		// get picked dates which is '1' in report
		for (int i = 0; i < monthly_dates.size(); i++) {
				try {
					if (report.charAt(i) == '1')
						days.add(monthly_dates.get(i));
				} catch (Exception e) {

				}
		}
		return days;
	}
	
	public List<String> getNotPickedDays(String confirmation,int count) {
		List<String> days = new ArrayList<>();
		List<String> monthly_dates = dateService.getMonthlyDates(LocalDate.now().withDayOfMonth(1).minusMonths(1));
		// remove '00' from monthly_dates
		monthly_dates = dateService.removeExtraStringInList(monthly_dates);
		for(int i=0; i<monthly_dates.size(); i++) {
			if(confirmation.charAt(i)=='1')
				days.add(monthly_dates.get(i));
		}
		
		return days;
	}


	//get not picked days by user report
	public List<String> getNotPickedDays(String report, String confirmation,int count) {

		List<String> days = new ArrayList<>();

		List<String> monthly_dates = dateService.getMonthlyDates(LocalDate.now().withDayOfMonth(1).minusMonths(1));
		// remove '00' from monthly_dates
		monthly_dates = dateService.removeExtraStringInList(monthly_dates);

		// get not picked dates which is 'x' in report
		for (int i = 0; i < report.length(); i++) {
			if(confirmation.charAt(i)=='1')
				if (report.charAt(i) == 'x')
					days.add(monthly_dates.get(i));
		}
		return days;
	}

	//get not registered picked days by user report
	public List<String> getPickedUpWithoutRegisteredDays(String report,int count) {

		List<String> days = new ArrayList<>();

		// get dates of last month
		List<String> monthly_dates = get_Monthly_Dates(count);
		// remove '00' from monthly_dates
		monthly_dates = dateService.removeExtraStringInList(monthly_dates);

		// get not registered picked dates which is 'n' in report
		for (int i = 0; i < monthly_dates.size(); i++) {
				try {
					if (report.charAt(i) == 'n')
						days.add(monthly_dates.get(i));
				} catch (Exception e) {

				}
		}
		return days;
	}

	//get month and year string for last week
	public String get_Month_Year_ReportWeekly() {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);
//		Integer month_value = today.getMonthValue()+2;
		Integer month_value = today.getMonthValue();
		String month = month_value.toString();
		if (month.length() < 2)
			month = "0" + month;
		String s = month + "/" + today.getYear();
		return s;
	}

	//get month and year string for last month
	public String get_Month_Year_ReportMonthly(int count) {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);
		int month_value = today.getMonthValue() - count;
		if (month_value > 12) {
			today.plusYears(1);
			month_value = 1;
		} else if (month_value < 1) {
			today.minusYears(1);
			month_value = 12;

		}
		String month = Integer.toString(month_value);
		if (month.length() < 2)
			month = "0" + month;
		String s = month + "/" + today.getYear();
		return s;
	}

}