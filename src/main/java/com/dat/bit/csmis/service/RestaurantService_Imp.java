package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.RestaurantDAO;
import com.dat.bit.csmis.entity.Restaurant;

@Service
public class RestaurantService_Imp implements RestaurantService {
	
	@Autowired
	private RestaurantDAO restaurantDAO;

	@Override
	@Transactional
	public List<Restaurant> getAllRestaurantList() {
		// TODO Auto-generated method stub
		return restaurantDAO.getAllRestaurantList();
	}

	@Override
	@Transactional
	public boolean saveResaturant(Restaurant restaurant, String createdStaffId) {
		// TODO Auto-generated method stub
		return restaurantDAO.saveResaturant(restaurant, createdStaffId);
	}

	@Override
	@Transactional
	public boolean updateRestaurant(Restaurant restaurant,String updatedStaffId) {
		// TODO Auto-generated method stub
		return restaurantDAO.updateRestaurant(restaurant, updatedStaffId);
	}

	@Override
	@Transactional
	public Restaurant getRestaurantById(int id) {
		// TODO Auto-generated method stub
		return restaurantDAO.getRestaurantById(id);
	}

	@Override
	@Transactional
	public Restaurant getActiveRestaurant() {
		// TODO Auto-generated method stub
		return restaurantDAO.getActiveRestaurant();
	}

	@Override
	@Transactional
	public List<Restaurant> getAllRestaurantListBySearchName(String searchValue) {
		// TODO Auto-generated method stub
		return restaurantDAO.getAllRestaurantListBySearchName(searchValue);
	}

}
