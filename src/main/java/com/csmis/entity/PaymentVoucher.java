package com.csmis.entity;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="payment_voucher")
public class PaymentVoucher {
	@Id
	@Column(name="voucher_number")
	private String voucherNumber;


	@Column(name="cartering_service_name")
	private String carteringServiceName;

	@Column(name="invoice_start_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate invoiceStartDate;


	@Column(name="invoice_end_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate invoiceEndDate;

	@Column(name="cashier")
	private String cashier;


	@Column(name="received_by")
	private String receivedBy;

	@Column(name="approved_by")
	private String approvedBy;

	@Column(name="number_of_pax")
	private int numberOfPax;

	@Column(name="price")
	private int price;

	@Column(name="amount")
	private int amount;

	@Column(name="payment_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate paymentDate;


	@Column(name="payment_method")
	private String paymentMethod;


	public PaymentVoucher() {
		super();
		
	}


	public PaymentVoucher(String voucherNumber, String carteringServiceName, LocalDate invoiceStartDate,
			LocalDate invoiceEndDate, String cashier, String receivedBy, String approvedBy, int numberOfPax, int price,
			int amount, LocalDate paymentDate, String paymentMethod) {
		super();
		this.voucherNumber = voucherNumber;
		this.carteringServiceName = carteringServiceName;
		this.invoiceStartDate = invoiceStartDate;
		this.invoiceEndDate = invoiceEndDate;
		this.cashier = cashier;
		this.receivedBy = receivedBy;
		this.approvedBy = approvedBy;
		this.numberOfPax = numberOfPax;
		this.price = price;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.paymentMethod = paymentMethod;
	}


	public String getVoucherNumber() {
		return voucherNumber;
	}


	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}


	public String getCarteringServiceName() {
		return carteringServiceName;
	}


	public void setCarteringServiceName(String carteringServiceName) {
		this.carteringServiceName = carteringServiceName;
	}


	public LocalDate getInvoiceStartDate() {
		return invoiceStartDate;
	}


	public void setInvoiceStartDate(LocalDate invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}


	public LocalDate getInvoiceEndDate() {
		return invoiceEndDate;
	}


	public void setInvoiceEndDate(LocalDate invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}


	public String getCashier() {
		return cashier;
	}


	public void setCashier(String cashier) {
		this.cashier = cashier;
	}


	public String getReceivedBy() {
		return receivedBy;
	}


	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}


	public String getApprovedBy() {
		return approvedBy;
	}


	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}


	public int getNumberOfPax() {
		return numberOfPax;
	}


	public void setNumberOfPax(int numberOfPax) {
		this.numberOfPax = numberOfPax;
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


	public LocalDate getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}