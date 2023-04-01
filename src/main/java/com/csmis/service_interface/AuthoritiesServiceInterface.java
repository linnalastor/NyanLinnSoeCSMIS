package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Authorities;

public interface AuthoritiesServiceInterface {

	public void saveAuthorities(List<Authorities> authoritiesList);

	public List<Authorities> getAuthorities();
	public void save(Authorities authorities);

	public Authorities findById(String id);

	public void saveAuthorities(Authorities authorities) ;
}
