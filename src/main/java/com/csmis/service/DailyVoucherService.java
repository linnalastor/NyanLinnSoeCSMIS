package com.csmis.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.csmis.dao.DailyVoucherRepository;
import com.csmis.entity.DailyVoucher;
import com.csmis.service_interface.DailyVoucherServiceInterface;

public class DailyVoucherService implements DailyVoucherServiceInterface{

	@Autowired
	DailyVoucherRepository dailyVoucherRepository;
	@Override
	public void save(DailyVoucher vouchers) {
		dailyVoucherRepository.save(vouchers);
	}
	
}
