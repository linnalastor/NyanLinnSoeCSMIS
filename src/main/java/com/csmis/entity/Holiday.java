package com.csmis.entity;





import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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

		String[] dateFormats = {"dd-MM-yy","d-MM-yy","dd-m-yy","d-m-yy"};
		for(String foramat: dateFormats) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(foramat);
            try {
            	this.date = LocalDate.parse(sdate,formatter);
            	break;
            } catch (Exception e) { }
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
