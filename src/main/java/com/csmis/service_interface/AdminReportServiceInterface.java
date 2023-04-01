package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Lunch_Report;

public interface AdminReportServiceInterface {
	List<Lunch_Report> getAllLunch_Reports();

	Lunch_Report getLunch_Report(String id);

}
