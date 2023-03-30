package com.csmis.service;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.ConsumerListRepository;
import com.csmis.dao.HolidayRepository;
import com.csmis.entity.ConsumerList;
import com.csmis.entity.Holiday;
import com.csmis.entity.HolidayDTO;
import com.csmis.service_interface.HolidayServiceInterface;

@Service
public class HolidayService implements HolidayServiceInterface {

	@Autowired
	private HolidayRepository holidayRepository;
	@Autowired
	private ConsumerListRepository consumerListRepository;

	@Override
	public void saveHoliday(Holiday holiday) {
		holidayRepository.save(holiday);
	}

	@Override
	public void saveHolidays(List<HolidayDTO> holidays) throws ParseException {
		List<Holiday> holiday = new ArrayList<>();
		for (HolidayDTO hDTO : holidays) {
			Holiday h = new Holiday();
			h.HolidayDTO(hDTO.getDate(), hDTO.getDescription());
			holiday.add(h);
		}
		holidayRepository.saveAll(holiday);
		getUpdatedConsumerList(holiday);
	}

	@Override
	public List<Holiday> findAll() {
		List<Holiday> holidays = holidayRepository.findAll();
		return holidays;
	}

	@Override
	public void deleteAll() {
		holidayRepository.deleteAll();
	}

	@Override
	public Holiday findByDate(LocalDate date) {
		Holiday holiday = holidayRepository.findbyDate(date.toString());
		return holiday;
	}

	public List<String> getThisMonthHoliday(LocalDate date) {
		List<Holiday> holidays = holidayRepository.findAll();
		System.out.println(holidays);
		List<String> days = new ArrayList<>();

		for (Holiday holiday : holidays) {
			LocalDate thisdate = holiday.getDate();
			System.out.println(thisdate);
			if (thisdate.getMonthValue() == date.getMonthValue())
				days.add("" + thisdate.getDayOfMonth());
		}

		return days;
	}

	@Override
	public List<String> holiday_date_list() {
		return null;
	}

	public Date getDateOnly(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// Set the time fields to zero
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// Get the date portion as a Date object
		Date dateOnly = cal.getTime();
		return dateOnly;
	}

	public List<ConsumerList> getUpdatedConsumerList(List<Holiday> holidays) {

		List<ConsumerList> lunchRegisterList = consumerListRepository.findAll();
		List<ConsumerList> updatedList = new ArrayList<>();
		List<LocalDate> dates = new ArrayList<>();
		List<String> IDs = new ArrayList<>();
		List<Integer> days;
		int index=0;

		for (Holiday h : holidays) {
			dates.add(h.getDate());
		}

		for (LocalDate date : dates) {
			for (ConsumerList lunchList : lunchRegisterList) {
				if (Integer.parseInt(lunchList.getConsumer_information_id().substring(3, 7)) == date.getYear()) {
					if (Integer.parseInt(lunchList.getConsumer_information_id().substring(0, 2)) == date
							.getMonthValue()) {
						
						days = getMonthlyDays(date);
						System.out.println(days);

						boolean flag=false;
						for(int i=0; i<days.size();i++) {
							if(date.getDayOfMonth()==days.get(i)) {
								index=i;
								System.out.println(days.get(i)+"Match date ==> "+date.getDayOfMonth());
								flag=true;
								break;
							}
						}
						if(flag) {
							String confirmation = lunchList.getConfirmation();
							System.out.println("index ==> "+index);
							confirmation = confirmation.substring(0,index)+"h"+confirmation.substring(index+1);
							lunchList.setConfirmation(confirmation);
							
						}
					}
				}
				consumerListRepository.save(lunchList);
			}
		}

		return updatedList;

	}

	public List<Integer> getMonthlyDays(LocalDate day) {
		boolean isweekend = false;
		Integer temp;

		day = day.withDayOfMonth(1);
		LocalDate thisMonth = day;
		List<Integer> days = new ArrayList<>();

		// input first week of the month
		if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
			isweekend = true;

		if (!isweekend) {
			if (day.getDayOfWeek() == DayOfWeek.MONDAY) {
				days.add(day.getDayOfMonth());
				day = day.plusDays(1);
			}else days.add(0);

			if (day.getDayOfWeek() == DayOfWeek.TUESDAY) {
				days.add(day.getDayOfMonth());
				day = day.plusDays(1);
			}else days.add(0);

			if (day.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
				days.add(day.getDayOfMonth());
				day = day.plusDays(1);
			}else days.add(0);

			if (day.getDayOfWeek() == DayOfWeek.THURSDAY) {
				days.add(day.getDayOfMonth());
				day = day.plusDays(1);
			}else days.add(0);

			if (day.getDayOfWeek() == DayOfWeek.FRIDAY) {
				days.add(day.getDayOfMonth());
				day = day.plusDays(1);
			}else days.add(0);
		}
		// input other week days of the month
		while (day.getMonthValue() == thisMonth.getMonthValue()) {
			isweekend = false;
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				isweekend = true;
			if (!isweekend) {
				days.add( day.getDayOfMonth());
			}
			day = day.plusDays(1);
		}
		return days;

	}

}