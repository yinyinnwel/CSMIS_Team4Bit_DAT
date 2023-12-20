package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.entity.AvoidMeat;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

@Repository
public class AvoidMeatDAO_Imp implements AvoidMeatDAO{
	
	

	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public AvoidMeatDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	
	@Override
	public void saveAvoidMeatData(Staff_CSV staff_CSV) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where staff_id = '"+staff_CSV.getStaffId()+"'", AvoidMeat.class);
		AvoidMeat avoidMeat = new AvoidMeat();
		
		if(theQuery.getResultList().size() > 0) {
			avoidMeat = theQuery.getSingleResult();
		}
		
		avoidMeat.setDescription(null);
		avoidMeat.setCreatedAt(new Timestamp(new Date().getTime()));
		avoidMeat.setUpdatedAt(null);
		avoidMeat.setStaff_id(staff_CSV.getStaffId());
		
		currentSession.saveOrUpdate(avoidMeat);
		
	}
	
	@Override
	public void saveAvoidMeatData(Staff_ECXCEL staff_ECXCEL) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where staff_id = '"+staff_ECXCEL.getStaffId()+"'", AvoidMeat.class);
		AvoidMeat avoidMeat = new AvoidMeat();
		
		if(theQuery.getResultList().size() > 0) {
			avoidMeat = theQuery.getSingleResult();
		}
		
		avoidMeat.setDescription(null);
		avoidMeat.setCreatedAt(new Timestamp(new Date().getTime()));
		avoidMeat.setUpdatedAt(null);
		avoidMeat.setStaff_id(staff_ECXCEL.getStaffId());
		
		currentSession.saveOrUpdate(avoidMeat);
		
	}

	@Override
	public void updateAvoidMeatData(AvoidMeat avMeat) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where staff_id = '"+avMeat.getStaff_id()+"'", AvoidMeat.class);
		AvoidMeat avoidMeat = theQuery.getSingleResult();
		
		avoidMeat.setDescription(avMeat.getDescription());
		avoidMeat.setUpdatedAt(new Timestamp(new Date().getTime()));
		
		currentSession.update(avoidMeat);
		
	}

	@Override
	public AvoidMeat getAvoidMeatDataByStaffId(String staffId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		AvoidMeat avoidMeat = new AvoidMeat();
		
		try {
			Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where staff_id = '"+staffId+"'", AvoidMeat.class);
			avoidMeat = theQuery.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return avoidMeat;
	}

	@Override
	public List<AvoidMeat> getAvoidMeatStaffData() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where description is not null and description != ''", AvoidMeat.class);
		List<AvoidMeat> avoidMeats = theQuery.getResultList();
		

		return avoidMeats;
	}


	@Override
	public List<AvoidMeat> getAvoidMeatListByStaffId(String searchValue) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where staff_id like '%"+searchValue+"%' and description is not null and description != ''",AvoidMeat.class);
		List<AvoidMeat> avoidMeats = theQuery.getResultList();
		
		return avoidMeats;
	}


	@Override
	public List<AvoidMeat> getAvoidMeatListByStaffName(String searchValue) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Staff> query = currentSession.createQuery("from Staff where name like '%"+searchValue+"%' and status = 1 order by name",Staff.class);
		List<Staff> staffs = query.getResultList();
		
		List<AvoidMeat> avoidMeats = new ArrayList<AvoidMeat>();
		
		for(Staff staff : staffs) {
			Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where staff_id = '"+staff.getStaffId()+"' and description is not null and description != ''",AvoidMeat.class);
			if(!theQuery.getResultList().isEmpty()) {
				AvoidMeat avoidMeat = theQuery.getSingleResult();
				
				
				avoidMeats.add(avoidMeat);
			}
		}
		
		return avoidMeats;
	}


	

}
