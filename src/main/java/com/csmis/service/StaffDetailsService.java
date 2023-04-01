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
			staffDetailsRepository.save(staffDetail);
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
				try {
					staffDetails=staffDetailsRepository.getByID(id);
				} catch (Exception e) {	}
				return staffDetails;
			}
			@Override
			public void save(StaffDetails staffDetails) {
				staffDetailsRepository.save(staffDetails);
			}
			@Override
			public StaffDetails getStaffDetailByID(String id) {
				StaffDetails staffDetails=new StaffDetails();
				try {
					staffDetails=staffDetailsRepository.getByID(id);
				} catch (Exception e) {	}
				return staffDetails;
			}
			@Override
			public List<StaffDetails> findByEmailStatus() {
				return staffDetailsRepository.getEmailStatus();
			}

		}
