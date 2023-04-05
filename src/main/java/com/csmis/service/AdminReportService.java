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
import com.csmis.entity.ConsumerList;
import com.csmis.entity.Lunch_Report;

@Service
public class AdminReportService {

	boolean isweekend;

	@Autowired
	DateService dateService;
	@Autowired
	Operator_Register_Service operatorRegisterService;
	@Autowired
	Operator_Report_Service operatorReportService;

	LunchReportRepository lunchReportRepository;

	@Autowired
	public AdminReportService(LunchReportRepository lunchReportRepository) {
		this.lunchReportRepository = lunchReportRepository;
	}

	// get not eat day
	public List<String> getStaffIdList(List<Lunch_Report> lunchReport, String month_year) {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		int index = 0;

		List<String> days = dateService.getMonthlyDates(yesterday);

		List<String> staff_id_list = new ArrayList<>();

		String subString;
		String staff_id;

		for (int i = 0; i < days.size(); i++) {
			if (Integer.parseInt(days.get(i)) == yesterday.getDayOfMonth()) {
				index = i;
			}
		}
		for (Lunch_Report lr : lunchReport) {
			subString = lr.getReport_id();
			subString = subString.substring(0, 7);
			staff_id = lr.getReport_id().substring(8);
			if (subString.equals(month_year)) {
				if (lr.getReport_status().charAt(index) == '1' || lr.getReport_status().charAt(index) == 'n') {
					staff_id_list.add(staff_id);
				}
			}
		}
		return staff_id_list;

	}

	// get dates end

	// ---------------------------------------------------

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

	// get picked days by user report
	public List<String> getPickedDays(String report, int count) {

		List<String> days = new ArrayList<>();

		// get dates of last month
		List<String> monthly_dates = get_Monthly_Dates(count);
		// remove '00' from monthly_dates
		monthly_dates = removeExtraStringInList(monthly_dates);

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

	// get not picked days by user report
	public List<String> getNotPickedDays(String report, String confirmation, int count) {

		List<String> days = new ArrayList<>();

		// get dates of last month
		List<String> monthly_dates = get_Monthly_Dates(count);
		// remove '00' from monthly_dates
		monthly_dates = removeExtraStringInList(monthly_dates);

		// get not picked dates which is 'x' in report
		for (int i = 0; i < report.length(); i++) {
			if (confirmation.charAt(i) == '1')
				if (report.charAt(i) == 'x')
					days.add(monthly_dates.get(i));
		}
		return days;
	}

	// get not registered picked days by user report
	public List<String> getPickedUpWithoutRegisteredDays(String report, int count) {

		List<String> days = new ArrayList<>();

		// get dates of last month
		List<String> monthly_dates = get_Monthly_Dates(count);
		// remove '00' from monthly_dates
		monthly_dates = removeExtraStringInList(monthly_dates);

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

	// remove extra string '00' from month list
	public List<String> removeExtraStringInList(List<String> monthly_dates) {
		// remove '00' from monthly_date
		String target = "00";
		Iterator<String> iter = monthly_dates.iterator();
		while (iter.hasNext()) {
			String str = iter.next();
			if (str.equals(target)) {
				iter.remove();
			}
		}
		return monthly_dates;
	}

	// get month and year string for last week
	public String get_Month_Year_ReportWeekly() {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);
//			Integer month_value = today.getMonthValue()+2;
		Integer month_value = today.getMonthValue();
		String month = month_value.toString();
		if (month.length() < 2)
			month = "0" + month;
		String s = month + "/" + today.getYear();
		return s;
	}

	// get month and year string for last month
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

	public List<List<String>> getPickedDateLists(String id, LocalDate date, List<String> targetDates) {

		List<List<String>> pickedDateList = new ArrayList<>();

		// get this week days
		List<String> dates = dateService.getMonthlyDates(date);
		List<Integer> indexs = new ArrayList<>();

		for (int i = 0; i < dates.size(); i++) {
			for (int j = 0; j < targetDates.size(); j++) {
				if (dates.get(i).equals(targetDates.get(j))) {
					indexs.add(i);
				}
			}
		}

		List<Lunch_Report> reports = new ArrayList<>();
		try {
			reports = operatorReportService.findAll_Monthly(id);
		} catch (Exception e1) {
		}

		List<ConsumerList> confirmations = new ArrayList<>();
		try {
			confirmations = operatorRegisterService.getAllLunchPlan_by_MonthYear(id);
		} catch (Exception e1) {
		}

		for (ConsumerList confirm : confirmations) {
			List<String> pickedDates = new ArrayList<>();
			for (Lunch_Report report : reports) {
				String confirm_id = confirm.getConsumer_information_id();
				String report_id = report.getReport_id();
				if (confirm_id.equals(report_id)) {
					String confirmation = confirm.getConfirmation();
					String reportStatus = report.getReport_status();
					String staff_id = confirm_id.substring(8);
					for (int i : indexs) {
						char c = 'x';
						char r = 'x';
						try {
							c = confirmation.charAt(i);
							r = reportStatus.charAt(i);
						} catch (Exception e) {
						}
						if (c == '1' && r == '1')
							pickedDates.add(dates.get(i) + staff_id);
					}
				}
			}
			pickedDateList.add(pickedDates);
		}
		return pickedDateList;

	}

	public List<List<String>> getNotPickedDateLists(String id, LocalDate date, List<String> targetDates) {

		List<List<String>> notPickedDateList = new ArrayList<>();

		// get this week days
		List<String> dates = dateService.getMonthlyDates(date);
		List<Integer> indexs = new ArrayList<>();

		for (int i = 0; i < dates.size(); i++) {
			for (int j = 0; j < targetDates.size(); j++) {
				if (dates.get(i).equals(targetDates.get(j))) {
					indexs.add(i);
				}
			}
		}

		List<Lunch_Report> reports = new ArrayList<>();
		try {
			reports = operatorReportService.findAll_Monthly(id);
		} catch (Exception e1) {
		}

		List<ConsumerList> confirmations = new ArrayList<>();
		try {
			confirmations = operatorRegisterService.getAllLunchPlan_by_MonthYear(id);
		} catch (Exception e1) {
		}

		for (ConsumerList confirm : confirmations) {
			boolean status = false;
			char c = 'x';
			char r = 'x';
			String confirm_id = confirm.getConsumer_information_id();
			String confirmation = confirm.getConfirmation();
			String staff_id = confirm_id.substring(8);
			List<String> notPickedDates = new ArrayList<>();
			for (Lunch_Report report : reports) {
				String report_id = report.getReport_id();
				if (confirm_id.equals(report_id)) {
					status = true;
					String reportStatus = report.getReport_status();
					for (int i : indexs) {
						try {
							c = confirmation.charAt(i);
							r = reportStatus.charAt(i);
						} catch (Exception e) {
						}
						if (c == '1' && r == 'x')
							notPickedDates.add(dates.get(i) + staff_id);
					}
				}
			}
			if (!status) {
				for (int i : indexs) {
					c = confirmation.charAt(i);
					if (c == '1')
						notPickedDates.add(dates.get(i) + staff_id);
				}
			}
			notPickedDateList.add(notPickedDates);
		}
		return notPickedDateList;

	}

	public List<List<String>> getNotRegisteredDateLists(String id, LocalDate date,List<String> targetDates) {

		List<List<String>> notRegisteredDateList = new ArrayList<>();

		// get this week days
		List<String> dates = dateService.getMonthlyDates(date);

		List<Integer> indexs = new ArrayList<>();
		String monthDate= null;
		String targetDate = null;
		for (int i = 0; i < dates.size(); i++) {
			monthDate=dates.get(i);
			for (int j = 0; j < targetDates.size(); j++) {
				targetDate = targetDates.get(j);
				if (monthDate.equals(targetDate)){
					indexs.add(i);
				}
			}
		}

		List<Lunch_Report> reports = new ArrayList<>();

			try {
				reports = operatorReportService.findAll_Monthly(id);
			} catch (Exception e) {

			}


		for (Lunch_Report report : reports) {
			List<String> notRegisteredDates = new ArrayList<>();
			String report_status = report.getReport_status();
			String staff_id = report.getReport_id().substring(8);
			for(int i : indexs) {
				try {
					if(report_status.charAt(i)=='n') notRegisteredDates.add(dates.get(i) + staff_id);
				} catch (Exception e) {

				}
			}
			notRegisteredDateList.add(notRegisteredDates);
		}
		return notRegisteredDateList;
	}

}
