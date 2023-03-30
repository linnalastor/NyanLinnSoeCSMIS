package com.csmis.service_interface;

import java.util.List;

import com.csmis.entity.Restaurant;

public interface RestaurantServiceInterface {

	public void saveRestaurants(List<Restaurant> restaurants);
	public List<Restaurant> findAll();



	public Restaurant findByName(String name);

	public void save(Restaurant theRestaurant);
	public void delete(Restaurant restaurant);

}
