package com.csmis.entity;

import java.sql.Timestamp;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "staff_detail")
public class StaffDetails {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "password")
	private String password;

	@Column(name = "description")
	private String description;

	@Column(name = "created_by")
	private String created_by;

	@Column(name = "last_updated_by")
	private String last_updated_by;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Column(name = "email_status")
	private String email_status;

	@Column(name = "enabled")
	private String enabled;

	public StaffDetails() {
		super();

	}

	public void StaffDetailsDTO(String id, String passwords, String description, String enabled, String created_by,
			String last_updated_by, Timestamp timestamp) throws ParseException {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.password = encoder.encode(passwords);
		this.id = id;
		this.description = description;
		this.enabled = enabled;
		this.created_by = created_by;
		this.last_updated_by = last_updated_by;
		this.getTimestamp();

	}

	public StaffDetails(String id, String password, String description, String created_by, String last_updated_by,
			Timestamp timestamp, String enabled) {
		super();
		this.id = id;
		this.password = password;
		this.description = description;

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

	public String getEmail_status() {
		return email_status;
	}

	public void setEmail_status(String email_status) {
		this.email_status = email_status;
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