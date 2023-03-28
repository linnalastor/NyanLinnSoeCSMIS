package com.csmis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmis.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,String> {
	@Query(value="Select * from Holiday where date like %?1%",nativeQuery=true)
	public List<String> getDateList_by_MonthYear(String s);
}
