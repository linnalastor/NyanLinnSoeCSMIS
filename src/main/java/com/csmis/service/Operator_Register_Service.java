package com.csmis.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.ConsumerListRepository;
import com.csmis.entity.ConsumerList;
import com.csmis.service_interface.OperatorRegisterServiceInterface;

@Service
public class Operator_Register_Service implements OperatorRegisterServiceInterface {

	boolean isweekend;

	ConsumerListRepository consumerListRepository;

	@Autowired
	public Operator_Register_Service(ConsumerListRepository consumerListRepository) {
		this.consumerListRepository = consumerListRepository;
	}



	@Override
	public void saveConsumerMonthlyRegistration(ConsumerList consumerList) {
		consumerListRepository.save(consumerList);

	}

<<<<<<< Updated upstream
	@Override
	public List<ConsumerList> getAllLunchPlan_by_MonthYear(String prefix) {
		return consumerListRepository.findAll_by_MonthYear(prefix);
	}

	@Override
	public ConsumerList getLunchRegistration_by_ID(String id) {
		// TODO Auto-generated method stub
		ConsumerList consumerList = new ConsumerList();
		
		try {
			consumerList = consumerListRepository.getLunchPlanRegister_by_ID(id);
		} catch (Exception e) {	}
		
		return consumerList;
	}

=======
	// get not eat day
	public List<String> getStaffIdList(List<ConsumerList> consumerLists,String month_year) {

		LocalDate today = LocalDate.now();
		int index = 0;

		List<String> days = get_Monthly_Dates(0);
//		System.out.println("days list >> " + days);

		ConsumerList consumerList = new ConsumerList();
		String confirmation = null;
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
		System.out.println("days list >> " + days);

		String subConfirmation = null;

		String subString;
		String staff_id;

		for (int i = 0; i < days.size(); i++) {
			if (Integer.parseInt(days.get(i)) == today.getDayOfMonth()) {
				index = i;
			}
		}

		for(ConsumerList c: consumerLists) {
			subString = c.getConsumer_information_id();
			subString = subString.substring(0,7);
			staff_id  = c.getConsumer_information_id().substring(8);
			System.out.println("staff_id " + staff_id);
			System.out.println("subString "+subString);
			System.out.println("month_year "+month_year);
			if(subString.equals(month_year)) {
				System.out.println("confirmation id check >> "+c.getConsumer_information_id());
				if(c.getConfirmation().charAt(index) == '1') {
					staff_id_list.add(staff_id);
					System.out.println(staff_id_list);
				}
			}
		}




		System.out.println("Index confirmation >> "+index);
		return staff_id_list;

	}

	//get dates end

	//---------------------------------------------------

>>>>>>> Stashed changes
	// get dates of next month
	public List<String> get_Monthly_Dates(int count) {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

		LocalDate day = today.withDayOfMonth(1).plusMonths(count);
		List<String> days = new ArrayList<>();
		Integer temp;

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
		while (day.getMonthValue() == today.getMonthValue() + count) {
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

	// Get monthly confirmation from database
	public List<String> getMonthlyNotRegisteredDate(String id) {
		ConsumerList consumerList = new ConsumerList();
		String confirmation = null;
		try {
			consumerList = consumerListRepository.getById(id);
			confirmation = consumerList.getConfirmation();
		} catch (Exception exception) {
		}

		boolean isweekend;
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);
		LocalDate day = today.withDayOfMonth(1).plusMonths(1);
		List<String> days = new ArrayList<>();
		Integer temp;
		String strTemp;

		while (day.getMonthValue() == today.getMonthValue() + 1) {
			isweekend = false;
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				isweekend = true;
			if (!isweekend) {
				temp = day.getDayOfMonth();
				strTemp = Integer.toString(temp);
				if (strTemp.length() < 2)
					strTemp = "0" + strTemp;

				days.add(strTemp);

			}
			day = day.plusDays(1);
		}

		List<String> returnList = new ArrayList<>();
		if (confirmation != null) {
			for (int i = 0; i < confirmation.length(); i++) {
				char c = confirmation.charAt(i);
				if (c == 'x') {
					returnList.add(days.get(i));
				}
			}
		}
		return returnList;
	}

	// get admin non register date from database
	public List<String> getAdminMonthlyNotRegisteredDate(String id) {
		ConsumerList consumerList = new ConsumerList();
		String confirmation = null;
		try {
			consumerList = consumerListRepository.getById(id);
			confirmation = consumerList.getConfirmation();
		} catch (Exception exception) {
		}

		boolean isweekend;
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);
		LocalDate day = today.withDayOfMonth(1).plusMonths(1);
		List<String> days = new ArrayList<>();
		Integer temp;
		String strTemp;

		while (day.getMonthValue() == today.getMonthValue() + 1) {
			isweekend = false;
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				isweekend = true;
			if (!isweekend) {
				temp = day.getDayOfMonth();
				strTemp = Integer.toString(temp);
				if (strTemp.length() < 2)
					strTemp = "0" + strTemp;

				strTemp += id.substring(8, id.length());
				days.add(strTemp);

			}
			day = day.plusDays(1);
		}

		List<String> retuenList = new ArrayList<>();
		if (confirmation != null) {
			for (int i = 0; i < confirmation.length(); i++) {
				char c = confirmation.charAt(i);
				if (c == 'x') {
					retuenList.add(days.get(i));
				}
			}
		}
		return retuenList;
	}

	// Start getConfirm Monthly
	public String getMonthlyConfirmation(List<String> list) {

		String[] holidays = { "05", "26" };
		String confirmation = "";
		boolean checker;
		boolean holiday;
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

//		LocalDate day = today.withDayOfMonth(1).plusMonths(3);
		LocalDate day = today.withDayOfMonth(1).plusMonths(1);

		// get confirmation string for a month
//		while (day.getMonthValue() == today.getMonthValue() + 3) {
		while (day.getMonthValue() == today.getMonthValue() + 1) {
			isweekend = false;
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				isweekend = true;
			if (!isweekend) {
				checker = false;
				holiday = false;

				// holiday='true' on the holidays
				for (String s : holidays)
					if (Integer.parseInt(s) == day.getDayOfMonth())
						holiday = true;

				if (list == null) {
					if (holiday)
						confirmation += "h";
					else
						confirmation += "1";
				} else {

					// checker = 'true' on the selected days
					for (String s : list)
						if (Integer.parseInt(s) == day.getDayOfMonth())
							checker = true;

					// x=not register
					if (checker)
						confirmation += "x";
					// h=hoiliday
					else if (holiday)
						confirmation += "h";
					// 1=registered
					else
						confirmation += "1";
				}
			}
			day = day.plusDays(1);
		}

		return confirmation;
	}
	// End getConfirmation Monthly

	//--------------------------------------------

	// Weekly Date Real Page
	public List<String> getWeeklyDate() {
		ZoneId zone = ZoneId.systemDefault();
//		LocalDate day = LocalDate.now(zone);
		LocalDate today = LocalDate.now(zone);

//		LocalDate today = day.withDayOfMonth(1).plusMonths(2);
//		today=today.plusDays(25);

		List<String> days = new ArrayList<>();
		Integer temp;

		// input first week of month
		while (today.getDayOfWeek() != DayOfWeek.SUNDAY) {
			today = today.plusDays(1);
		}

		while (today.getDayOfWeek() != DayOfWeek.FRIDAY) {
			today = today.plusDays(1);
			temp = today.getDayOfMonth();
			days.add(temp.toString());
			for (int i = 0; i < days.size(); i++) {
				if (days.get(i).length() < 2)
					days.set(i, "0" + days.get(i));
			}

		}
//		System.out.println("op days week >> " +days);

		return days;
	}

	// set and return weekly confirmation with the weekly selected list
	public String getWeeklConfirmation(List<String> list) {
		String[] holidays = { "05", "26" };
		String confirmation = "";
		boolean checker;
		boolean holiday;
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);

//		LocalDate day = today.withDayOfMonth(1).plusMonths(2);
		LocalDate day = today.withDayOfMonth(1);

//		while (day.getMonthValue() == today.getMonthValue()+2) {
		while (day.getMonthValue() == today.getMonthValue()) {

			isweekend = false;
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				isweekend = true;
			if (!isweekend) {
				checker = false;
				holiday = false;
				for (String s : holidays) {
					if (Integer.parseInt(s) == day.getDayOfMonth())
						holiday = true;
				}
				if (list == null) {
					if (holiday)
						confirmation += "h";
					else
						confirmation += "1";
				} else {
					for (String s : list) {
						if (Integer.parseInt(s) == day.getDayOfMonth())
							checker = true;
					}

					if (checker)
						confirmation += "x";
					else if (holiday)
						confirmation += "h";
					else
						confirmation += "1";
				}
			}
			day = day.plusDays(1);
		}

		return confirmation;
	}

	// Get/Set value from/to database
	public List<String> getWeeklyConfirmDate(String id) {
		ConsumerList consumerList = new ConsumerList();
		String confirmation = null;
		try {
			consumerList = consumerListRepository.getById(id);
			confirmation = consumerList.getConfirmation();
		} catch (Exception exception) {
		}

		boolean isweekend;
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);
		LocalDate day = today.withDayOfMonth(1);
		List<String> days = new ArrayList<>();
		Integer temp;
		String strTemp;

		while (day.getMonthValue() == today.getMonthValue()) {
			isweekend = false;
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				isweekend = true;
			if (!isweekend) {
				temp = day.getDayOfMonth();
				strTemp = Integer.toString(temp);
				if (strTemp.length() < 2)
					strTemp = "0" + strTemp;
				days.add(strTemp);
			}
			day = day.plusDays(1);
		}

		List<String> retuenList = new ArrayList<>();
		if (confirmation != null) {
			for (int i = 0; i < confirmation.length(); i++) {
				char c = confirmation.charAt(i);
				if (c == 'x') {
					retuenList.add(days.get(i));
				}
			}
		}
		return retuenList;
	}

	// Get/Set value from/to database Admin
		public List<String> getAdminWeeklyConfirmDate(String id) {
			ConsumerList consumerList = new ConsumerList();
			String confirmation = null;
			try {
				consumerList = consumerListRepository.getById(id);
				confirmation = consumerList.getConfirmation();
			} catch (Exception exception) {
			}

			boolean isweekend;
			ZoneId zone = ZoneId.systemDefault();
			LocalDate today = LocalDate.now(zone);
			LocalDate day = today.withDayOfMonth(1);
			List<String> days = new ArrayList<>();
			Integer temp;
			String strTemp;

			while (day.getMonthValue() == today.getMonthValue()) {
				isweekend = false;
				if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
					isweekend = true;
				if (!isweekend) {
					temp = day.getDayOfMonth();
					strTemp = Integer.toString(temp);
					if (strTemp.length() < 2)
						strTemp = "0" + strTemp;

						strTemp += id.substring(8, id.length());

					days.add(strTemp);
				}
				day = day.plusDays(1);
			}

			List<String> retuenList = new ArrayList<>();
			if (confirmation != null) {
				for (int i = 0; i < confirmation.length(); i++) {
					char c = confirmation.charAt(i);
					if (c == 'x') {
						retuenList.add(days.get(i));
					}
				}
			}
			return retuenList;
		}

	public String get_Month_Year_Weekly() {
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

	public String get_Month_Year_Monthly() {
		ZoneId zone = ZoneId.systemDefault();
		LocalDate today = LocalDate.now(zone);
//		Integer month_value = today.getMonthValue() + 3;
		int month_value = today.getMonthValue() + 1;
		if (month_value > 12) {
			today.plusYears(1);
			month_value = 1;
		}
		String month = Integer.toString(month_value);
		if (month.length() < 2)
			month = "0" + month;
		String s = month + "/" + today.getYear();
		return s;
	}

}
