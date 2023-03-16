package com.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resturant")
public class Resturant {
	@Id
	@Column(name="name")
	private String name;

	@Column(name="address")
	private String address;


	@Column(name="phone_number")
	private String phone_number;


	public Resturant() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Resturant(String name, String address, String phone_number) {
		super();
		this.name = name;
		this.address = address;
		this.phone_number = phone_number;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
}