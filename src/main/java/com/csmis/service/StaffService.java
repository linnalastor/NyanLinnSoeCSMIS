package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.StaffRepository;
import com.csmis.entity.Staff;
import com.csmis.service_interface.StaffServiceInterface;

@Service
public class StaffService implements StaffServiceInterface{

	@Autowired
	private StaffRepository staffRepository;

	@Override
	public void saveStaffs(List<Staff> staffs) {
		staffRepository.saveAll(staffs);

	}

	@Override
	public List<Staff> findAll() {
		return staffRepository.findAll();
	}

	@Override
	public Staff findByID(String id) {
		return staffRepository.getById(id);
	}

	@Override
	public String findID_By_DoorAccessID(String id) {
		return staffRepository.getByDoor_Access_ID(id);
	}

	@Override
	public void save(Staff thestaff) {
		staffRepository.save(thestaff);
	}

	@Override
	public Staff findByEmail(String email) {
		Staff staff = new Staff();
		staff=staffRepository.findByEmail(email);
		return staff;
	}



}
