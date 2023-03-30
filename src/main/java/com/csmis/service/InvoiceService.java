package com.csmis.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.InvoiceRepository;
import com.csmis.entity.DailyInvoice;
import com.csmis.service_interface.InvoiceServiceInterface;

@Service
public class InvoiceService implements InvoiceServiceInterface {

	private InvoiceRepository invoiceRepository;

	@Autowired
	public InvoiceService(InvoiceRepository theInvoiceRepository) {
		invoiceRepository = theInvoiceRepository;
	}

	@Override
	public List<DailyInvoice> findAll() {

		return invoiceRepository.findAll();
	}
	@Override
	public List<DailyInvoice> findByDateBetween(LocalDate startDate, LocalDate endDate) {

		return invoiceRepository.findByDateBetween(startDate, endDate);
	}

	@Override
	public void save(DailyInvoice dailyInvoice) {
		invoiceRepository.save(dailyInvoice);
	}

	@Override
	public LocalDate getFirstDate() {
		return invoiceRepository.getFirstDate();

	}




}
