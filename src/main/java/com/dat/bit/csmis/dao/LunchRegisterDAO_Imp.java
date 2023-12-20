package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.entity.LunchRegister;
import com.dat.bit.csmis.entity.Staff;

@Repository
public class LunchRegisterDAO_Imp implements LunchRegisterDAO {

	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public LunchRegisterDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public List<LunchRegister> getAllLunchRegistrationForCurrentMonth() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		LocalDate localDate = LocalDate.now();
		String startDate = localDate.withDayOfMonth(1)+"";;
		String endDate = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()))+"";
		
		Query<LunchRegister> query = currentSession.createQuery("from LunchRegister where register_for >= '"+startDate+"' "
				+ "and register_for <= '"+endDate+"'",LunchRegister.class);
		
		List<LunchRegister> lunchRegisters = query.getResultList();
		
		return lunchRegisters;
	}

	@Override
	public LunchRegister getLunchRegistrationByStaffId(String staffId,String registerFor) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		String startDate = "";
		String endDate = "";

		
		LunchRegister lunchRegister = new LunchRegister();
		lunchRegister.setId(0);
		
		if(registerFor.equals("current")) {//Current month
			LocalDate localDate = LocalDate.now();
			startDate =localDate.withDayOfMonth(1)+"";
			endDate = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()))+"";
			
		}else {//Next month
			List<String> start_end_ofNextMonth = getStart_End_OfMonth(getNextMonth(new Date()));
			startDate = start_end_ofNextMonth.get(0);
			endDate = start_end_ofNextMonth.get(1);
		}
		
		
		Query<LunchRegister> query  = currentSession.createQuery("from LunchRegister where register_for >= '"+startDate+"' "
				+ "and register_for <= '"+endDate+"' and staffId = '"+staffId+"'", LunchRegister.class);
		
		
		if(!query.getResultList().isEmpty()) {
			lunchRegister = query.getSingleResult();
		}
		
		return lunchRegister;
	}


	@Override
	public void saveLunchRegistration(int registeredId,String registerFor, String status, String staffId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		
		if(registeredId == 0) {
			LunchRegister lunchRegister = new LunchRegister();
			lunchRegister.setId(registeredId);
			lunchRegister.setRegistered_date(new Timestamp(new Date().getTime()));
			if(registerFor.equals("current")) {
				lunchRegister.setRegister_for(new Timestamp(new Date().getTime()));
			}else {
				lunchRegister.setRegister_for(new Timestamp(getNextMonth(new Date()).getTime()));
			}
			lunchRegister.setRegister_status(status);
			lunchRegister.setStaffId(staffId);
			
			currentSession.save(lunchRegister);
		}else {
			Query<LunchRegister> query = currentSession.createQuery("from LunchRegister where id = "+registeredId+"", LunchRegister.class);
			LunchRegister register = query.getSingleResult();
			register.setUpdated_date(new Timestamp(new Date().getTime()));
			register.setRegister_status(status);
			
			currentSession.update(register);
		}
		
		
		
	}
	
	@Override
	public List<LunchRegister> getLunchRegistratoinListByMonth(String month) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<LunchRegister> lunchRegisters = new ArrayList<LunchRegister>();
		String startDate = "";
		String endDate = "";
		
		try {
			if(month.equals("")) {
				LocalDate localDate = LocalDate.now();
				startDate = localDate.withDayOfMonth(1)+"";;
				endDate = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()))+"";
			}else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse(month+"-01");
				
				List<String> start_end_ofNextMonth = getStart_End_OfMonth(date);
				startDate = start_end_ofNextMonth.get(0);
				endDate = start_end_ofNextMonth.get(1);
			}
			
			Query<LunchRegister> query = currentSession.createQuery("from LunchRegister where register_for >= '"+startDate+"' "
					+ "and register_for <= '"+endDate+"'",LunchRegister.class);
			
			lunchRegisters = query.getResultList();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lunchRegisters;
	}
	
	@Override
	public List<LunchRegister> searchLunchRegistrationListByStaffId(String searchValue, String month) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<LunchRegister> lunchRegisters = new ArrayList<LunchRegister>();
		String startDate = "";
		String endDate = "";
		
		try {
			if(month.equals("")) {
				LocalDate localDate = LocalDate.now();
				startDate = localDate.withDayOfMonth(1)+"";;
				endDate = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()))+"";
			}else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse(month+"-01");
				
				List<String> start_end_ofNextMonth = getStart_End_OfMonth(date);
				startDate = start_end_ofNextMonth.get(0);
				endDate = start_end_ofNextMonth.get(1);
			}
			
			Query<LunchRegister> query = currentSession.createQuery("from LunchRegister where register_for >= '"+startDate+"' "
					+ "and register_for <= '"+endDate+"' and staffId like '%"+searchValue+"%'",LunchRegister.class);
			
			lunchRegisters = query.getResultList();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lunchRegisters;
	}

	@Override
	public List<LunchRegister> searchLunchRegistrationListByStaffName( String searchValue,String month) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<LunchRegister> lunchRegisters = new ArrayList<LunchRegister>();
		String startDate = "";
		String endDate = "";
		
		try {
			if(month.equals("")) {
				LocalDate localDate = LocalDate.now();
				startDate = localDate.withDayOfMonth(1)+"";;
				endDate = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()))+"";
			}else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse(month+"-01");
				
				List<String> start_end_ofNextMonth = getStart_End_OfMonth(date);
				startDate = start_end_ofNextMonth.get(0);
				endDate = start_end_ofNextMonth.get(1);
			}
			
			Query<Staff> query = currentSession.createQuery("from Staff where name like '%"+searchValue+"%'",Staff.class);
			List<Staff> staffs = query.getResultList();
			
			for(Staff staff : staffs) {
				Query<LunchRegister> query1 = currentSession.createQuery("from LunchRegister where register_for >= '"+startDate+"' "
						+ "and register_for <= '"+endDate+"' and staffId = '"+staff.getStaffId()+"'",LunchRegister.class);
				if(!query1.getResultList().isEmpty()) {
					lunchRegisters.add(query1.getSingleResult());
				}
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return lunchRegisters;
	}

	
	//========================== Other Function -===========================================
	
	public static Date getNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		
		if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
		}else {
			calendar.roll(Calendar.MONTH, true);
		}
		
		
		return calendar.getTime();
		
	}
	
	public static List<String> getStart_End_OfMonth(Date date) {
		List<String> start_end_ofNextMonth = new ArrayList<String>();
        
        // Convert the Date object to a Calendar object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        // Get the year and month from the Calendar object
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        
        // Set the day of the month to 1 and create a new Date object to represent the first day of the month
        calendar.set(year, month, 1);
        Date startDate = calendar.getTime();
        
        // Set the day of the month to the last day of the month and create a new Date object to represent the last day of the month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = dateFormat.format(startDate);
        String endDateString = dateFormat.format(endDate);
        
        start_end_ofNextMonth.add(startDateString);
        start_end_ofNextMonth.add(endDateString);
        
        // Print the start and end date of the month
        //System.out.println("Start date: " + startDateString);
        //System.out.println("End date: " + endDateString);
        return start_end_ofNextMonth;
	}

	

}
