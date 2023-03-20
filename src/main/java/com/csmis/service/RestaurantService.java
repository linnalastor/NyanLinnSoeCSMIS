package com.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.RestaurantRepository;
import com.csmis.entity.Restaurant;
import com.csmis.service_interface.RestaurantServiceInterface;

@Service
public class RestaurantService implements RestaurantServiceInterface {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public void saveRestaurants(List<Restaurant> restaurants) {
		
		restaurantRepository.saveAll(restaurants);
	}

}
