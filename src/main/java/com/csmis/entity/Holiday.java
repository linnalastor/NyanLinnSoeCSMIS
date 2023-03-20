package com.csmis.entity;





import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity
@Table(name="holiday")
public class Holiday {
	@Id
	@Column(name="date")
	private Date date;

	@Column(name="description")
	private String description;

	public Holiday() {

	}

	public void HolidayDTO(String sdate, String description)throws ParseException {
		
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

	@Override
	public String toString() {
		return "holiday [date=" + date + ", description=" + description + "]";
	}



}
