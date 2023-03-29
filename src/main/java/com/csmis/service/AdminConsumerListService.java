package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.ConsumerListRepository;
import com.csmis.dao.StaffRepository;
import com.csmis.entity.ConsumerList;
import com.csmis.service_interface.AdminConsumerListServiceInterface;

@Service
public class AdminConsumerListService implements AdminConsumerListServiceInterface{

	ConsumerListRepository consumerListRepository;

	StaffRepository staffRepository;


	@Autowired
	public AdminConsumerListService(ConsumerListRepository consumerListRepository) {

		this.consumerListRepository = consumerListRepository;
	}

	@Override
	public List<ConsumerList> getAllConsumerLists() {

		return consumerListRepository.findAll();
	}


	@Override
	public ConsumerList getConsumerList(String id) {
		return consumerListRepository.getById(id);
	}




}
