package com.csmis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmis.entity.StaffDetails;

@Repository
public interface StaffDetailsRepository  extends JpaRepository<StaffDetails, String> {

	boolean existsById(Long id);


	@Query(value="SELECT * FROM staff_detail WHERE email_status = 1",nativeQuery = true)
	public List<StaffDetails> getEmailStatus ();


	@Query(value="Select * from staff_detail where id=?1",nativeQuery=true)
	public StaffDetails getByID(String id);



}
