package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, String>{

}
