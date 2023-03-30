package com.csmis.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.ConsumerListRepository;
import com.csmis.entity.ConsumerList;

@Service
public class DateService {
	
	@Autowired
	ConsumerListRepository consumerListRepository;

		public List<String> getWeeklyDate() {
			ZoneId zone = ZoneId.systemDefault();
			LocalDate today = LocalDate.now(zone);

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
			return days;
		}
		
		
		// get dates of next month
		public List<String> getMonthlyDates(LocalDate today) {
			
			boolean isweekend = false;
			
			LocalDate day = today.withDayOfMonth(1);
			
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
			while (day.getMonthValue() == today.getMonthValue()) {
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
		
		
		public List<String> getMonthlyNotRegisteredDate(String id, LocalDate today) {

			String confirmation = getConfirmation(id);
			List<String> days;
			return null;
		}
		
		public List<String> getUncheckedList(String id, LocalDate date){
			
			String confirmation = getConfirmation(id);

			List<String> days = getMonthlyDates(date);
			List<String> uncheckedDates = new ArrayList<>();
			
			if(confirmation!=null) {
				for (int i = 0; i < confirmation.length(); i++) {
					char c = confirmation.charAt(i);
					if (c == 'x') {
						uncheckedDates.add(days.get(i));
					}
				}
			}
			return uncheckedDates;
		}
		
		public String getConfirmation(String id) {
			ConsumerList consumerList = new ConsumerList();
			String confirmation = null;
			
			try {
				consumerList = consumerListRepository.getReferenceById(id);
				confirmation = consumerList.getConfirmation();
			} catch (Exception exception) { }
			
			return confirmation;
		}
		
}
