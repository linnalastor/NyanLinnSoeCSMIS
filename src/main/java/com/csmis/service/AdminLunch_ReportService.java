package com.csmis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.csmis.dao.LunchReportRepository;
import com.csmis.dao.StaffRepository;
import com.csmis.entity.Lunch_Report;
import com.csmis.service_interface.AdminReportServiceInterface;

@Service
public class AdminLunch_ReportService implements AdminReportServiceInterface{
	
	LunchReportRepository lunchReportRepository;
	
	StaffRepository staffRepository;
	
	public AdminLunch_ReportService(LunchReportRepository lunchReportRepository) {
		this.lunchReportRepository = lunchReportRepository;
	}

	@Override
	public List<Lunch_Report> getAllLunch_Reports() {
		return lunchReportRepository.findAll();
	}

	@Override
	public Lunch_Report getLunch_Report(String id) {
		return lunchReportRepository.getById(id);
	}

}
