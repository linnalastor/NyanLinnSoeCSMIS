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
	private int register_head_count;

	@Column(name="actual_head_count")
<<<<<<< Updated upstream
	private int actual_head_count;
	
=======
	private String actual_head_count;

>>>>>>> Stashed changes
	@Column(name="staff_cost")
	private int staff_cost;


	@Column(name="company_cost")
	private int company_cost;


	@Column(name="amount")
	private int amount;

	public DailyVoucher() {
		super();
	}

	public DailyVoucher(Date date, String catering_service_name, int register_head_count, int actual_head_count,
			int staff_cost, int company_cost, int amount) {
		super();
		this.date = date;
		this.catering_service_name = catering_service_name;
		this.register_head_count = register_head_count;
		this.actual_head_count = actual_head_count;
		this.staff_cost = staff_cost;
		this.company_cost = company_cost;
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

	public int getRegister_head_count() {
		return register_head_count;
	}

	public void setRegister_head_count(int register_head_count) {
		this.register_head_count = register_head_count;
	}

	public int getActual_head_count() {
		return actual_head_count;
	}

	public void setActual_head_count(int actual_head_count) {
		this.actual_head_count = actual_head_count;
	}

	public int getStaff_cost() {
		return staff_cost;
	}

	public void setStaff_cost(int staff_cost) {
		this.staff_cost = staff_cost;
	}

	public int getCompany_cost() {
		return company_cost;
	}

	public void setCompany_cost(int company_cost) {
		this.company_cost = company_cost;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}