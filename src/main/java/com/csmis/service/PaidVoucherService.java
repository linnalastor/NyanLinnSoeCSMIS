package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.PaidVoucherRepository;
import com.csmis.entity.PaymentVoucher;
import com.csmis.service_interface.PaidVoucherServiceInterface;

@Service
public class PaidVoucherService implements PaidVoucherServiceInterface {
	private PaidVoucherRepository paidVoucherRepository;
	
	@Autowired
	public PaidVoucherService(PaidVoucherRepository thePaidVoucherRepository) {
		paidVoucherRepository=thePaidVoucherRepository;
	}

	@Override
	public void save(PaymentVoucher thePaymentVoucher) {
		
		paidVoucherRepository.save(thePaymentVoucher);
		
	}

	@Override
	public List<PaymentVoucher> findAll() {
		
		return paidVoucherRepository.findAll();
	}

	@Override
	public String getLastDate() {
		
		return paidVoucherRepository.getLastDate();
	}

	

	

	

}
