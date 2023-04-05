package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.csmis.entity.Access;

public interface AccessRepository  extends JpaRepository<Access, String> {
	
	@Query(value="Select * from Access where id=?1",nativeQuery=true)
	public Access getAccessById(int id);

}
