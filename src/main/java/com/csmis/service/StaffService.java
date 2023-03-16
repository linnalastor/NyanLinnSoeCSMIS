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

}
