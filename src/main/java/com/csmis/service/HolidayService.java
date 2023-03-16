package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.HolidayRepository;
import com.csmis.entity.Holiday;
import com.csmis.service_interface.HolidayServiceInterface;
@Service
public class HolidayService implements HolidayServiceInterface{
	@Autowired
	private HolidayRepository holidayRepository;
	@Override
	public void saveHolidays(List<Holiday> holidays) {
		// TODO Auto-generated method stub
		holidayRepository.saveAll(holidays);
	}

}
