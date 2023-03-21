package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.CostRepository;
import com.csmis.entity.Cost;
import com.csmis.service_interface.CostServiceInterface;

@Service
public class CostService implements CostServiceInterface {

	@Autowired
	private CostRepository costRepository;
	
	@Override
	public void saveCosts(List<Cost> costs) {
		costRepository.saveAll(costs);
		
	}

}
