package com.dat.bit.csmis.service;

import java.util.List;

import com.dat.bit.csmis.entity.Restaurant_Comment;

public interface RestaurantCommentService {

	public List<Restaurant_Comment> getAllRestaurantComment_ByRestaurantId(int restaurant_id);
	
	public void saveComment_ByRestaurantId(int restaurant_id,String staffId,String comment);
}
