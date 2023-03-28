package com.csmis.dao;

<<<<<<< Updated upstream
import java.util.List;
=======
>>>>>>> Stashed changes

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.csmis.entity.ConsumerList;

public interface ConsumerListRepository extends JpaRepository<ConsumerList, String>{
	
	@Query(value="Select * from Consumer_list where consumer_information_id=?1",nativeQuery = true)
	public ConsumerList getLunchPlanRegister_by_ID(String id);
	
	@Query(value="Select * from Consumer_list where consumer_information_id Like %?1%",nativeQuery = true)
	public List<ConsumerList> findAll_by_MonthYear(String prefix);
}
