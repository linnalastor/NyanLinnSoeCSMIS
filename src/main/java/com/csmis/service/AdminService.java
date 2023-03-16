package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.AdminRepository;
import com.csmis.entity.Admin;
import com.csmis.service_interface.AdminServiceInterface;

@Service
public class AdminService implements AdminServiceInterface{
	@Autowired
	private AdminRepository adminReopsitory;
	@Override
	public void saveAdmin(List<Admin> admins) {
		// TODO Auto-generated method stub
		adminReopsitory.saveAll(admins);
	}

}
