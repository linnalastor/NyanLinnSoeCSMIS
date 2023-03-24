package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.InvoiceCashierRepository;
import com.csmis.entity.InvoiceCashier;
import com.csmis.service_interface.InvoiceCashierServiceInterface;

@Service
public class InvoiceCashierService  implements InvoiceCashierServiceInterface{
	private InvoiceCashierRepository invoiceCashierRepository;
	@Autowired
	public InvoiceCashierService(InvoiceCashierRepository theCashierRepository) {
		invoiceCashierRepository=theCashierRepository;
	}
	@Override
	public List<InvoiceCashier> findAll() {
		
		return invoiceCashierRepository.findAll();
	}
	@Override
	public void saveInvoiceCashiers(List<InvoiceCashier> invoiceCashiers) {
		 invoiceCashierRepository.saveAll(invoiceCashiers);
		
	}

	@Override
	public void save(InvoiceCashier theinvoiceCashier) {
		// TODO Auto-generated method stub
		invoiceCashierRepository.save(theinvoiceCashier);
	}

	
	


}
