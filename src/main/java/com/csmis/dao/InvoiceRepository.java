package com.csmis.dao;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.DailyInvoice;


public interface InvoiceRepository extends JpaRepository<DailyInvoice, Integer>{
	
	 List<DailyInvoice> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
