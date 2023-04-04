package com.csmis.service;

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
}
