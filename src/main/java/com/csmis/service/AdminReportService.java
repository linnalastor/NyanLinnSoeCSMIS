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

@Service
public class AdminReportService{
	
	boolean isweekend;

	LunchReportRepository lunchReportRepository;

	@Autowired
	public AdminReportService(LunchReportRepository lunchReportRepository) {
		this.lunchReportRepository = lunchReportRepository;
	}

	
	
	// get not eat day
			public List<String> getStaffIdList(List<Lunch_Report> lunchReport,String month_year) {
				LocalDate yesterday = LocalDate.now().minusDays(1);
				int index = 0;

				List<String> days = get_Monthly_Dates(0);

				List<String> staff_id_list = new ArrayList<>();


				// remove '00' from monthly_date
				String target = "00";
				Iterator<String> iter = days.iterator();
				while (iter.hasNext()) {
					String str = iter.next();
					if (str.equals(target)) {
						iter.remove();
					}
				}


				String subString;
				String staff_id;

				for (int i = 0; i < days.size(); i++) {
					if (Integer.parseInt(days.get(i)) == yesterday.getDayOfMonth()) {
						index = i;
					}
				}

				for(Lunch_Report lr: lunchReport) {
					subString = lr.getReport_id();
					subString = subString.substring(0,7);
					staff_id  = lr.getReport_id().substring(8);
					if(subString.equals(month_year)) {
						if(lr.getReport_status().charAt(index) == '1' || lr.getReport_status().charAt(index) == 'n') {
							staff_id_list.add(staff_id);
						}
					}
				}




				return staff_id_list;

			}

			//get dates end

			//---------------------------------------------------
	
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

		//get not picked days by user report
		public List<String> getNotPickedDays(String report, String confirmation,int count) {

			List<String> days = new ArrayList<>();

			// get dates of last month
			List<String> monthly_dates = get_Monthly_Dates(count);
			// remove '00' from monthly_dates
			monthly_dates = removeExtraStringInList(monthly_dates);

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

		//remove extra string '00' from month list
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

		//get month and year string for last week
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
