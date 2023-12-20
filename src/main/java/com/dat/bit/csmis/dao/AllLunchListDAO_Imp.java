package com.dat.bit.csmis.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.dto.Registered_Staff_DTO;
import com.dat.bit.csmis.entity.All_Have_Lunch;
import com.dat.bit.csmis.entity.Cost;
import com.dat.bit.csmis.entity.DoorLog_CSV;
import com.dat.bit.csmis.entity.DoorLog_EXCEL;
import com.dat.bit.csmis.entity.LunchRegister;
import com.dat.bit.csmis.entity.Restaurant;
import com.dat.bit.csmis.entity.Staff;

@Repository
public class AllLunchListDAO_Imp implements AllLunchListDAO {

private EntityManager entityManager;
	
	@Autowired
	public AllLunchListDAO_Imp(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}
	@Override
	public void saveTodayDoorTransactionsToDB(List<DoorLog_CSV> doorLog_CSVs) {
		Session currentSession=entityManager.unwrap(Session.class);

//==================== Get Cost of Company and Staff =======================================================================
		Query<Cost> costQuery = currentSession.createQuery("from Cost",Cost.class);
		Cost cost = new Cost();
		if(!costQuery.getResultList().isEmpty()) {
			cost = costQuery.getSingleResult();
		}
//==================== Get Current Active Restaurant Service =================================================================
		Query<Restaurant> resQuery = currentSession.createQuery("from Restaurant where status = 1",Restaurant.class);
		Restaurant restaurant = new Restaurant();
		if(!resQuery.getResultList().isEmpty()) {
			restaurant = resQuery.getSingleResult();
		}
//==================== Get total Registered Staff list for today ===============================================================
		
		List<Registered_Staff_DTO> dtos = getTodayLunchRegisteredStaffList();

//===================================================================================================================
		int count = 0;
		List<All_Have_Lunch> all_Have_LunchList = new ArrayList<All_Have_Lunch>();
		
		for(DoorLog_CSV doorLog_CSV : doorLog_CSVs) {
			int nextIndex = doorLog_CSVs.indexOf(doorLog_CSV)+1;
			if(nextIndex == doorLog_CSVs.size()) {
				nextIndex = 0;
			}
			if(doorLog_CSV.getDoorlogNo() != doorLog_CSVs.get(nextIndex).getDoorlogNo()) {//<= Check duplicate DoorLog Transaction
				boolean check = false;
				int removeIndex = -1;
				
				for(Registered_Staff_DTO staff_DTO : dtos) {
					if(doorLog_CSV.getDoorlogNo() == staff_DTO.getDoorLogNo()) {//<= Eat also register
						removeIndex = dtos.indexOf(staff_DTO);
						check = true;
						count++;
						try {
							All_Have_Lunch all_Have_Lunch = new All_Have_Lunch();

							all_Have_Lunch.setStaffId(staff_DTO.getStaffId());
							all_Have_Lunch.setName(staff_DTO.getName());
							all_Have_Lunch.setDoorLogNo(staff_DTO.getDoorLogNo());
							all_Have_Lunch.setDate_time(generateTimestamp(doorLog_CSV.getDate_time()));
							all_Have_Lunch.setCompany_cost(cost.getCompanyCost());
							all_Have_Lunch.setStaff_cost(cost.getStaffCost());
							all_Have_Lunch.setRestaurnat_id(restaurant.getId());
							all_Have_Lunch.setRegister((staff_DTO.getRegister_status().equals("1") ? 1 : 0));
							all_Have_Lunch.setStatus(1);
							all_Have_Lunch.setCreatedAt(new Timestamp(new Date().getTime()));
							
							all_Have_LunchList.add(all_Have_Lunch);
						} catch (Exception e) {
							System.out.println("Here 1 => No Staff with this doorlog"+e.getMessage());
						}

					}
				}
				
				if(removeIndex != -1) {
					dtos.remove(removeIndex);
				}
				
				if(!check) {//Eat not register
					Query<Staff> query2 = currentSession.createQuery("from Staff where doorLogNo = '"+(doorLog_CSV.getDoorlogNo()+"").trim()+"'",Staff.class);
					
					try {
						Staff staff = query2.getSingleResult();
						
						All_Have_Lunch all_Have_Lunch = new All_Have_Lunch();
						
						all_Have_Lunch.setStaffId(staff.getStaffId());
						all_Have_Lunch.setName(staff.getName());
						all_Have_Lunch.setDoorLogNo(doorLog_CSV.getDoorlogNo());
						all_Have_Lunch.setDate_time(generateTimestamp(doorLog_CSV.getDate_time()));
						all_Have_Lunch.setCompany_cost(cost.getCompanyCost());
						all_Have_Lunch.setStaff_cost(cost.getStaffCost());
						all_Have_Lunch.setRestaurnat_id(restaurant.getId());
						all_Have_Lunch.setRegister(0);
						all_Have_Lunch.setStatus(1);
						all_Have_Lunch.setCreatedAt(new Timestamp(new Date().getTime()));
						
						all_Have_LunchList.add(all_Have_Lunch);
					} catch (Exception e) {
						System.out.println("Here 2 => No Staff with this doorlog");
					}
					
					
				}
				check = false;
				removeIndex = -1;
			}
		}
		
		System.out.println("Register and Eat Total  => "+count);
		System.out.println("Register but Not Eat Total => "+dtos.size());

		if(!dtos.isEmpty()) {//<=There are staffs who had registered but didn't have lunch
			for(Registered_Staff_DTO dto : dtos) {
				try {
					All_Have_Lunch all_Have_Lunch = new All_Have_Lunch();

					all_Have_Lunch.setStaffId(dto.getStaffId());
					all_Have_Lunch.setName(dto.getName());
					all_Have_Lunch.setDoorLogNo(dto.getDoorLogNo());
					all_Have_Lunch.setDate_time(null);
					all_Have_Lunch.setCompany_cost(cost.getCompanyCost());
					all_Have_Lunch.setStaff_cost(cost.getStaffCost());
					all_Have_Lunch.setRestaurnat_id(restaurant.getId());
					all_Have_Lunch.setRegister(1);
					all_Have_Lunch.setStatus(0);
					all_Have_Lunch.setCreatedAt(new Timestamp(new Date().getTime()));
					
					all_Have_LunchList.add(all_Have_Lunch);
				} catch (Exception e) {
					System.out.println("Here 3"+e.getMessage());
				}
			}
		}
		
	//============= Add Today Lunch Data to DB ===============================================
		
		for(All_Have_Lunch lunch : all_Have_LunchList) {
			String time = (lunch.getCreatedAt()+"").substring(0,11);
			Query<All_Have_Lunch> lunchQuery = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+time+" 00:00:00' and createdAt <= '"+time+" 23:59:59' "
					+ "and doorLogNo = '"+lunch.getDoorLogNo()+"'",All_Have_Lunch.class);
			
			if(lunchQuery.getResultList().isEmpty()) {
				//check is current transaction already exist or not
				currentSession.save(lunch);
			}
			
		}
		

	}
	
	@Override
	public void saveTodayDoorTransactionsToDBByExcelFile(List<DoorLog_EXCEL> doorLog_EXCELs) {
			Session currentSession=entityManager.unwrap(Session.class);

		//==================== Get Cost of Company and Staff =======================================================================
				Query<Cost> costQuery = currentSession.createQuery("from Cost",Cost.class);
				Cost cost = new Cost();
				if(!costQuery.getResultList().isEmpty()) {
					cost = costQuery.getSingleResult();
				}
		//==================== Get Current Active Restaurant Service =================================================================
				Query<Restaurant> resQuery = currentSession.createQuery("from Restaurant where status = 1",Restaurant.class);
				Restaurant restaurant = new Restaurant();
				if(!resQuery.getResultList().isEmpty()) {
					restaurant = resQuery.getSingleResult();
				}
		//==================== Get total Registered Staff list for today ===============================================================
				
				List<Registered_Staff_DTO> dtos = getTodayLunchRegisteredStaffList();
				
		//===================================================================================================================
				int count = 0;
				List<All_Have_Lunch> all_Have_LunchList = new ArrayList<All_Have_Lunch>();
				
				for(DoorLog_EXCEL doorLog_EXCEL : doorLog_EXCELs) {
					int nextIndex = doorLog_EXCELs.indexOf(doorLog_EXCEL)+1;
					if(nextIndex == doorLog_EXCELs.size()) {
						nextIndex = 0;
					}
					if(doorLog_EXCEL.getDoorlogNo() != doorLog_EXCELs.get(nextIndex).getDoorlogNo()) {//<= Check duplicate DoorLog Transaction
						boolean check = false;
						int removeIndex = -1;
						
						for(Registered_Staff_DTO staff_DTO : dtos) {
							if(doorLog_EXCEL.getDoorlogNo() == staff_DTO.getDoorLogNo()) {//<= Eat also register
								removeIndex = dtos.indexOf(staff_DTO);
								check = true;
								count++;
								try {
									All_Have_Lunch all_Have_Lunch = new All_Have_Lunch();

									all_Have_Lunch.setStaffId(staff_DTO.getStaffId());
									all_Have_Lunch.setName(staff_DTO.getName());
									all_Have_Lunch.setDoorLogNo(staff_DTO.getDoorLogNo());
									all_Have_Lunch.setDate_time(generateTimestampForExcel(doorLog_EXCEL.getDate_time()+""));
									all_Have_Lunch.setCompany_cost(cost.getCompanyCost());
									all_Have_Lunch.setStaff_cost(cost.getStaffCost());
									all_Have_Lunch.setRestaurnat_id(restaurant.getId());
									all_Have_Lunch.setRegister((staff_DTO.getRegister_status().equals("1") ? 1 : 0));
									all_Have_Lunch.setStatus(1);
									all_Have_Lunch.setCreatedAt(new Timestamp(new Date().getTime()));
									
									all_Have_LunchList.add(all_Have_Lunch);
								} catch (Exception e) {
									System.out.println("Here 1 => No Staff with this doorlog"+e.getMessage());
								}

							}
						}
						
						if(removeIndex != -1) {
							dtos.remove(removeIndex);
						}
						
						if(!check) {//Eat not register
							Query<Staff> query2 = currentSession.createQuery("from Staff where doorLogNo = '"+(doorLog_EXCEL.getDoorlogNo()+"").trim()+"'",Staff.class);
							
							try {
								Staff staff = query2.getSingleResult();
								
								All_Have_Lunch all_Have_Lunch = new All_Have_Lunch();
								
								all_Have_Lunch.setStaffId(staff.getStaffId());
								all_Have_Lunch.setName(staff.getName());
								all_Have_Lunch.setDoorLogNo(doorLog_EXCEL.getDoorlogNo());
								all_Have_Lunch.setDate_time(generateTimestampForExcel(doorLog_EXCEL.getDate_time()+""));
								all_Have_Lunch.setCompany_cost(cost.getCompanyCost());
								all_Have_Lunch.setStaff_cost(cost.getStaffCost());
								all_Have_Lunch.setRestaurnat_id(restaurant.getId());
								all_Have_Lunch.setRegister(0);
								all_Have_Lunch.setStatus(1);
								all_Have_Lunch.setCreatedAt(new Timestamp(new Date().getTime()));
								
								all_Have_LunchList.add(all_Have_Lunch);
							} catch (Exception e) {
								System.out.println("Here 2 => No Staff with this doorlog");
							}
							
							
						}
						check = false;
						removeIndex = -1;
					}
				}
				
				System.out.println("Register and Eat Total  => "+count);
				System.out.println("Register but Not Eat Total => "+dtos.size());

				if(!dtos.isEmpty()) {//<=There are staffs who had registered but didn't have lunch
					for(Registered_Staff_DTO dto : dtos) {
						try {
							All_Have_Lunch all_Have_Lunch = new All_Have_Lunch();

							all_Have_Lunch.setStaffId(dto.getStaffId());
							all_Have_Lunch.setName(dto.getName());
							all_Have_Lunch.setDoorLogNo(dto.getDoorLogNo());
							all_Have_Lunch.setDate_time(null);
							all_Have_Lunch.setCompany_cost(cost.getCompanyCost());
							all_Have_Lunch.setStaff_cost(cost.getStaffCost());
							all_Have_Lunch.setRestaurnat_id(restaurant.getId());
							all_Have_Lunch.setRegister(1);
							all_Have_Lunch.setStatus(0);
							all_Have_Lunch.setCreatedAt(new Timestamp(new Date().getTime()));
							
							all_Have_LunchList.add(all_Have_Lunch);
						} catch (Exception e) {
							System.out.println("Here 3"+e.getMessage());
						}
					}
				}
				
			//============= Add Today Lunch Data to DB ===============================================
				
				for(All_Have_Lunch lunch : all_Have_LunchList) {
					String time = (lunch.getCreatedAt()+"").substring(0,11);
					Query<All_Have_Lunch> lunchQuery = currentSession.createQuery("from All_Have_Lunch "
							+ "where createdAt >= '"+time+" 00:00:00' and createdAt <= '"+time+" 23:59:59' "
							+ "and doorLogNo = '"+lunch.getDoorLogNo()+"'",All_Have_Lunch.class);
					
					if(lunchQuery.getResultList().isEmpty()) {
						//check is current transaction already exist or not
						currentSession.save(lunch);
					}
					
				}
				

		
	}
	
	
	
	@Override
	public List<All_Have_Lunch> getTodayAllLunchTransactionListByOption(String option) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<All_Have_Lunch> all_Have_Lunchs = new ArrayList<All_Have_Lunch>();
		
		Query<All_Have_Lunch> query = null;
		
		switch (option) {
		case "all":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' "
					+ "and register = 1 and status = 1 order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_not_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' "
					+ "and register = 1 and status = 0 order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat_noreg":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' "
					+ "and register = 0 and status = 1 order by createdAt",All_Have_Lunch.class);
		}break;

		default:
			break;
		}
		
		
		all_Have_Lunchs = query.getResultList();
		
		return all_Have_Lunchs;
	}
	
	
	
	@Override
	public List<All_Have_Lunch> searchTodayAllLunchTransactionList(String option, String searchValue, String filterBy) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<All_Have_Lunch> all_Have_Lunchs = new ArrayList<All_Have_Lunch>();
		
		Query<All_Have_Lunch> query = null;
		
		switch (option) {
		case "all":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' "
					+ "and register = 1 and status = 1 "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_not_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' "
					+ "and register = 1 and status = 0 "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat_noreg":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+LocalDate.now()+" 00:00:00' "
					+ "and createdAt <= '"+LocalDate.now()+" 23:59:59' "
					+ "and register = 0 and status = 1 "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;

		default:
			break;
		}
		
		all_Have_Lunchs = query.getResultList();
		
		return all_Have_Lunchs;
	}
	
	
	@Override
	public List<All_Have_Lunch> getAllLunchTransactionListByOptionAndDateRange(String option, String startDate,String endDate) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<All_Have_Lunch> all_Have_Lunchs = new ArrayList<All_Have_Lunch>();
		
		Query<All_Have_Lunch> query = null;
		
		switch (option) {
		case "all":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "and register = 1 and status = 1 order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_not_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "and register = 1 and status = 0 order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat_noreg":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "and register = 0 and status = 1 order by createdAt",All_Have_Lunch.class);
		}break;

		default:
			break;
		}
		
		
		all_Have_Lunchs = query.getResultList();
		
		return all_Have_Lunchs;
	}
	@Override
	public List<All_Have_Lunch> searchAllLunchTransactionListWithDateRange(String option, String searchValue,
			String filterBy, String startDate, String endDate) {
		Session currentSession=entityManager.unwrap(Session.class);
		
		List<All_Have_Lunch> all_Have_Lunchs = new ArrayList<All_Have_Lunch>();
		
		Query<All_Have_Lunch> query = null;
		
		switch (option) {
		case "all":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "and register = 1 and status = 1 "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_not_eat":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "and register = 1 and status = 0 "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;
		case "total_eat_noreg":{
			query = currentSession.createQuery("from All_Have_Lunch "
					+ "where createdAt >= '"+(startDate.equals("")? LocalDate.now() : startDate)+" 00:00:00' "
					+ "and createdAt <= '"+(endDate.equals("")? LocalDate.now() : endDate)+" 23:59:59' "
					+ "and register = 0 and status = 1 "
					+ "and "+(filterBy.equals("staffId") ? "staffId" : "name")+" like '%"+searchValue+"%' "
					+ "order by createdAt",All_Have_Lunch.class);
		}break;

		default:
			break;
		}
		
		all_Have_Lunchs = query.getResultList();
		
		return all_Have_Lunchs;
	}
	
	
	
	
	//============================ Other Functions =========================================
	
	public List<Registered_Staff_DTO> getTodayLunchRegisteredStaffList(){
		
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
					
					String register_status = statusArray.get(dateArr.indexOf(today));
					
					//System.out.println(register_status);
					
					//If staff is register for today
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
					
				}
				
				System.out.println("Total registered Staff => "+dtos.size());
				
				return dtos;
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
	
	public Timestamp generateTimestamp(String dateString) throws ParseException {
		
		DateFormat inputDateFormat = new SimpleDateFormat("M/dd/yyyy HH:mm");
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = inputDateFormat.parse(dateString);
		String formattedDate = outputDateFormat.format(date);
		
		//System.out.println(formattedDate);
		
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date parsedDate = dateFormat.parse(formattedDate);
		Timestamp timestamp = new Timestamp(parsedDate.getTime());
		
//		System.out.println(timestamp);
		
		return timestamp;
	}
	
	public Timestamp generateTimestampForExcel(String dateString) throws ParseException{
		
	        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	        Timestamp timestamp = null;
	        try {
	            Date date = inputFormat.parse(dateString);
	            String formattedDate = outputFormat.format(date);
	            
	            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	            Date parsedDate = dateFormat.parse(formattedDate);
	    		timestamp = new Timestamp(parsedDate.getTime());
	    		
	    		
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return timestamp;
	}

}
