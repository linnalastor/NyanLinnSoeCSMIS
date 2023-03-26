package com.csmis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.csmis.entity.Lunch_Report;

public interface LunchReportRepository extends JpaRepository<Lunch_Report, String>{
	@Query(value="Select * from Consumer_report where report_id=?1",nativeQuery = true)
	public Lunch_Report getLunchReport_By_ID(String id);
	
	@Query(value="Select * from Consumer_report where report_id Like %?1%",nativeQuery = true)
	public List<Lunch_Report> findAll_Monthly(String prefix_id);
}
