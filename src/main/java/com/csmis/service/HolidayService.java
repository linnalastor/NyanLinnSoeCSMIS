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
	@Autowired
	DateService dateService;

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
		List<String> days;
		int index = 0;

		for (Holiday h : holidays) {
			dates.add(h.getDate());
		}
		System.out.println("Holidays==>" + dates);

		List<ConsumerList> lunchRegisterList = new ArrayList<>();
		try {
			lunchRegisterList.addAll(consumerListRepository.findAll());
		} catch (Exception e1) {
		}
		for (ConsumerList lunchList : lunchRegisterList) {
			String confirmation = lunchList.getConfirmation();
			System.out.println("confirmation ==> " + confirmation);
			
			for (int i = 0; i < confirmation.length(); i++) {
			String temp = "";
			if (confirmation.charAt(i) == 'h') {
				try {
					temp = confirmation.substring(i + 1);
				} catch (Exception e) {
				}
				confirmation = confirmation.substring(0, i) + "x" + temp;
				lunchList.setConfirmation(confirmation);
				System.out.println("x substituded confirmation==>" + confirmation);
			}
		}
			for (LocalDate date : dates) {
				System.out.println("++++++++++++ dates ++++++++++++++++" + date);
				
				String temp = "";
				if (Integer.parseInt(lunchList.getConsumer_information_id().substring(3, 7)) == date.getYear()) {
					if (Integer.parseInt(lunchList.getConsumer_information_id().substring(0, 2)) == date
							.getMonthValue()) {
						days = dateService.getMonthlyDates(date);
						for(String day : days) 
							if(day.equals("00")) days.remove("00");
						String tempMonth = ""+date.getDayOfMonth();
						if(tempMonth.length()<2) tempMonth="0"+tempMonth;

						boolean flag = false;
						for (int i = 0; i < days.size(); i++) {
							if (days.get(i).equals(tempMonth)) {
								index = i;
								flag = true;
								break;
							}
						}
						if (flag) {
							try {
								temp = confirmation.substring(index + 1);
							} catch (Exception e) {
							}
							confirmation = confirmation.substring(0, index) + "h" + temp;
							lunchList.setConfirmation(confirmation);
						}
						System.out.println("holiday updated confirmation==> " + confirmation);
						consumerListRepository.save(lunchList);
					}
				}
			}
		}
	}

}