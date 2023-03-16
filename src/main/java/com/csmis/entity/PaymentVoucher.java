package com.csmis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payment_voucher")
public class PaymentVoucher {
	@Id
	@Column(name="voucher_number")
	private String voucher_number;


	@Column(name="cartering_service_name")
	private String cartering_service_name;

	@Column(name="invoice_start_date")
	private Date invoice_start_date;


	@Column(name="invoice_end_date")
	private Date invoice_end_date;

	@Column(name="cashier")
	private String cashier;


	@Column(name="received_by")
	private String received_by;

	@Column(name="approved_by")
	private String approved_by;

	@Column(name="number_of_pax")
	private int number_of_pax;

	@Column(name="price")
	private int price;

	@Column(name="amount")
	private int amount;


	@Column(name="payment_date")
	private Date payment_date;


	@Column(name="payment_method")
	private String payment_method;


	public PaymentVoucher() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PaymentVoucher(String voucher_number, String cartering_service_name, Date invoice_start_date,
			Date invoice_end_date, String cashier, String received_by, String approved_by, int number_of_pax, int price,
			int amount, Date payment_date, String payment_method) {
		super();
		this.voucher_number = voucher_number;
		this.cartering_service_name = cartering_service_name;
		this.invoice_start_date = invoice_start_date;
		this.invoice_end_date = invoice_end_date;
		this.cashier = cashier;
		this.received_by = received_by;
		this.approved_by = approved_by;
		this.number_of_pax = number_of_pax;
		this.price = price;
		this.amount = amount;
		this.payment_date = payment_date;
		this.payment_method = payment_method;
	}


	public String getVoucher_number() {
		return voucher_number;
	}


	public void setVoucher_number(String voucher_number) {
		this.voucher_number = voucher_number;
	}


	public String getCartering_service_name() {
		return cartering_service_name;
	}


	public void setCartering_service_name(String cartering_service_name) {
		this.cartering_service_name = cartering_service_name;
	}


	public Date getInvoice_start_date() {
		return invoice_start_date;
	}


	public void setInvoice_start_date(Date invoice_start_date) {
		this.invoice_start_date = invoice_start_date;
	}


	public Date getInvoice_end_date() {
		return invoice_end_date;
	}


	public void setInvoice_end_date(Date invoice_end_date) {
		this.invoice_end_date = invoice_end_date;
	}


	public String getCashier() {
		return cashier;
	}


	public void setCashier(String cashier) {
		this.cashier = cashier;
	}


	public String getReceived_by() {
		return received_by;
	}


	public void setReceived_by(String received_by) {
		this.received_by = received_by;
	}


	public String getApproved_by() {
		return approved_by;
	}


	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}


	public int getNumber_of_pax() {
		return number_of_pax;
	}


	public void setNumber_of_pax(int number_of_pax) {
		this.number_of_pax = number_of_pax;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public Date getPayment_date() {
		return payment_date;
	}


	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}


	public String getPayment_method() {
		return payment_method;
	}


	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	
}