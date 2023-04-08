package com.csmis.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.entity.ConsumerList;
import com.csmis.entity.Lunch_Report;

@Service
public class DoorAccessService {

	@Autowired
	Operator_Report_Service operator_report_service;
	@Autowired
	Operator_Register_Service operator_register_service;
	@Autowired
	DateService dateService;
	@Autowired
	Prefix_ID_Service prefixed_id_service;

	int index = 0;

	public String getMonth(LocalDate date) {
		String month = "" + date.getMonthValue();
		if (month.length() < 2)
			month = "0" + month;

		return month;

	}

	public List<String> door_log_list(List<List<String>> data) {
		List<String> door_access_list = new ArrayList<>();
		int nameColumnIndex = -1;
		int rowCount = 0;
		boolean headerFlag = false;
		while (rowCount < 10 && !headerFlag) {

			List<String> headerRow = data.get(rowCount);

			// Loop through header row to find Name column index
			for (int i = 0; i < headerRow.size(); i++) {

				// Check if header value equals "Name"
				if (headerRow.get(i).equals("Name")) {
					nameColumnIndex = i;
					headerFlag = true;
					break;
				}
			}
			rowCount++;
		}

		// Start from index 1 to skip header row
		if (nameColumnIndex != -1) {
			for (int i = rowCount; i < data.size(); i++) {
				List<String> row = data.get(i);
				if (row.size() > nameColumnIndex) { // Check if row has enough columns
					try {
						String name = row.get(nameColumnIndex);
						door_access_list.add(name);
					} catch (Exception e) {
						door_access_list.removeAll(door_access_list);
						break;
					}
				}

			}
		}
		return door_access_list;

	}

	public Lunch_Report getLunchReport(String dateString, String id) {

		LocalDate date = LocalDate.parse(dateString);

		String month = getMonth(date);

		String prefix_id = "" + month + "/" + date.getYear() + "|";
		String report = "";
		String confirmation = "";

		boolean registered = false;

		// get all dates of this month without SAT &SUN
		List<String> days = dateService.getMonthlyDates(date);
		for(int i=0; i<days.size(); i++){
			if(days.get(i).equals("00")) {
				days.remove(i);
				i--;
			}
		}

		// pre setting report String
		// setting index of today in report string
		for (int j = 0; j < days.size(); j++) {
			report += "x";
			if (Integer.parseInt(days.get(j)) == date.getDayOfMonth()) {
				this.index = j;
			}
		}
		// get lunch plan of staff if available
		ConsumerList lunch_plan = null;
		try {
			lunch_plan = operator_register_service.getLunchRegistration_by_ID(prefix_id + id);
		} catch (Exception e) {
		}

		// check if staff is registered or not
		if (lunch_plan != null) {
			confirmation = lunch_plan.getConfirmation();
			if (confirmation.charAt(index) == '1')
				registered = true;
		}

		// get lunch report of staff if available
		Lunch_Report lunch_report = null;
		try {
			lunch_report = operator_report_service.getLunch_Report(prefix_id + id);
		} catch (Exception e) {
		}
		// get report status of staff
		if (lunch_report == null) {
			lunch_report = new Lunch_Report();
			lunch_report.setReport_id(prefix_id + id);

			// set report status
		} else {
			report = lunch_report.getReport_status();
		}

		String temp = null;
		try {
			temp = report.substring(index + 1);
		} catch (Exception e) {
		}
		// change to '1' if registered
		// change to 'n' if not registered
		if (registered) {
			report = report.substring(0, index) + '1' + temp;
		} else {
			report = report.substring(0, index) + 'n' + temp;
		}
		// set report status string into lunch report of staff
		lunch_report.setReport_status(report);

		return lunch_report;
	}

	public List<Integer> getHeadCount(String dateString) {

		List<Integer> countList = new ArrayList<>();

		LocalDate date = LocalDate.parse(dateString);
		String day = "" + date.getDayOfMonth();
		if (day.length() < 2)
			day = "0" + day;

		List<String> dates = dateService.getMonthlyDates(date);
		dates = dateService.removeExtraStringInList(dates);

		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).equals(day)) {
				index = i;
				break;
			}
		}
		System.out.println(index);

		// get lenght value 2 string month
		String month = getMonth(date);
		// get prefix id for this month
		String prefix_id = prefixed_id_service.getPrefix_ID(date);

		int count = 0;

		// get all lunch plan of this month
		List<ConsumerList> lunchPlanList = operator_register_service.getAllLunchPlan_by_MonthYear(prefix_id);

		// get registered count
		for (ConsumerList c : lunchPlanList)
			if (c.getConfirmation().charAt(index) == '1')
				count++;

		// add registered staff count to list
		countList.add(count);

		count = 0;
		// get all lunch report of this month
		List<Lunch_Report> lunchReportList = operator_report_service.findAll_Monthly(prefix_id);

		// get registered and not registered lunch picked count
		for (Lunch_Report lr : lunchReportList) {
			if (lr.getReport_status().charAt(index) == 'n')
				count++;
		}
		// add not registered lunch picked count to list
		countList.add(count);

		count = 0;
		for (ConsumerList c : lunchPlanList) {
			String registerid = c.getConsumer_information_id();
			String register = c.getConfirmation();
			if (register.charAt(index) == '1') {
				boolean picked = false;
				for (Lunch_Report lr : lunchReportList) {
					String reportid = lr.getReport_id();
					String report = lr.getReport_status();
					if (reportid.equals(registerid)) {
						if (report.charAt(index) == '1') {
							System.out.println("report.charAt(index)  "+report.charAt(index));
							picked=true;	
						}
					}
				}
				System.out.println("here  "+count );
				if(!picked) count++;
			}
		}
		// add not picked lunch picked count to list
		countList.add(count);

		return countList;
	}
}
