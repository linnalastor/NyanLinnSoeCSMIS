package com.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name="admin")
public class Admin {
	@Id
	@Column(name="username")
	@CsvBindByName(column="username")
	private String username;


	@Column(name="password")
	@CsvBindByName(column="password")
	private String password;


	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Admin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
}