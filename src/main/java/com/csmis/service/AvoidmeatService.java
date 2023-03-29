package com.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.AvoidmeatRepository;
import com.csmis.entity.Avoidmeat;
import com.csmis.service_interface.AvoidmeatServiceInterface;
@Service
public class AvoidmeatService implements AvoidmeatServiceInterface {

	private AvoidmeatRepository avoidmeatRepository;
	@Autowired
	public AvoidmeatService(AvoidmeatRepository theAvoidmeatRepository) {
		avoidmeatRepository=theAvoidmeatRepository;
	}
	
	
	
	@Override
	public List<Avoidmeat> findAll() {
		// TODO Auto-generated method stub
		return avoidmeatRepository.findAll();
	}

	@Override
	public void saveAvoidmeat(List<Avoidmeat> avoidmeats) {
		// TODO Auto-generated method stub
		avoidmeatRepository.saveAll(avoidmeats);
		
	}

	@Override
	public void save(Avoidmeat theavoidmeat) {
		// TODO Auto-generated method stub
		avoidmeatRepository.save(theavoidmeat);
	}

	@Override
	public Avoidmeat findByName(String name) {
		Optional<Avoidmeat> avoidmeat2 = avoidmeatRepository.findById(name);

		Avoidmeat avoidmeat = null;

		if (avoidmeat2.isPresent()) {
			avoidmeat = avoidmeat2.get();
		} else {
			// we didn't find the Avoidmeat
			throw new RuntimeException("Did not find Avoidmeat name - " + name);
		}

		return avoidmeat;		
	}

	@Override
	public void delete(Avoidmeat avoidmeat) {
		// TODO Auto-generated method stub
		avoidmeatRepository.delete(avoidmeat);
	}

}
