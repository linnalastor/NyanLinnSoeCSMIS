package com.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="consumer_report")
public class Lunch_Report {
	@Id
	@Column(name="report_id")
	private String report_id;

	@Column(name="report_status")
	private String report_status;


	public Lunch_Report() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Lunch_Report(String report_id, String report_status) {
		super();
		this.report_id = report_id;
		this.report_status = report_status;
	}


	public String getReport_id() {
		return report_id;
	}


	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}


	public String getReport_status() {
		return report_status;
	}


	public void setReport_status(String report_status) {
		this.report_status = report_status;
	}

}