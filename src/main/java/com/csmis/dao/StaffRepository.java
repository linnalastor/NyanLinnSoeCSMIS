package com.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.csmis.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, String>{

	@Query(value="SELECT id FROM Staff WHERE door_access_id = ?1",nativeQuery = true)
	public String getByDoor_Access_ID(String id);

	@Query(value="Select * from Staff where email=?1",nativeQuery=true)
	public Staff findByEmail(String email);

	


}
