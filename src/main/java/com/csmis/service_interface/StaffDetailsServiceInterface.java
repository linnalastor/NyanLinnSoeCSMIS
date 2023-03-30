package com.csmis.service_interface;

import java.text.ParseException;
import java.util.List;

import com.csmis.entity.StaffDetails;
import com.csmis.entity.StaffDetailsDTO;

public interface StaffDetailsServiceInterface {


	public void saveStaffDetails(List<StaffDetailsDTO> staffDetails)throws ParseException;
	public List<StaffDetails> getStaffDetails();


	public StaffDetails getByID(String id);
	public void save(StaffDetails staffDetails);
	public StaffDetails getStaffDetailByID(String id);
	public List<StaffDetails> findByEmailStatus();



}
