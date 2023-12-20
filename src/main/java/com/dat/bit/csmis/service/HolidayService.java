package com.dat.bit.csmis.service;

import java.util.List;

import com.dat.bit.csmis.entity.Holiday;
import com.dat.bit.csmis.entity.Holiday_CSV;



public interface HolidayService {
	public void addAllHolidayDataToDB(List<Holiday_CSV> holiday_CSVs);
	public void addAllHolidayDatatoDBWithExcelFile(List<Holiday> holidays);
	public List<Holiday> getAllHolidayDataFromDB();
	public void deleteAll();
}
