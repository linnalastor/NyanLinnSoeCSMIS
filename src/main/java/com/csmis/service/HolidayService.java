package com.csmis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
	public void saveHolidays(List<HolidayDTO> holidays) throws ParseException {
		Holiday h = new Holiday();
		for(HolidayDTO hDTO:holidays) {
			h.HolidayDTO(hDTO.getDate(),hDTO.getDescription());
			holidayRepository.save(h);
		}
		

	}

	@Override
	public List<Holiday> findAll() {
		// TODO Auto-generated method stub
		return holidayRepository.findAll();
	}

	
//
//	@Override
//	public Holiday findByDate(Date date) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//	    String dateString = formatter.format(date);
//		Optional<Holiday> holiday2 = holidayRepository.findById(dateString);
//		
//		Holiday holiday1 = null;
//		
//		if (holiday2.isPresent()) {
//			holiday1 = holiday2.get();
//		}
//		else {
//			// we didn't find the employee
//			throw new RuntimeException("Did not find Holiday date - " + date);
//		}
//		
//		return holiday1;
//	
//	}
	@Override
	public Holiday findByDate(Date date) {
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String dateString = localDate.format(formatter);
	    Optional<Holiday> holiday2 = holidayRepository.findById(dateString);
	    
	    Holiday holiday1 = null;
	    
	    if (holiday2.isPresent()) {
	        holiday1 = holiday2.get();
	    }
	    else {
	        // we didn't find the employee
	        throw new RuntimeException("Did not find Holiday date - " + dateString);
	    }
	    
	    return holiday1;
	}

}
