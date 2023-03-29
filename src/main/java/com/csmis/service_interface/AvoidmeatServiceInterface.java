package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Avoidmeat;

public interface AvoidmeatServiceInterface {

	public List<Avoidmeat> findAll();

	public void saveAvoidmeat(List<Avoidmeat> avoidmeats);

	public void save(Avoidmeat theavoidmeat);

	public Avoidmeat findByName(String name);

	public void delete(Avoidmeat avoidmeat);

}
