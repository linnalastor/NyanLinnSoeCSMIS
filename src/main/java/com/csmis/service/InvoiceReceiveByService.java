package com.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.InvoiceReceiveByRepository;
import com.csmis.entity.InvoiceReceiveBy;
import com.csmis.entity.Restaurant;
import com.csmis.service_interface.InvoiceReceiveByServiceInterface;

@Service
public class InvoiceReceiveByService implements InvoiceReceiveByServiceInterface
{
        @Autowired
        private InvoiceReceiveByRepository invoiceReceiveByRepository;
        public InvoiceReceiveByService(InvoiceReceiveByRepository theInvoiceReceiveByRepository) {
        	invoiceReceiveByRepository=theInvoiceReceiveByRepository;
        }
	@Override
	public void saveInvoiceReceiveBys(List<InvoiceReceiveBy> invoiceReceiveBys) {
	
		invoiceReceiveByRepository.saveAll(invoiceReceiveBys);
		
	}
	@Override
	public List<InvoiceReceiveBy> findAll() {

		// TODO Auto-generated method stub
		return invoiceReceiveByRepository.findAll();
	}
	@Override
	public void save(InvoiceReceiveBy theinvoiceReceiveBy) {
		// TODO Auto-generated method stub
		invoiceReceiveByRepository.save(theinvoiceReceiveBy);
	}
	@Override
	public InvoiceReceiveBy findByName(String name) {

		Optional<InvoiceReceiveBy> invoiceReceiveBy2 = invoiceReceiveByRepository.findById(name);

		InvoiceReceiveBy invoiceReceiveBy = null;

		if (invoiceReceiveBy2.isPresent()) {
			invoiceReceiveBy = invoiceReceiveBy2.get();
		} else {
			// we didn't find the invoiceReceiveBy
			throw new RuntimeException("Did not find invoiceReceiveBy name - " + name);
		}

		return invoiceReceiveBy;
	
	
	
	
	}
	@Override
	public void delete(InvoiceReceiveBy invoiceReceiveBy) {

		
		invoiceReceiveByRepository.delete(invoiceReceiveBy);
	}



}
