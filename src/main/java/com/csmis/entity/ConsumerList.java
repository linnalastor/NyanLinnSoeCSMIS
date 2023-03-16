package com.csmis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="consumer_list")
public class ConsumerList {
	@Id
	@Column(name="consumer_information_id")
	private String consumer_information_id;


	@Column(name = "staff_id")
	private String staff_id;

	@Column(name="confirmation")
	private String confirmation;


	@Column(name="date")
	private Date date;


	public ConsumerList() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ConsumerList(String consumer_information_id, String staff_id, String confirmation, Date date) {
		super();
		this.consumer_information_id = consumer_information_id;
		this.staff_id = staff_id;
		this.confirmation = confirmation;
		this.date = date;
	}


	public String getConsumer_information_id() {
		return consumer_information_id;
	}


	public void setConsumer_information_id(String consumer_information_id) {
		this.consumer_information_id = consumer_information_id;
	}


	public String getStaff_id() {
		return staff_id;
	}


	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}


	public String getConfirmation() {
		return confirmation;
	}


	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	
	
}