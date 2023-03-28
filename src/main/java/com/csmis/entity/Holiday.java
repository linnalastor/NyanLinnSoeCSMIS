package com.csmis.entity;

<<<<<<< Updated upstream
import java.sql.Date;
=======




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
>>>>>>> Stashed changes

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "holiday")
public class Holiday {
	@Id
	@Column(name = "date")
	private LocalDate date;

	@Column(name = "description")
	private String description;

	public Holiday() {

	}

	public void HolidayDTO(String sdate, String description)throws ParseException {
<<<<<<< Updated upstream
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
		try{
			this.date=LocalDate.parse(sdate,formatter);
		}catch(Exception e) {
			formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
			this.date=LocalDate.parse(sdate,formatter);
		}
        
        this.description=description;
=======

		String[] formats = {"d/M/yyyy", "dd/M/yyyy", "d/MM/yyyy", "dd/MM/yyyy"};
		Date date = null;
		for (String format : formats) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(sdate);
        	    System.out.println("here");
                break;
            } catch (ParseException e) {
            }if (date != null) {
                System.out.println(date);
            } else {
                System.err.println("Error parsing date string: " + sdate);
            }
        }
		this.date = date;
		this.description = description;
>>>>>>> Stashed changes
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
