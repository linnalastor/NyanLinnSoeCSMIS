package com.csmis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.HolidayRepository;
import com.csmis.entity.Holiday;
import com.csmis.entity.HolidayDTO;
import com.csmis.service_interface.HolidayServiceInterface;
@Service
public class HolidayService implements HolidayServiceInterface {

	@Autowired
	private HolidayRepository holidayRepository;
	
	@Override
	public void saveHoliday(Holiday holiday) {
		holidayRepository.save(holiday);
	}
	
	@Override
	public void saveHolidays(List<HolidayDTO> holidays) throws ParseException {

		List<Holiday> holiday=new ArrayList<>();
		for(HolidayDTO hDTO:holidays) {
			System.out.println(hDTO.getDate());
			Holiday h = new Holiday();
			h.HolidayDTO(hDTO.getDate(),hDTO.getDescription());
			holiday.add(h);
		}
		holidayRepository.saveAll(holiday);
	}

	@Override
	public List<Holiday> findAll() {
		List<Holiday> holidays=  holidayRepository.findAll();
		return holidays;
	}

	@Override
	public void deleteAll() {
		holidayRepository.deleteAll();
	}

	@Override
	public Holiday findByDate(LocalDate date) {
		System.out.println(date.toString());
		Holiday holiday=holidayRepository.findbyDate(date.toString());
		System.out.println("Holiday==>"+holiday.getDate());
	    return holiday;
	}
	
	
	public List<String> getThisMonthHoliday(LocalDate date){
		List<Holiday> holidays = holidayRepository.findAll();
		System.out.println(holidays);
		List<String> days = new ArrayList<>();
		
		for(Holiday holiday:holidays) {
			LocalDate thisdate=holiday.getDate();
			System.out.println(thisdate);
			if(thisdate.getMonthValue()==date.getMonthValue()) days.add(""+thisdate.getDayOfMonth());
		}
		
		return days;
	}
	
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

}