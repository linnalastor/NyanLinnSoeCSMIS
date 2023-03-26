package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.ConsumerList;

public interface OperatorRegisterServiceInterface{
	public void saveConsumerMonthlyRegistration(ConsumerList consumerList);
	public ConsumerList getLunchRegistration_by_ID(String id);
	public List<ConsumerList> getAllLunchPlan_by_MonthYear(String prefix);
}
