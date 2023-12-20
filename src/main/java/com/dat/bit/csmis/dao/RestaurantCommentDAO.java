package com.dat.bit.csmis.dao;

import java.util.List;

import com.dat.bit.csmis.entity.Restaurant_Comment;

public interface RestaurantCommentDAO {

	public List<Restaurant_Comment> getAllRestaurantComment_ByRestaurantId(int restaurant_id);
	
	public void saveComment_ByRestaurantId(int restaurant_id,String staffId,String comment);
}
