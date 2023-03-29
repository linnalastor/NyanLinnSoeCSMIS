package com.csmis.service_interface;

import java.time.LocalDate;
import java.util.List;
import com.csmis.entity.PaymentVoucher;

public interface PaidVoucherServiceInterface {
	public List<PaymentVoucher> findAll();
	public void save(PaymentVoucher thePaymentVoucher);
	public String getLastDate();
	public LocalDate getFirstDate(); 
}
