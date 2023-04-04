package com.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.AuthoritiesRepository;
import com.csmis.entity.Authorities;
import com.csmis.service_interface.AuthoritiesServiceInterface;
@Service
public class AuthoritiesService implements AuthoritiesServiceInterface{



	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	@Override
	public void saveAuthorities(List<Authorities> authoritiesList) {

		Authorities authorities=new Authorities();
		for(Authorities auth: authoritiesList) {
			authorities.Authorities1(auth.getId(),auth.getAuthority());
			authoritiesRepository.save(authorities);
		}


	}


	@Override
	public List<Authorities> getAuthorities() {
		// TODO Auto-generated method stub
		return authoritiesRepository.findAll();
	}

	@Override
	public void save(Authorities authorities) {
		// TODO Auto-generated method stub
		authoritiesRepository.save(authorities);
	}


	@Override
	public Authorities findById(String id) {
		// TODO Auto-generated method stub
	Optional<Authorities> authorities2 = authoritiesRepository.findById(id);

	Authorities authorities = null;

		if (authorities2.isPresent()) {
			authorities = authorities2.get();
		}
		else {
			// we didn't find the authorities
			throw new RuntimeException("Did not find authorities id - " + id);
		}

		return authorities;
	}


	@Override
	public void saveAuthorities(Authorities authorities) {
		// TODO Auto-generated method stub
		 authoritiesRepository.save(authorities);
	}


	public List<Authorities> findAll() {
		return authoritiesRepository.findAll();
		
	}

}
