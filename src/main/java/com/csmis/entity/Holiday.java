package com.csmis.entity;





import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name="holiday")
public class Holiday {
	@Id
	@Column(name="date")
	private LocalDate date;

	@Column(name="description")
	private String description;

	public Holiday() {

	}

	public void HolidayDTO(String sdate, String description)throws ParseException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
            	this.date = LocalDate.parse(sdate,formatter);
            } catch (Exception e) {
            	formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            	this.date = LocalDate.parse(sdate,formatter);
            }
		this.description = description;
	}

	public Holiday(LocalDate date, String description) {
		super();
		this.date = date;
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "holiday [date=" + date + ", description=" + description + "]";
	}



}
