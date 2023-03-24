package com.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.InvoiceApprovedByRepository;
import com.csmis.entity.InvoiceApprovedBy;
import com.csmis.service_interface.InvoiceApprovedByServiceInterface;
@Service
public class InvoiceApprovedByService implements InvoiceApprovedByServiceInterface {

	 @Autowired
	 private InvoiceApprovedByRepository invoiceApprovedByRepository;
	
	@Override
	public void saveInvoiceApprovedBys(List<InvoiceApprovedBy> invoiceApprovedBys) {
		
		invoiceApprovedByRepository.saveAll(invoiceApprovedBys);
		
	}

	@Override
	public List<InvoiceApprovedBy> findAll() {
		// TODO Auto-generated method stub
		return invoiceApprovedByRepository.findAll();
	}

	@Override
	public InvoiceApprovedBy findByName(String name) {
Optional<InvoiceApprovedBy> invoiceApprovedBy2 = invoiceApprovedByRepository.findById(name);
		
InvoiceApprovedBy invoiceApprovedBy = null;
		
		if (invoiceApprovedBy2.isPresent()) {
			invoiceApprovedBy = invoiceApprovedBy2.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find invoiceApprovedBy name - " + name);
		}
		
		return invoiceApprovedBy;
	
	}

	@Override
	public void save(InvoiceApprovedBy theinvoiceApprovedBy) {
		
		invoiceApprovedByRepository.save(theinvoiceApprovedBy);
	}

}
