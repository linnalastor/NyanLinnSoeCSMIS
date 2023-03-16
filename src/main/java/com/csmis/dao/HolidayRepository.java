package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, String>{

}
