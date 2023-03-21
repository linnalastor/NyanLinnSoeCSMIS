package com.csmis.service;

import java.text.ParseException;
import java.util.List;

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
	public void saveHolidays(List<HolidayDTO> holidays) throws ParseException {
		Holiday h = new Holiday();
		for(HolidayDTO hDTO:holidays) {
			h.HolidayDTO(hDTO.getDate(),hDTO.getDescription());
			holidayRepository.save(h);
		}
		

	}

}
