package com.csmis.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="daily_voucher")
public class DailyInvoice {
		@Id
		@Column(name="date")
		private LocalDate date;
		
		@Column(name="cartering_service_name")
		private String catheringServiceName;
		
		@Column(name="register_head_count")
		private int registerHeadCount;
		
		@Column(name="actual_head_count")
		private int actualHeadCount;
		
		
		
		@Column(name="staff_cost")
		private int staffCost;
		
		@Column(name="company_cost")
		private int companyCost;
		
		@Column(name="amount")
		private int amount;
		
		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public DailyInvoice() {
			
		}
		
		
		public DailyInvoice(LocalDate date, String catheringServiceName, int registerHeadCount, int actualHeadCount,
				int staffCost, int companyCost, int amount) {
			super();
			this.date = date;
			this.catheringServiceName = catheringServiceName;
			this.registerHeadCount = registerHeadCount;
			this.actualHeadCount = actualHeadCount;
			this.staffCost = staffCost;
			this.companyCost = companyCost;
			this.amount = amount;
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
		
		
		

}
