package com.dat.bit.csmis.dao;

import java.util.List;

import com.dat.bit.csmis.entity.Holiday;
import com.dat.bit.csmis.entity.Holiday_CSV;



public interface HolidayDao {
	public void addAllHolidayDataToDB(List<Holiday_CSV> holiday_CSVs);
	public void addAllHolidayDatatoDBWithExcelFile(List<Holiday> holidays);
	public List<Holiday> getAllHolidayDataFromDB();
	public void deleteAll();
}
