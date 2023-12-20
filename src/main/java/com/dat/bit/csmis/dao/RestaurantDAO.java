package com.dat.bit.csmis.dao;

import java.util.List;

import com.dat.bit.csmis.entity.Restaurant;

public interface RestaurantDAO {
	
	public List<Restaurant> getAllRestaurantList();
	
	public boolean saveResaturant(Restaurant restaurant,String createdStaffId);
	
	public boolean updateRestaurant(Restaurant restaurant,String updatedStaffId);
	
	public Restaurant getRestaurantById(int id);
	
	public Restaurant getActiveRestaurant();
	
	public List<Restaurant> getAllRestaurantListBySearchName(String searchValue);

}
