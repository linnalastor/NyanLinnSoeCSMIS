package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.Access;

public interface AccessRepository  extends JpaRepository<Access, String> {

}
