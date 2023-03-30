package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.HeadCountRepository;
import com.csmis.entity.HeadCount;
import com.csmis.service_interface.HeadCountServiceInterface;
@Service
public class HeadCountService implements HeadCountServiceInterface {

	@Autowired
	HeadCountRepository headCountRepository;

	@Override
	public void save(HeadCount headCount) {
		headCountRepository.save(headCount);
	}

	@Override
	public HeadCount find_by_id(String id) {
		return headCountRepository.find_by_id(id);
	}

	@Override
	public List<HeadCount> findAllDesc() {
		return headCountRepository.findAllDesc();
	}

}
