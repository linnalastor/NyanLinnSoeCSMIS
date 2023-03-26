package com.csmis.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmis.entity.HeadCount;

@Repository
public interface HeadCountRepository extends JpaRepository<HeadCount,String> {
	@Query(value="Select * from Head_count where date=?1",nativeQuery=true)
	public HeadCount find_by_id(String s);
}
