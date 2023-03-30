package com.csmis.DTO;

import java.time.LocalDate;

public class DailyInvoiceDTO {
	private LocalDate date;
	private String catheringServiceName;
	private int registerHeadCount;
	private int actualHeadCount;
	private int staffCost;
	private int companyCost;
	private int difference;
	private int Camount;
	private int Samount;
	private int numOfPax;
	public int getNumOfPax() {
		return numOfPax;
	}

	public void setNumOfPax(int numOfPax) {
		this.numOfPax = numOfPax;
	}

	public DailyInvoiceDTO() {
		super();
	}

	public int getCamount() {
		return Camount;
	}
	public void setCamount(int camount) {
		Camount = camount;
	}
	public int getSamount() {
		return Samount;
	}
	public void setSamount(int samount) {
		Samount = samount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getCatheringServiceName() {
		return catheringServiceName;
	}
	public void setCatheringServiceName(String catheringServiceName) {
		this.catheringServiceName = catheringServiceName;
	}
	public int getRegisterHeadCount() {
		return registerHeadCount;
	}
	public void setRegisterHeadCount(int registerHeadCount) {
		this.registerHeadCount = registerHeadCount;
	}
	public int getActualHeadCount() {
		return actualHeadCount;
	}
	public void setActualHeadCount(int actualHeadCount) {
		this.actualHeadCount = actualHeadCount;
	}
	public int getStaffCost() {
		return staffCost;
	}
	public void setStaffCost(int staffCost) {
		this.staffCost = staffCost;
	}
	public int getCompanyCost() {
		return companyCost;
	}
	public void setCompanyCost(int companyCost) {
		this.companyCost = companyCost;
	}
	public int getDifference() {
		return difference;
	}
	public void setDifference(int difference) {
		this.difference = difference;
	}


}
