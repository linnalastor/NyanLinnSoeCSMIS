package com.csmis.entity;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name="staff_detail")
public class StaffDetailsDTO {

	@Id
	@Column(name="id")
	@CsvBindByName(column="id")
	private String id;



	@Column(name="password")
	@CsvBindByName(column="password")
	private String password;

	@Column(name="description")
	@CsvBindByName(column="description")
	private String description;

//
//	@Column(name="division")
//	@CsvBindByName(column="division")
//	private String division;


	@Column(name = "created_by")
	@CsvBindByName(column="created_by")
	private String created_by;

	@Column(name="last_updated_by")
	@CsvBindByName(column="last_updated_by")
	private String last_updated_by;


	@Column(name = "timestamp")
	@CsvBindByName(column="timestamp")
	private Timestamp timestamp;
	
	@Column(name = "enabled")
	@CsvBindByName(column="enabled")
    private String enabled;

	public String getEnabled() {
		return enabled;
	}


	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}


	public StaffDetailsDTO() {
		}



	public StaffDetailsDTO(String id, String password, String description, String enabled, String created_by, String last_updated_by, Timestamp timestamp) {
		super();
		this.id = id;
		this.password = password;
		this.description = description;
		this.enabled = enabled;
		this.created_by=created_by;
		this.last_updated_by=last_updated_by;
		this.timestamp=timestamp;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


//	public String getDivision() {
//		return division;
//	}
//
//
//	public void setDivision(String division) {
//		this.division = division;
//	}
//

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


//	@Override
//	public String toString() {
//		return "StaffDetails [id=" + id + ", description=" + description + ", password=" + password + ", division="
//				+ division + ", created_by=" + created_by + ", last_updated_by=" + last_updated_by + ", timestamp="
//				+ timestamp + "]";
//	}




}
