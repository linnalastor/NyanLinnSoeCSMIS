package com.csmis.service_interface;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.csmis.entity.Holiday;
import com.csmis.entity.HolidayDTO;

public interface HolidayServiceInterface {
	
	public void saveHolidays(List<HolidayDTO> holidays) throws ParseException;

	public List<Holiday> findAll();

	public Holiday findByDate(Date date);
	public List<String> holiday_date_list();
}
