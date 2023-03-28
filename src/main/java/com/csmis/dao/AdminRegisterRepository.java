package com.csmis.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmis.entity.ConsumerList;


@Repository
public interface AdminRegisterRepository  extends JpaRepository<ConsumerList, String>{

}
