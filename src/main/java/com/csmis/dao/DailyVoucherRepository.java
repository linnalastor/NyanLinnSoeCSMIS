package com.csmis.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmis.entity.DailyVoucher;

@Repository
public interface DailyVoucherRepository  extends JpaRepository<DailyVoucher, String>{
	@Query(value="Select date from Daily_voucher LIMIT 1",nativeQuery = true)
	public LocalDate getFirstDate();
}
