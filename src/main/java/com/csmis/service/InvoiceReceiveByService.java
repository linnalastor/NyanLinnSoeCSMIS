package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.InvoiceReceiveByRepository;
import com.csmis.entity.InvoiceReceiveBy;
import com.csmis.service_interface.InvoiceReceiveByServiceInterface;

@Service
public class InvoiceReceiveByService implements InvoiceReceiveByServiceInterface
{
        @Autowired
        private InvoiceReceiveByRepository invoiceReceiveByRepository;
	@Override
	public void saveInvoiceReceiveBys(List<InvoiceReceiveBy> invoiceReceiveBys) {
	
		invoiceReceiveByRepository.saveAll(invoiceReceiveBys);
		
	}

}