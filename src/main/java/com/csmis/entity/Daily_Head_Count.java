package com.csmis.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="head_count")
public class Daily_Head_Count {
	
	@Id
	@Column(name="date")
	private LocalDate date;
	
	@Column(name="registered_head_count")
	private int registered_head_count;
	
	@Column(name="actual_head_count")
	private int actual_head_count;
	
	@Column(name="non_register_head_count")
	private int non_register_head_count;
	
	@Column(name="not_picked_head_count")
	private int not_picked_head_count;

	public Daily_Head_Count() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Daily_Head_Count(LocalDate date, int registered_head_count, int actual_head_count, int non_register_head_count,
			int not_picked_head_count) {
		super();
		this.date = date;
		this.registered_head_count = registered_head_count;
		this.actual_head_count = actual_head_count;
		this.non_register_head_count = non_register_head_count;
		this.not_picked_head_count = not_picked_head_count;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getRegistered_head_count() {
		return registered_head_count;
	}

	public void setRegistered_head_count(int registered_head_count) {
		this.registered_head_count = registered_head_count;
	}

	public int getActual_head_count() {
		return actual_head_count;
	}

	public void setActual_head_count(int actual_head_count) {
		this.actual_head_count = actual_head_count;
	}

	public int getNon_register_head_count() {
		return non_register_head_count;
	}

	public void setNon_register_head_count(int non_register_head_count) {
		this.non_register_head_count = non_register_head_count;
	}

	public int getNot_picked_head_count() {
		return not_picked_head_count;
	}

	public void setNot_picked_head_count(int not_picked_head_count) {
		this.not_picked_head_count = not_picked_head_count;
	}
	
	
	
	

}
