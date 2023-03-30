package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csmis.entity.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities,String> {

}
