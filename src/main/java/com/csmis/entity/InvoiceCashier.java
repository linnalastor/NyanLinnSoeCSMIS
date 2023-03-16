package com.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="incoive_cashier")
public class InvoiceCashier {
	@Id
	@Column(name="name")
	public String name;

	public InvoiceCashier(String name) {
		super();
		this.name = name;
	}

	public InvoiceCashier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}