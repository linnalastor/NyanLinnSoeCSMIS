package com.csmis.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmis.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,String> {

    @Query(value="Select * from Holiday h where h.date=?1",nativeQuery=true)
    public Holiday findbyDate(String date);


}