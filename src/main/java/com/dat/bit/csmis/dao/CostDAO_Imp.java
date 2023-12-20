package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.entity.Cost;

@Repository
public class CostDAO_Imp implements CostDAO{

	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public CostDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public Cost getCost() {
		Session currentSession = entityManager.unwrap(Session.class);
		Cost cost = new Cost();
		cost.setCostId("csc001");
		try {
			Query<Cost> query = currentSession.createQuery("from Cost where costId = 'csc001'",Cost.class);
			cost = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return cost;
	}

	@Override
	public void saveCost(Cost cost, String staffId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		cost.setCostId("csc001");
		
		cost.setUpdatedAt(new Timestamp(new Date().getTime()));
		cost.setUpdatedBy(staffId);
		
		currentSession.saveOrUpdate(cost);
		
	}

}
