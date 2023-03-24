package com.csmis.service_interface;

import java.time.LocalDate;
import java.util.List;

import com.csmis.entity.DailyInvoice;


public interface InvoiceServiceInterface {
public List<DailyInvoice> findAll();
	 List<DailyInvoice> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
