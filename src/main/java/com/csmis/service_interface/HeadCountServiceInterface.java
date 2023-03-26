package com.csmis.service_interface;

import com.csmis.entity.HeadCount;

public interface HeadCountServiceInterface {
	public void save(HeadCount headCount);
	public HeadCount find_by_id(String id);
}
