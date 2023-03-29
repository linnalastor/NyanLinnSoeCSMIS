package com.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.PaymentmethodRepository;
import com.csmis.entity.Paymentmethod;
import com.csmis.service_interface.PaymentmethodServiceInterface;
@Service
public class PaymentmethodService implements PaymentmethodServiceInterface  {

	 private PaymentmethodRepository paymentmethodRepository;
	 
	 @Autowired
	 public PaymentmethodService( PaymentmethodRepository thePaymentmethodRepository) {
		paymentmethodRepository=thePaymentmethodRepository;
	 }
	
	@Override
	public List<Paymentmethod> findAll() {
		// TODO Auto-generated method stub
		return paymentmethodRepository.findAll();
	}

	@Override
	public void savePaymentmethod(List<Paymentmethod> paymentmethods) {
		// TODO Auto-generated method stub
		paymentmethodRepository.saveAll(paymentmethods);
	}

	@Override
	public void save(Paymentmethod thePaymentmethod) {
		// TODO Auto-generated method stub
		paymentmethodRepository.save(thePaymentmethod);
	}

	@Override
	public Paymentmethod findByName(String name) {
		Optional<Paymentmethod> paymentmethod2 = paymentmethodRepository.findById(name);

		Paymentmethod paymentmethod = null;

		if (paymentmethod2.isPresent()) {
			paymentmethod = paymentmethod2.get();
		} else {
			// we didn't find the Paymentmethod
			throw new RuntimeException("Did not find Paymentmethod name - " + name);
		}

		return paymentmethod;		
	
	}

	@Override
	public void delete(Paymentmethod paymentmethod) {

		paymentmethodRepository.delete(paymentmethod);
		
	}

}
