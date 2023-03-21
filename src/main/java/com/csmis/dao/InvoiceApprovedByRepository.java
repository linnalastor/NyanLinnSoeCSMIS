package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmis.entity.InvoiceApprovedBy;

@Repository
public interface InvoiceApprovedByRepository  extends JpaRepository<InvoiceApprovedBy, String>{

}
