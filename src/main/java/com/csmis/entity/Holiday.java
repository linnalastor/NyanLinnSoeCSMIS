package com.csmis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name="holiday")
public class Holiday {
	@Id
	@Column(name="date")
	@CsvBindByName(column="Date")
	private Date date;

	@Column(name="description")
	@CsvBindByName(column="Holiday")
	private String description;

	public Holiday() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Holiday(Date date, String description) {
		super();
		this.date = date;
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}