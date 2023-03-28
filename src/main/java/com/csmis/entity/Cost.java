package com.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cost")
public class Cost {
	@Id
	@Column(name="payer")
	private String payer;


	@Column(name="cost")
	private int cost;


	public Cost() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Cost(String payer, int cost) {
		super();
		this.payer = payer;
		this.cost = cost;
	}


	public String getPayer() {
		return payer;
	}


	public void setPayer(String payer) {
		this.payer = payer;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}

}