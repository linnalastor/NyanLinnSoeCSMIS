package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmis.entity.InvoiceCashier;

@Repository
public interface InvoiceCashierRepository extends JpaRepository<InvoiceCashier, String> {

}
