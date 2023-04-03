package com.csmis.entity;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

public class StaffDetailsDTO {

	private String id;

	private String password;

	private String description;

	private String created_by;

	private String last_updated_by;

	private Timestamp timestamp;

    private String enabled;
	
	private String email_status;

	public StaffDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StaffDetailsDTO(String id, String password, String description, String created_by, String last_updated_by,
			Timestamp timestamp, String enabled, String email_status) {
		super();
		this.id = id;
		this.password = password;
		this.description = description;
		this.created_by = created_by;
		this.last_updated_by = last_updated_by;
		this.timestamp = timestamp;
		this.enabled = enabled;
		this.email_status = email_status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getEmail_status() {
		return email_status;
	}

	public void setEmail_status(String email_status) {
		this.email_status = email_status;
	}

}