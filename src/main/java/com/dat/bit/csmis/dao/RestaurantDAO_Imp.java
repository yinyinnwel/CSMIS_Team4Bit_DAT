package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.entity.Restaurant;
import com.dat.bit.csmis.entity.Staff;

@Repository
public class RestaurantDAO_Imp implements RestaurantDAO {
	
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public RestaurantDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	

	@Override
	public List<Restaurant> getAllRestaurantList() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Restaurant> query = currentSession.createQuery("from Restaurant order by name",Restaurant.class);
		List<Restaurant> restaurantList = query.getResultList();
		
		return restaurantList;
	}

	@Override
	public boolean saveResaturant(Restaurant restaurant, String createdStaffId) {
		boolean status = false;
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Staff> query= currentSession.createQuery("from Staff where staffId = '"+createdStaffId+"'",Staff.class);
		Staff createdStaff = query.getSingleResult();
		
		
		
		if(restaurant.getStatus() == 1) {
			try {
				String hql1 = "SELECT id FROM Restaurant where status = 1";
				Query<Integer> query3 = currentSession.createQuery(hql1,Integer.class);
				int res3Id = query3.getSingleResult();
				
				String hql = "SELECT count(voucher_no) FROM Invoice where restaurantId = "+res3Id+" and status = 0";
				Query<Long> query11 = currentSession.createQuery(hql,Long.class);
				long unpaidVoucherCount = query11.getSingleResult();
				
				if(unpaidVoucherCount == 0) {
					restaurant.setCreatedAt(new Timestamp(new Date().getTime()));
					restaurant.setCreatedBy("("+createdStaffId+")"+createdStaff.getName());
					
					currentSession.save(restaurant);

					updateOtherRestaurants_Status_To_Inactive(restaurant);
				}else {
					//***You can't add this restaurant
					status = true;
				}
				
			} catch (Exception e) {
				//all restaurant are inactive
				restaurant.setCreatedAt(new Timestamp(new Date().getTime()));
				restaurant.setCreatedBy("("+createdStaffId+")"+createdStaff.getName());
				
				currentSession.save(restaurant);

			}
		}else {//add new restaurant as inactive
			restaurant.setCreatedAt(new Timestamp(new Date().getTime()));
			restaurant.setCreatedBy("("+createdStaffId+")"+createdStaff.getName());
			
			currentSession.save(restaurant);

		}
	
		return status;
	}

	@Override
	public boolean updateRestaurant(Restaurant restaurant,String updatedStaffId) {
		boolean status = false;
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Staff> query= currentSession.createQuery("from Staff where staffId = '"+updatedStaffId+"'",Staff.class);
		Staff updatedStaff = query.getSingleResult();
		
		Query<Restaurant> query1= currentSession.createQuery("from Restaurant where id = "+restaurant.getId()+"",Restaurant.class);
		Restaurant restaurant2 = query1.getSingleResult();
		
		
		if(restaurant.getStatus() == 0) {//when change to inactive
			String hql = "SELECT count(voucher_no) FROM Invoice where restaurantId = "+restaurant.getId()+" and status = 0";
			Query<Long> query11 = currentSession.createQuery(hql,Long.class);
			long unpaidVoucherCount = query11.getSingleResult();
			
			if(unpaidVoucherCount == 0) {//There are no unpaid vouchers.So update restaurant;
				restaurant2.setName(restaurant.getName());
				restaurant2.setAddress(restaurant.getAddress());
				restaurant2.setReceiver(restaurant.getReceiver());
				restaurant2.setPhone_no(restaurant.getPhone_no());
				restaurant2.setEmail(restaurant.getEmail());
				restaurant2.setStatus(restaurant.getStatus());
				restaurant2.setUpdatedAt(new Timestamp(new Date().getTime()));
				restaurant2.setUpdatedBy("("+updatedStaffId+")"+updatedStaff.getName());
				
				currentSession.update(restaurant2);
				
				updateOtherRestaurants_Status_To_Inactive(restaurant);
			}else {
				System.out.println("can't update to inactive 98");
				//***You can't update restaurant to inactive because there are some voucher that doesn't paid of this restaurant
				status = true;
			}
		}else {//when change to active
			try {
				//---- Check are there any active restaurant
				String hql1 = "SELECT id FROM Restaurant where id != "+restaurant.getId()+" and status = 1";
				Query<Integer> query3 = currentSession.createQuery(hql1,Integer.class);
				int res3Id = query3.getSingleResult();
				
				
				String hql = "SELECT count(voucher_no) FROM Invoice where restaurantId = "+res3Id+" and status = 0";
				Query<Long> query11 = currentSession.createQuery(hql,Long.class);
				long unpaidVoucherCount = query11.getSingleResult();

				
				if(unpaidVoucherCount == 0) {//There are no unpaid vouchers from last active restauratn;
					System.out.println("here 111");
					restaurant2.setName(restaurant.getName());
					restaurant2.setAddress(restaurant.getAddress());
					restaurant2.setReceiver(restaurant.getReceiver());
					restaurant2.setPhone_no(restaurant.getPhone_no());
					restaurant2.setEmail(restaurant.getEmail());
					restaurant2.setStatus(restaurant.getStatus());
					restaurant2.setUpdatedAt(new Timestamp(new Date().getTime()));
					restaurant2.setUpdatedBy("("+updatedStaffId+")"+updatedStaff.getName());
					
					currentSession.update(restaurant2);
					
					updateOtherRestaurants_Status_To_Inactive(restaurant);
				}else {
					//***You can't update restaurant to inactive because there are some voucher that doesn't paid of last active restaurant
					status = true;
					System.out.println("can't update to active 133");
				}
			} catch (Exception e) {
				System.out.println("here 136=> updated smoothly");
				//This error is showed because of => Long res3Id = query3.getSingleResult();
				//There is no other active restaurant<= that mean all are inactive
				//If so you can update this restaurant to active
				restaurant2.setName(restaurant.getName());
				restaurant2.setAddress(restaurant.getAddress());
				restaurant2.setReceiver(restaurant.getReceiver());
				restaurant2.setPhone_no(restaurant.getPhone_no());
				restaurant2.setEmail(restaurant.getEmail());
				restaurant2.setStatus(restaurant.getStatus());
				restaurant2.setUpdatedAt(new Timestamp(new Date().getTime()));
				restaurant2.setUpdatedBy("("+updatedStaffId+")"+updatedStaff.getName());
				
				currentSession.update(restaurant2);
				
				updateOtherRestaurants_Status_To_Inactive(restaurant);
			}
		}
	
		
		return status;
 
	}

	@Override
	public Restaurant getRestaurantById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Restaurant restaurant = new Restaurant();
		
		try {
			Query<Restaurant> query = currentSession.createQuery("from Restaurant where id = "+id,Restaurant.class);
			restaurant = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return restaurant;
	}

	@Override
	public Restaurant getActiveRestaurant() {
		Session currentSession = entityManager.unwrap(Session.class);
		Restaurant restaurant = new Restaurant();
		try {
			Query<Restaurant> query = currentSession.createQuery("from Restaurant where status = 1",Restaurant.class);
			restaurant = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return restaurant;
	}
	

	@Override
	public List<Restaurant> getAllRestaurantListBySearchName(String searchValue) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Restaurant> query = currentSession.createQuery("from Restaurant where name like '%"+searchValue+"%' order by name",Restaurant.class);
		List<Restaurant> restaurantList = query.getResultList();
		
		return restaurantList;
	}

//============================== Other Methods ======================================================================
	
	
	private void updateOtherRestaurants_Status_To_Inactive(Restaurant restaurant) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		if(restaurant.getStatus() == 1) {
			Query<Restaurant> query2= currentSession.createQuery("from Restaurant",Restaurant.class);
			List<Restaurant> restaurantList = query2.getResultList();
			
			for(Restaurant res : restaurantList) {
				if(res.getId() != restaurant.getId()) {
					res.setStatus(0);
					
					currentSession.update(res);
				}
			}
		}
	}



}
