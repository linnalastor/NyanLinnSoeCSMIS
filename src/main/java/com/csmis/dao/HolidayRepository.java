package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmis.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,String> {

}
