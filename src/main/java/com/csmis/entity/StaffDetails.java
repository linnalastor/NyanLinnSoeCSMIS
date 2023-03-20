package com.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="staff_detail")
public class StaffDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private String id;

	@Column(name="password")
	private String password;


	@Column(name="description")
	private String description;


	@Column(name="division")
	private String division;


	@Column(name = "created_by")
	private String created_by;

	@Column(name="last_updated_by")
	private String last_updated_by;


	@Column(name = "timestamp")
	private Timestamp timestamp;
	
	@Column(name = "email_status")
	private String email_status;
	
	@Column(name = "enabled")
	private String enabled;
	


	public StaffDetails() {
		super();
		// TODO Auto-generated constructor stub
	}



	public StaffDetails(String id, String password, String description, String division, String created_by,
			String last_updated_by, Timestamp timestamp, String enabled) {
		super();
		this.id = id;
		this.password = password;
		this.description = description;
		this.division = division;
		this.created_by = created_by;
		this.last_updated_by = last_updated_by;
		this.timestamp = timestamp;
		this.enabled = enabled;
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



	public String getDivision() {
		return division;
	}



	public void setDivision(String division) {
		this.division = division;
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
	
	
}