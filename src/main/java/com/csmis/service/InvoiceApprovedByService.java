package com.csmis.service;

import java.util.List;

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

}
