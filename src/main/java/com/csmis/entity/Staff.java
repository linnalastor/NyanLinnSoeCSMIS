package com.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name="staff")
public class Staff {

	@Id
	@Column(name="id")
	@CsvBindByName(column="Staff ID")
	private String id;


	@Column(name="door_access_id")
	@CsvBindByName(column="DoorLogNo.")
	private String door_access_id;

	@Column(name="name")
	@CsvBindByName(column="Name")
	private String name;

	@Column(name="division")
	@CsvBindByName(column="Division")
	private String division;

	@Column(name="department")
	@CsvBindByName(column="Dept")
	private String department;

	@Column(name="team")
	@CsvBindByName(column="Team")
	private String team;

	@Column(name="email")
	@CsvBindByName(column="Email")
	private String email;

	@Column(name="status")
	@CsvBindByName(column="Status")
	private String status;

	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Staff(String id, String door_access_id, String name, String division, String department, String team,
			String email, String status) {
		super();
		this.id = id;
		this.door_access_id = door_access_id;
		this.name = name;
		this.division = division;
		this.department = department;
		this.team = team;
		this.email = email;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDoor_access_id() {
		return door_access_id;
	}

	public void setDoor_access_id(String door_access_id) {
		this.door_access_id = door_access_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}