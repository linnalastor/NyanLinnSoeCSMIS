package com.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name="invoice_approved_by")
public class InvoiceApprovedBy {

	@Id
	@Column(name="name")
	@CsvBindByName(column="Name")
	public String name;

	public InvoiceApprovedBy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceApprovedBy(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}