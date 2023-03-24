package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.InvoiceCashier;

public interface InvoiceCashierServiceInterface {


	 public void saveInvoiceCashiers(List<InvoiceCashier> invoiceCashiers);

	public List<InvoiceCashier> findAll();

	public void save(InvoiceCashier theinvoiceCashier);
	 

    

}
