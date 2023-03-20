package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Staff;

public interface StaffServiceInterface {

	public void saveStaffs(List<Staff> staffs);
	public List<Staff> findAll();
	public Staff findByID(String id);
}
