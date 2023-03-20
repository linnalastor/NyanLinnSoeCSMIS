package com.csmis.service_interface;

import java.text.ParseException;
import java.util.List;

import com.csmis.entity.HolidayDTO;

public interface HolidayServiceInterface {
	
	public void saveHolidays(List<HolidayDTO> holidays) throws ParseException;
}
