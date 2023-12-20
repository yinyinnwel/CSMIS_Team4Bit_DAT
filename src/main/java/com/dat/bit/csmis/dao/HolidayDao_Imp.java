package com.dat.bit.csmis.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.entity.Holiday;
import com.dat.bit.csmis.entity.Holiday_CSV;
import com.dat.bit.csmis.entity.LunchRegister;

import org.hibernate.Session;
import org.hibernate.query.Query;

@Repository
public class HolidayDao_Imp implements HolidayDao {
	
	private EntityManager entityManager;
	
	@Autowired
	public HolidayDao_Imp(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}
	@Override
	public void addAllHolidayDataToDB(List<Holiday_CSV> holiday_CSVs) {
		Session currentSession=entityManager.unwrap(Session.class);
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<String> holidayStringArr = new ArrayList<>();
		
		for(Holiday_CSV holiday_csv : holiday_CSVs) {
			String inputDateStr = holiday_csv.getDate();
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date inputDate = inputDateFormat.parse(inputDateStr);
				String outputDateStr = outputDateFormat.format(inputDate);
				holidayStringArr.add(outputDateStr);
			} catch (Exception e) {}
		}
		
		for(Holiday_CSV holiday_csv : holiday_CSVs) {
			String date = holiday_csv.getDate();
			SimpleDateFormat inputFormat=new SimpleDateFormat("MM/dd/yyyy");
			
			SimpleDateFormat outputFormat=new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Date inputDate = inputFormat.parse(date);
				String outputDate=outputFormat.format(inputDate);
				//outputDate=outputDate.replace(" ", "-");
				Holiday holiday=new Holiday(0,outputDate,holiday_csv.getHoliday());
				
				currentSession.saveOrUpdate(holiday);
				
				//====== Update Holiday to registered Staff lunch registration ============
				UpdateHolidaytoRegisteredStaffLunchRegistration(outputDate, holidayStringArr);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}
	
	@Override
	public void addAllHolidayDatatoDBWithExcelFile(List<Holiday> holidays) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<String> holidayStringArr = new ArrayList<>();
		
		for(Holiday holiday : holidays) {
			String inputDateStr = holiday.getDate();
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date inputDate = inputDateFormat.parse(inputDateStr);
				String outputDateStr = outputDateFormat.format(inputDate);
				holidayStringArr.add(outputDateStr);
			} catch (Exception e) {}
		}

		for (Holiday holiday : holidays) {
			String inputDateStr = holiday.getDate();
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date inputDate = inputDateFormat.parse(inputDateStr);
				String outputDateStr = outputDateFormat.format(inputDate);
				// System.out.println(outputDateStr); // Output: 2023-01-04

				holiday.setDate(outputDateStr);
		//======================= Save Holiday to DB ============================
				currentSession.saveOrUpdate(holiday);
				
		//====== Update Holiday to registered Staff lunch registration ============
				UpdateHolidaytoRegisteredStaffLunchRegistration(outputDateStr, holidayStringArr);
					
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

	}
	
	@Override
	public List<Holiday> getAllHolidayDataFromDB() {
		Session currentSession=entityManager.unwrap(Session.class);
		Query<Holiday> query=currentSession.createQuery("from Holiday",Holiday.class);
		List<Holiday> holidays=query.getResultList();
		return holidays;
	}
	
	@Override
	public void deleteAll() {
		Session currentSession=entityManager.unwrap(Session.class);
		
		Query<Holiday> query=currentSession.createQuery("from Holiday",Holiday.class);
		List<Holiday> holidays=query.getResultList();
		for(Holiday h:holidays) {
			currentSession.delete(h);
		}
	}
	
	
//===================================== Other Functions ========================================
	
	public void UpdateHolidaytoRegisteredStaffLunchRegistration(String outputDateStr, List<String> holidayStringArr) throws ParseException {
		Session currentSession = entityManager.unwrap(Session.class);

		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = outputDateFormat.parse(outputDateStr);
		List<String> start_end_ofNextMonth = getStart_End_OfMonth(d);
		String startDate = start_end_ofNextMonth.get(0);
		String endDate = start_end_ofNextMonth.get(1);
		// -------- Get lunch registered staff list based on each holiday-----------
		List<LunchRegister> lunchRegisters = new ArrayList<>();
		Query<LunchRegister> query = currentSession.createQuery("from LunchRegister where " 
									+ "register_for >= '"+ startDate + "' and " 
									+ "register_for <= '" + endDate + "'", LunchRegister.class);
		lunchRegisters = query.getResultList();

		for (LunchRegister register : lunchRegisters) {
			String rawStatus = register.getRegister_status();// 1,1,0,1,0,#,....
			String stStatus = rawStatus.substring(0, rawStatus.length() - 1);

			List<String> statusArr = new ArrayList<>(Arrays.asList(stStatus.split(",")));

			List<String> dateArr = getArrayOfWeekDays(outputDateStr);
			int holidayIndex = dateArr.indexOf(outputDateStr);

			if (holidayIndex != -1) {// If index is -1,this is Sat or Sun

				// ==== Update holiday with "#" inside original status string
				// ==== And Create new Status and update DB

				if (!statusArr.get(holidayIndex).equals("#")) {// This day is already holiday,no need to update
					statusArr.set(holidayIndex, "#");
					String newStatus = "";
					for (String status : statusArr) {
						newStatus += status + ",";
					}

					register.setRegister_status(newStatus);

					currentSession.update(register);
				}
			}

			// =============== Logic 2(Recheck holiday) =============================
			boolean check = false;
			for (int i = 0; i < statusArr.size(); i++) {
				if (statusArr.get(i).equals("#")) {
					if (!holidayStringArr.contains(dateArr.get(i))) {// if that day is not holiday,
						check = true;
						statusArr.set(i, "1");
					}
				}
			}

			if (check) {
				String newStatus = "";
				for (String status : statusArr) {
					newStatus += status + ",";
				}

				register.setRegister_status(newStatus);

				currentSession.update(register);
				check = false;
			}

		}

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
	
	public List<String> getArrayOfWeekDays(String outputDateStr) {
		List<String> yyyymmdd = new ArrayList<>(Arrays.asList(outputDateStr.split("-")));
		int year = Integer.parseInt(yyyymmdd.get(0));
		int month = Integer.parseInt(yyyymmdd.get(1));
		int day = Integer.parseInt(yyyymmdd.get(2));
		LocalDate currentDate=LocalDate.of(year,month,day);
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
