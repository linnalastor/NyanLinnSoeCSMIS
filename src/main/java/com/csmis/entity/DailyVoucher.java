package com.csmis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="daily_voucher")
public class DailyVoucher {
	@Id
	@Column(name="date")
	private Date date;

	@Column(name="catering_service_name")
	private String catering_service_name;

	@Column(name="register_head_count")
	private String register_head_count;

	@Column(name="actual_head_count")
	private String actual_head_count;

	@Column(name="amount")
	private int amount;

	public DailyVoucher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DailyVoucher(Date date, String catering_service_name, String register_head_count, String actual_head_count,
			int amount) {
		super();
		this.date = date;
		this.catering_service_name = catering_service_name;
		this.register_head_count = register_head_count;
		this.actual_head_count = actual_head_count;
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCatering_service_name() {
		return catering_service_name;
	}

	public void setCatering_service_name(String catering_service_name) {
		this.catering_service_name = catering_service_name;
	}

	public String getRegister_head_count() {
		return register_head_count;
	}

	public void setRegister_head_count(String register_head_count) {
		this.register_head_count = register_head_count;
	}

	public String getActual_head_count() {
		return actual_head_count;
	}

	public void setActual_head_count(String actual_head_count) {
		this.actual_head_count = actual_head_count;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}