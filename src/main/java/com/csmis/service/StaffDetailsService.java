package com.csmis.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.csmis.dao.StaffDetailsRepository;
import com.csmis.entity.StaffDetails;
import com.csmis.entity.StaffDetailsDTO;
import com.csmis.service_interface.StaffDetailsServiceInterface;

@Service
public class StaffDetailsService implements StaffDetailsServiceInterface {

	
	 @Autowired
	 private StaffDetailsRepository staffDetailsRepository;
	@Override
	public void saveStaffDetails(List<StaffDetailsDTO> staffDetails)throws ParseException {
		// TODO Auto-generated method stub
		StaffDetails staffDetail=new StaffDetails();
		for(StaffDetailsDTO hDTO:staffDetails) {
			staffDetail.StaffDetailsDTO(hDTO.getId(), hDTO.getPassword(), hDTO.getDescription(), hDTO.getEnabled(),hDTO.getCreated_by(),hDTO.getLast_updated_by(),hDTO.getTimestamp());
			System.out.println("hho"+staffDetail.getId());
			
			staffDetailsRepository.save(staffDetail);
			System.out.println("hho"+staffDetail.getId());
		}
		
		
	}
	public String encodedPassword(String pass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		pass=encoder.encode(pass);
		return pass;
		
	}
			@Override
			public List<StaffDetails> getStaffDetails() {
				
				
				return staffDetailsRepository.findAll();
			}
			
			
			
			@Override
			public StaffDetails getByID(String id) {
				StaffDetails staffDetails=new StaffDetails();
				// TODO Auto-generated method stub
				staffDetails=staffDetailsRepository.getByID(id);
				return staffDetails;
			}
			@Override
			public void save(StaffDetails staffDetails) {
				// TODO Auto-generated method stub
				staffDetailsRepository.save(staffDetails);
			}
		
		}
