package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Lunch_Report;

public interface OperatorReportServiceInterface{
	public void saveLunchReportMonthlyRegistration(Lunch_Report lunch_report);
	public Lunch_Report getLunch_Report(String id);
	public List<Lunch_Report> findAll_Monthly(String prefix_id);
	public void saveAll(List<Lunch_Report> lunch_report_list);
}
