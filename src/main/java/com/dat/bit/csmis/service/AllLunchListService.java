package com.dat.bit.csmis.service;

import java.util.List;

import com.dat.bit.csmis.entity.All_Have_Lunch;
import com.dat.bit.csmis.entity.DoorLog_CSV;
import com.dat.bit.csmis.entity.DoorLog_EXCEL;

public interface AllLunchListService {
	
	public void saveTodayDoorTransactionsToDB(List<DoorLog_CSV> doorLog_CSVs);
	
	public void saveTodayDoorTransactionsToDBByExcelFile(List<DoorLog_EXCEL> doorLog_EXCELs);
	
	public List<All_Have_Lunch> getTodayAllLunchTransactionListByOption(String option);
	
	public List<All_Have_Lunch> searchTodayAllLunchTransactionList(String option,String searchValue,String filterBy);
	
	public List<All_Have_Lunch> getAllLunchTransactionListByOptionAndDateRange(String option,String startDate,String endDate);
	
	public List<All_Have_Lunch> searchAllLunchTransactionListWithDateRange(String option,String searchValue,String filterBy,String startDate,String endDate);
	

}
