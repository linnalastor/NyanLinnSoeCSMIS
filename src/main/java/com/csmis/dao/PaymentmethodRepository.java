package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.Paymentmethod;

public interface PaymentmethodRepository  extends JpaRepository<Paymentmethod, String>{

}
