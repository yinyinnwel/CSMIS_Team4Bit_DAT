package com.dat.bit.csmis.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.bit.csmis.dao.HolidayDao;
import com.dat.bit.csmis.entity.Holiday;
import com.dat.bit.csmis.entity.Holiday_CSV;


@Service
public class HolidayService_Imp implements HolidayService {
	private HolidayDao holidayDao;
	
	@Autowired
	public HolidayService_Imp(HolidayDao holidayDao) {
		super();
		this.holidayDao = holidayDao;
	}


	@Override
	@Transactional
	public void addAllHolidayDataToDB(List<Holiday_CSV> holiday_CSVs) {
		holidayDao.addAllHolidayDataToDB(holiday_CSVs);

	}


	@Override
	@Transactional
	public List<Holiday> getAllHolidayDataFromDB() {
		
		return holidayDao.getAllHolidayDataFromDB();
	}


	@Override
	@Transactional
	public void deleteAll() {
		holidayDao.deleteAll();
		
	}


	@Override
	@Transactional
	public void addAllHolidayDatatoDBWithExcelFile(List<Holiday> holidays) {
		holidayDao.addAllHolidayDatatoDBWithExcelFile(holidays);
		
	}


	

}
