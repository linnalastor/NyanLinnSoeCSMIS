package com.csmis.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="access")
public class Access {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(name="start")
	private LocalDateTime start;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(name="end")
	private LocalDateTime end;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	public Access(int id, LocalDateTime start, LocalDateTime end) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
	}
	public Access() {
		super();
		// TODO Auto-generated constructor stub
	}






}