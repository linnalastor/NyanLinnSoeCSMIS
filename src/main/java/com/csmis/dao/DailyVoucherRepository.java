package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmis.entity.DailyVoucher;

@Repository
public interface DailyVoucherRepository  extends JpaRepository<DailyVoucher, String>{

}
