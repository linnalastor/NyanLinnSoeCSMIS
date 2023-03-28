package com.csmis.entity;






import com.opencsv.bean.CsvBindByName;

public class HolidayDTO {
	@CsvBindByName(column="Date")
	private String date;

	@CsvBindByName(column="Holiday")
	private String description;

	public HolidayDTO() {

	}
	
	public HolidayDTO(String date, String description) {
		super();
		this.date = date;
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
