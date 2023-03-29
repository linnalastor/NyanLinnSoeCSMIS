package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.StaffDetails;

public interface EmailStatusRepository extends JpaRepository<StaffDetails, Integer>{

}
