package com.dat.bit.csmis.dao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.dto.Registered_Staff_DTO;
import com.dat.bit.csmis.entity.Department;
import com.dat.bit.csmis.entity.LunchRegister;
import com.dat.bit.csmis.entity.Staff;

@Repository
public class DashboardDAO_Imp implements DashboardDAO{
	
private EntityManager entityManager;
	
	@Autowired
	public DashboardDAO_Imp(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}

	@Override
	public List<Registered_Staff_DTO> getTodayLunchRegisteredStaffList() {
		Session currentSession=entityManager.unwrap(Session.class);
		List<String> start_endOfMonth = getStart_End_OfMonth(new Date());
		String startDate = start_endOfMonth.get(0);
		String endDate = start_endOfMonth.get(1);
		
		List<String> dateArr = getArrayOfWeekDays();
		
		
		List<LunchRegister> lunchRegisters = new ArrayList<LunchRegister>();
		Query<LunchRegister> query = currentSession.createQuery("from LunchRegister where register_for >= '"+startDate+"' and register_for <= '"+endDate+"'",LunchRegister.class);
		
		lunchRegisters = query.getResultList();
		
		List<Registered_Staff_DTO> dtos = new ArrayList<Registered_Staff_DTO>();
		
		
		for(LunchRegister register : lunchRegisters) {
			String rawString = register.getRegister_status();//1,1,0,$,1,1,...
			String rawStatus = rawString.substring(0,rawString.length()-1);
			List<String> statusArray = new ArrayList<>(Arrays.asList(rawStatus.split(",")));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(new Date());
			
			try {
				String register_status = statusArray.get(dateArr.indexOf(today));
				
				if(register_status.equals("1")) {
					Query<Staff> query1 = currentSession.createQuery("from Staff where staffId = '"+register.getStaffId()+"'",Staff.class);
					Staff staff = query1.getSingleResult();
					
					Registered_Staff_DTO dto = new Registered_Staff_DTO();
					dto.setRegister_status(register_status);
					dto.setStaffId(register.getStaffId());
					dto.setName(staff.getName());
					dto.setDoorLogNo(Integer.parseInt(staff.getDoorLogNo()));
					
					dtos.add(dto);
				}
			} catch (Exception e) {
				
				//System.out.println("Error but not error => today is Saturday or Sunday");
			}
			
		}
		
		//System.out.println("Total registered Staff => "+dtos.size());
		
		return dtos;
	}
	

	@Override
	public long getTotalDepartmentOfCompany() {
		Session currentSession=entityManager.unwrap(Session.class);
		String hql = "SELECT COUNT(DISTINCT sd.department) FROM StaffDetail sd where sd.department != '' and sd.department is not null";
		Query<Long> query = currentSession.createQuery(hql, Long.class);
		
		long total = query.getSingleResult();
		
		
		return total;
	}

	@Override
	public long getTotalTeamOfCompany() {
		Session currentSession=entityManager.unwrap(Session.class);
		String hql = "SELECT COUNT(DISTINCT sd.team) FROM StaffDetail sd where sd.team != '' and sd.team is not null";
		Query<Long> query = currentSession.createQuery(hql, Long.class);
		
		long total = query.getSingleResult();
		
		return total;
	}

	@Override
	public long getTotalDivisionOfCompany() {
		Session currentSession=entityManager.unwrap(Session.class);
		String hql = "SELECT COUNT(DISTINCT sd.division) FROM StaffDetail sd where sd.division != '' and sd.division is not null";
		Query<Long> query = currentSession.createQuery(hql, Long.class);
		
		long total = query.getSingleResult();
		
		return total;
	}
	
	@Override
	public List<Department> getDepartmentDetailForGraph() {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<Department> departmentList = new ArrayList<>();
		
		long totalStaff = 0L;
		
		try {
			String totalHQL = "SELECT COUNT(sd.staff_id) from StaffDetail sd";
			totalStaff = currentSession.createQuery(totalHQL,Long.class).getSingleResult();
		} catch (Exception e) {
			
		}
		
		List<String> departments = new ArrayList<>();
		
		String hql = "SELECT DISTINCT sd.department FROM StaffDetail sd where sd.department != '' and sd.department is not null";
		Query<String> query = currentSession.createQuery(hql, String.class);
		departments = query.getResultList();
		
		long max = 0L;
		
		for(String departname : departments) {
			String male_hql = "SELECT COUNT(sd.staff_id) from StaffDetail sd where sd.department = '"+departname+"' and sd.staff_id like '_5-%'";
			String female_hql = "SELECT COUNT(sd.staff_id) from StaffDetail sd where sd.department = '"+departname+"' and sd.staff_id like '_6-%'";
			
			Query<Long> m_query = currentSession.createQuery(male_hql, Long.class);
			Query<Long> fm_query = currentSession.createQuery(female_hql, Long.class);
			
			long male = m_query.getSingleResult();
			long female = fm_query.getSingleResult();
			
			List<Long> numbers = Arrays.asList(male, female);
			Long m = numbers.stream().mapToLong(Long::longValue).max().orElseThrow(NoSuchElementException::new);
			
			if(m > max) {
				max = m;
			}	
			
			Department department  = new Department();
			try {
				if(totalStaff > 0) {//if staffs exist
					department.setName(departname);
					department.setMale((int)male);
					department.setFemale((int)female);

					departmentList.add(department);
				}
			} catch (Exception e) {}
		}
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		
		for(Department department : departmentList) {
			max += 15;
			double mPercen = Double.parseDouble(decimalFormat.format(((double)department.getMale()*100)/max));
			double fmPercen = Double.parseDouble(decimalFormat.format(((double)department.getFemale()*100)/max));
			
			department.setMalePercentage(mPercen+1);
			department.setFemalePercentage(fmPercen+1);
		}
		
		return departmentList;
	}
	
	@Override
	public List<String> getEmailsFromStaffsWhoNotiOn() {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<String> emails = new ArrayList<>();
		
		try {
			Query<String> query = currentSession.createQuery("select sd.email from StaffDetail sd "
					+ "where sd.email like '%@diracetechnology.com' "
					+ "and sd.email_noti = 1",String.class);
			
			emails = query.getResultList();
			
		} catch (Exception e) {
			System.out.println("Error from getEmailsFromStaffsWhoNotiOn()!");
			System.out.println(e.getMessage());
		}
		
		return emails;
	}

	
	
	//======================== Other Funcions =========================
	
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
        
        return start_end_ofNextMonth;
	}
	
	public List<String> getArrayOfWeekDays() {
		//LocalDate currentDate=LocalDate.of(2023,5,1);
		LocalDate currentDate=LocalDate.now();
        //System.out.println(currentDate);

        LocalDate firstDay=currentDate.withDayOfMonth(1);
        //System.out.println(firstDay);
        List<String> dateArr=new ArrayList<>();
        LocalDate date=firstDay;
        while (date.getMonth()==currentDate.getMonth()){
            DayOfWeek day_week=date.getDayOfWeek();
            if(day_week != DayOfWeek.SATURDAY && day_week != DayOfWeek.SUNDAY){

                dateArr.add(date+"");
            }
            date=date.plusDays(1);

        }
        //System.out.println(dateArr);
        //System.out.println(dateArr.size());
        return dateArr;
	}

	



}
