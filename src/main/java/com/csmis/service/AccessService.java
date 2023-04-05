package com.csmis.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.AccessRepository;
import com.csmis.entity.Access;
import com.csmis.service_interface.AccessServiceInterface;
@Service
public class AccessService implements AccessServiceInterface {
	@Autowired
	private AccessRepository accessRepository;

	@Override
	public void saveAccess(Access access) {

		accessRepository.save(access);
	}
	@Override
	public boolean checkWeeklyAccess() {
		boolean status = false;
		Access access = null;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = null;
		LocalDateTime end = null;
		try {
			access = accessRepository.getAccessById(1);
			start = access.getStart();
			end = access.getEnd();
			
			if(start.getDayOfWeek().getValue()<=now.getDayOfWeek().getValue()) {
				status = true;
			}
			if(end.getDayOfWeek().getValue()>now.getDayOfWeek().getValue()) {
				if(end.getHour()<now.getHour()) status =false;
			}else status = false;
		} catch (Exception e) {	}
		return status;
	}
	
	@Override
	public boolean checkMonthlyAccess() {
		boolean status = false;
		Access access = null;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = null;
		LocalDateTime end = null;
		try {
			access = accessRepository.getAccessById(1);
			start = LocalDateTime.now().withDayOfMonth(1).plusMonths(1).minusDays(1);
			while(start.getDayOfWeek()!=access.getStart().getDayOfWeek()) {
				start=start.minusDays(1);
			}
			end = access.getEnd();
			int difference = start.compareTo(now);
			if(difference < 0)  status = true;
			if(end.getDayOfWeek().getValue()>now.getDayOfWeek().getValue()) {
				if(end.getHour()<now.getHour()) status =false;
			}else status = false;
		} catch (Exception e) {	}

		return status;
	}
}
