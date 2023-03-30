package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.csmis.entity.PaymentVoucher;


public interface PaidVoucherRepository extends JpaRepository<PaymentVoucher, Integer> {
	@Query(value = "SELECT MAX(e.voucher_number)  FROM payment_voucher e",nativeQuery=true)
	public String getLastDate();


}
