package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.RestaurantCommentDAO;
import com.dat.bit.csmis.entity.Restaurant_Comment;

@Service
public class RestaurantCommentService_Imp implements RestaurantCommentService{
	
	@Autowired
	private RestaurantCommentDAO restaurantCommentDAO;

	@Override
	@Transactional
	public List<Restaurant_Comment> getAllRestaurantComment_ByRestaurantId(int restaurant_id) {
		// TODO Auto-generated method stub
		return restaurantCommentDAO.getAllRestaurantComment_ByRestaurantId(restaurant_id);
	}

	@Override
	@Transactional
	public void saveComment_ByRestaurantId(int restaurant_id, String staffId, String comment) {
		restaurantCommentDAO.saveComment_ByRestaurantId(restaurant_id, staffId, comment);
		
	}

}
