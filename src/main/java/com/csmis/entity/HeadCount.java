package com.csmis.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="head_count")
public class HeadCount {
	@Id
	@Column(name="date")
	private LocalDate date;

	@Column(name="actual_head_count")
	private int actual_head_count;

	@Column(name="registered_head_count")
	private int registered_head_count;

	@Column(name="not_registered_head_count")
	private int not_registered_head_count;

	@Column(name="not_picked_head_count")
	private int not_picked_head_count;

	@Column(name="updated_by")
	private int updated_by;

	public HeadCount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HeadCount(LocalDate date, int actual_head_count, int registered_head_count, int not_registered_head_count,
			int not_picked_head_count, int updated_by) {
		super();
		this.date = date;
		this.actual_head_count = actual_head_count;
		this.registered_head_count = registered_head_count;
		this.not_registered_head_count = not_registered_head_count;
		this.not_picked_head_count = not_picked_head_count;
		this.updated_by = updated_by;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getActual_head_count() {
		return actual_head_count;
	}

	public void setActual_head_count(int actual_head_count) {
		this.actual_head_count = actual_head_count;
	}

	public int getRegistered_head_count() {
		return registered_head_count;
	}

	public void setRegistered_head_count(int registered_head_count) {
		this.registered_head_count = registered_head_count;
	}

	public int getNot_registered_head_count() {
		return not_registered_head_count;
	}

	public void setNot_registered_head_count(int not_registered_head_count) {
		this.not_registered_head_count = not_registered_head_count;
	}

	public int getNot_picked_head_count() {
		return not_picked_head_count;
	}

	public void setNot_picked_head_count(int not_picked_head_count) {
		this.not_picked_head_count = not_picked_head_count;
	}

	public int getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}

}
