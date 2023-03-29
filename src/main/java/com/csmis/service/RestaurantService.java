package com.csmis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmis.dao.RestaurantRepository;
import com.csmis.entity.Restaurant;
import com.csmis.service_interface.RestaurantServiceInterface;

@Service
public class RestaurantService implements RestaurantServiceInterface {

	private RestaurantRepository restaurantRepository;

	@Autowired
	public RestaurantService(RestaurantRepository theRestaurantRepository) {
		restaurantRepository = theRestaurantRepository;
	}

	@Override
	public void saveRestaurants(List<Restaurant> restaurants) {

		restaurantRepository.saveAll(restaurants);
	}

	@Override
	public List<Restaurant> findAll() {

		// TODO Auto-generated method stub
		return restaurantRepository.findAll();
	}

	@Override
	public void delete(Restaurant restaurant) {
		restaurantRepository.delete(restaurant);
	}

	@Override
	public Restaurant findByName(String name) {
		Optional<Restaurant> restaurant2 = restaurantRepository.findById(name);

		Restaurant restaurant = null;

		if (restaurant2.isPresent()) {
			restaurant = restaurant2.get();
		} else {
			// we didn't find the Restaurant
			throw new RuntimeException("Did not find restaurant name - " + name);
		}

		return restaurant;
	}

	@Override
	public void save(Restaurant theRestaurant) {
		// TODO Auto-generated method stub

		restaurantRepository.save(theRestaurant);

	}

}