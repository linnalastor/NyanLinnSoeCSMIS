package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.InvoiceReceiveBy;

public interface InvoiceReceiveByServiceInterface {

public void saveInvoiceReceiveBys(List<InvoiceReceiveBy> invoiceReceiveBys);
public List<InvoiceReceiveBy> findAll();

public void save(InvoiceReceiveBy theinvoiceReceiveBy);

}
