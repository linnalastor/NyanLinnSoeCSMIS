package com.csmis.entity;

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

	@Column(name="confirmation")
	private String confirmation;


	public ConsumerList() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ConsumerList(String consumer_information_id, String confirmation) {
		super();
		this.consumer_information_id = consumer_information_id;
		this.confirmation = confirmation;
	}


	public String getConsumer_information_id() {
		return consumer_information_id;
	}


	public void setConsumer_information_id(String consumer_information_id) {
		this.consumer_information_id = consumer_information_id;
	}


	public String getConfirmation() {
		return confirmation;
	}


	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}


	
	
}