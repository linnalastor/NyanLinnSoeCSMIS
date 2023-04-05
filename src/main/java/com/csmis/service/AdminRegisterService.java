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

@Service
public class AdminRegisterService {

	boolean isweekend;
	@Autowired
	DateService dateService;

	ConsumerListRepository consumerListRepository;


	@Autowired
	public AdminRegisterService(ConsumerListRepository consumerListRepository) {
		this.consumerListRepository = consumerListRepository;
	}


	// get not eat day
		public List<String> getStaffIdList(List<ConsumerList> consumerLists,String month_year) {
			LocalDate today = LocalDate.now();
			int index = 0;

			List<String> days = dateService.getMonthlyDates(today);

			List<String> staff_id_list = new ArrayList<>();

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
				if(subString.equals(month_year)) {
					if(c.getConfirmation().charAt(index) == '1') {
						staff_id_list.add(staff_id);
					}
				}
			}
			return staff_id_list;

		}

		//get dates end

		//---------------------------------------------------

		//Month Register


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

		//---------------------------------------------------

		//Week Register
		// Get/Set value from/to database Admin
				public List<String> getAdminWeeklyConfirmDate(String id,int count) {
					ConsumerList consumerList = new ConsumerList();
					String confirmation = null;
					try {
						consumerList = consumerListRepository.getById(id);
						confirmation = consumerList.getConfirmation();
					} catch (Exception exception) {
					}

					boolean isweekend;
					ZoneId zone = ZoneId.systemDefault();
					LocalDate today = LocalDate.now(zone).plusMonths(count);
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
}
