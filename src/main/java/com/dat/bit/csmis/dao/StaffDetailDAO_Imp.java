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


import com.dat.bit.csmis.entity.StaffDetail;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

@Repository
public class StaffDetailDAO_Imp implements StaffDetailDAO{
	
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public StaffDetailDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public StaffDetail findByStaffId(String id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<StaffDetail> theQuery = currentSession.createQuery("from StaffDetail where staff_id = '"+id+"'", StaffDetail.class);
		StaffDetail staffDetail = theQuery.getSingleResult();
		
		return staffDetail;
	}

	@Override
	public void saveStaffDetail(Staff_CSV staff_CSV) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<StaffDetail> theQuery = currentSession.createQuery("from StaffDetail where staff_id = '"+staff_CSV.getStaffId()+"'", StaffDetail.class);
		StaffDetail staffDetail = new StaffDetail();
		
		if(theQuery.getResultList().size() > 0) {
			staffDetail = theQuery.getSingleResult();
		}
		
		staffDetail.setDivision(staff_CSV.getDivision());
		staffDetail.setDepartment(staff_CSV.getDepartment());
		staffDetail.setTeam(staff_CSV.getTeam());
		staffDetail.setEmail(staff_CSV.getEmail());//<== don't forget to change with staff_CSV.getEmail();
		staffDetail.setEmail_noti(1);
		staffDetail.setCreatedAt(new Timestamp(new Date().getTime()));
		staffDetail.setCreatedBy(staff_CSV.getCreatedBy());
		staffDetail.setStaff_id(staff_CSV.getStaffId());
		
		currentSession.saveOrUpdate(staffDetail);
		
	}
	
	@Override
	public void saveStaffDetailWithExcel(Staff_ECXCEL staff_ECXCEL) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<StaffDetail> theQuery = currentSession.createQuery("from StaffDetail where staff_id = '"+staff_ECXCEL.getStaffId()+"'", StaffDetail.class);
		StaffDetail staffDetail = new StaffDetail();
		
		if(theQuery.getResultList().size() > 0) {
			staffDetail = theQuery.getSingleResult();
		}
		
		staffDetail.setDivision(staff_ECXCEL.getDivision());
		staffDetail.setDepartment(staff_ECXCEL.getDepartment());
		staffDetail.setTeam(staff_ECXCEL.getTeam());
		staffDetail.setEmail(staff_ECXCEL.getEmail());//<== don't forget to change with staff_CSV.getEmail();
		staffDetail.setEmail_noti(1);
		staffDetail.setCreatedAt(new Timestamp(new Date().getTime()));
		staffDetail.setCreatedBy(staff_ECXCEL.getCreatedBy());
		staffDetail.setStaff_id(staff_ECXCEL.getStaffId());
		
		currentSession.saveOrUpdate(staffDetail);
		
	}

	@Override
	public void updateStaffDetail(StaffDetail staffDetail) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(staffDetail);
		
	}

	@Override
	public String checkOTPEmailIsContianedOrNot(String email) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		String staffId = "";
		
		try {
			Query<String> query = currentSession.createQuery("SELECT sd.staff_id FROM StaffDetail sd "
					+ "WHERE email = '"+email+"'",String.class);
			staffId = query.getSingleResult();
			
		} catch (Exception e) {
			staffId = "";
			System.out.println("Error from checkOTPEmailIsContianedOrNot!");
			System.out.println("Don't worry!This is not error!This message is for email not contained!");
			System.out.println(e.getMessage());
		}
		
		
		return staffId;
	}

	@Override
	public List<String> getAllDepartmentName() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<String> departments = new ArrayList<>();
		
		String hql = "select distinct(department) from StaffDetail order by department";
		
		Query<String> query = currentSession.createQuery(hql,String.class);
		
		departments = query.getResultList();
		
		return departments;
	}

	@Override
	public List<String> getAllTeamName() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<String> teams = new ArrayList<>();
		
		String hql = "select distinct(team) from StaffDetail order by team";
		
		Query<String> query = currentSession.createQuery(hql,String.class);
		
		teams = query.getResultList();
		
		return teams;
	}

	@Override
	public String getEmailByStaffId(String staffId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		String email = null;
		
		try {
			String hql = "select email from StaffDetail where staff_id = '"+staffId+"'";
			Query<String> query = currentSession.createQuery(hql,String.class);
			email = query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Error => Error from getEmailByStaffId() from StaffDetailDAO_Imp!");
			System.out.println(e.getMessage());
		}
		
		return email;
	}

	

}
