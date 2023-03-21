package com.csmis.service_interface;

import com.csmis.entity.Lunch_Report;

public interface OperatorReportServiceInterface{
	public void saveLunchReportMonthlyRegistration(Lunch_Report lunch_report);
	public Lunch_Report getLunch_Report(String id);
}
