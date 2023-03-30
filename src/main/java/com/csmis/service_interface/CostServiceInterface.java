package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Cost;

public interface CostServiceInterface {

	public void saveCosts(List<Cost> costs);

	public Cost findByPayer(String payer);

	public void save(Cost thecost);

	public List<Cost> findAll();


}
