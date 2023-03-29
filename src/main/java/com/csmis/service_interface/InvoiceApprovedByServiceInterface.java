package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.InvoiceApprovedBy;


public interface InvoiceApprovedByServiceInterface {
	 public void saveInvoiceApprovedBys(List<InvoiceApprovedBy> invoiceApprovedBys);
	public List<InvoiceApprovedBy> findAll();


	public InvoiceApprovedBy findByName(String name);

	public void save(InvoiceApprovedBy theinvoiceApprovedBy);
	public void delete(InvoiceApprovedBy invoiceApprovedBy);

}
