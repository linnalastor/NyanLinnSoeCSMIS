package com.csmis.service;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Cost findByPayer(String payer) {
	Optional<Cost> costt = costRepository.findById(payer);

		Cost cost = null;

		if (costt.isPresent()) {
			cost = costt.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find Cost payer - " + payer);
		}

		return cost;

	}



	@Override
	public void save(Cost thecost) {
	 costRepository.save(thecost);

	}



	@Override
	public List<Cost> findAll() {
		// TODO Auto-generated method stub
		return costRepository.findAll();
	}


}
