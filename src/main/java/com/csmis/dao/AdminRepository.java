package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{

}
