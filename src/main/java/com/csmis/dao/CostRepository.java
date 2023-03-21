package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmis.entity.Cost;

@Repository
public interface CostRepository extends JpaRepository<Cost, String> {

}
