package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.HeadCount;

public interface HeadCountServiceInterface {
	public void save(HeadCount headCount);
	public HeadCount find_by_id(String id);
	public List<HeadCount> findAllDesc();
}
