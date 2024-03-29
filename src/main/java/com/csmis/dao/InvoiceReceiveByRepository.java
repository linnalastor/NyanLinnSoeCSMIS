package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmis.entity.InvoiceReceiveBy;

@Repository
public interface InvoiceReceiveByRepository extends JpaRepository<InvoiceReceiveBy, String>{

}
