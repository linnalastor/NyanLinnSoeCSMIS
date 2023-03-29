package com.csmis.service;

import org.springframework.stereotype.Service;

import com.csmis.dao.EmailStatusRepository;
import com.csmis.entity.StaffDetails;
import com.csmis.service_interface.EmailStatusServiceInterface;

@Service
public class EmailStatusService implements EmailStatusServiceInterface{
	private EmailStatusRepository emailStatusRepository;
	
	public EmailStatusService(EmailStatusRepository theEmailStatusRepository) {
		emailStatusRepository = theEmailStatusRepository;
	}

	@Override
	public void save(StaffDetails staffDetails) {
		// TODO Auto-generated method stub
		emailStatusRepository.save(staffDetails);
	}

}
