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
		List<String> days = new ArrayList<>();

		for (Holiday holiday : holidays) {
			LocalDate thisdate = holiday.getDate();
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

	public void getUpdatedConsumerList(List<Holiday> holidays) {

		List<LocalDate> dates = new ArrayList<>();
		List<Integer> days;
		int index=0;
		

		List<ConsumerList> lunchRegisterList=null;
		try {
			lunchRegisterList = consumerListRepository.findAll();
		} catch (Exception e) {	}

		for (Holiday h : holidays) {
			dates.add(h.getDate());
		}

		for (LocalDate date : dates) {
			for (ConsumerList lunchList : lunchRegisterList) {

				String temp="";
				String confirmation = lunchList.getConfirmation();
				for(int i=0;i<confirmation.length();i++) {
					if(confirmation.charAt(i)=='h')
						try {
							temp = confirmation.substring(i+1);
						} catch (Exception e) { }
						confirmation = confirmation.substring(0,i)+"x"+temp;
						lunchList.setConfirmation(confirmation);
						
				}
				if (Integer.parseInt(lunchList.getConsumer_information_id().substring(3, 7)) == date.getYear()) {
					if (Integer.parseInt(lunchList.getConsumer_information_id().substring(0, 2)) == date
							.getMonthValue()) {
						
						days = getMonthlyDays(date);

						boolean flag=false;
						for(int i=0; i<days.size();i++) {
							if(date.getDayOfMonth()==days.get(i)) {
								index=i;
								flag=true;
								break;
							}
						}
						if(flag) {
							try {
								temp = confirmation.substring(index+1);
							} catch (Exception e) { }
							confirmation = confirmation.substring(0,index)+"h"+temp;
							lunchList.setConfirmation(confirmation);
						}
					}
				}
				consumerListRepository.save(lunchList);
			}
		}
	}

	public List<Integer> getMonthlyDays(LocalDate day) {
		boolean isweekend = false;

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