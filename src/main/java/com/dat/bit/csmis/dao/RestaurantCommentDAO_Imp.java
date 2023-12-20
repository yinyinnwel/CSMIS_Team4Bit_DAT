package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.entity.Restaurant_Comment;

@Repository
public class RestaurantCommentDAO_Imp implements RestaurantCommentDAO {

	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public RestaurantCommentDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	@Override
	public List<Restaurant_Comment> getAllRestaurantComment_ByRestaurantId(int restaurant_id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Restaurant_Comment> query = currentSession.createQuery("from Restaurant_Comment where restaurant_id = "+restaurant_id+" order by commentAt desc",Restaurant_Comment.class);
		List<Restaurant_Comment> comments = query.getResultList();
		
		return comments;
	}

	@Override
	public void saveComment_ByRestaurantId(int restaurant_id, String staffId, String comment) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Restaurant_Comment restaurant_Comment = new Restaurant_Comment();
		restaurant_Comment.setComment(comment);
		restaurant_Comment.setCommentAt(new Timestamp(new Date().getTime()));
		restaurant_Comment.setRestaurant_id(restaurant_id);
		restaurant_Comment.setStaff_id(staffId);
		
		currentSession.save(restaurant_Comment);

	}

}
