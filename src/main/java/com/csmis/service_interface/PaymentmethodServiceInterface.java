package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Paymentmethod;

public interface PaymentmethodServiceInterface {

	public List<Paymentmethod> findAll();

	public void savePaymentmethod(List<Paymentmethod> paymentmethods);

	public void save(Paymentmethod thePaymentmethod);

	public Paymentmethod findByName(String name);

	public void delete(Paymentmethod paymentmethod);



}
