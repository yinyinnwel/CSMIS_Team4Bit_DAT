package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.AllLunchListDAO;
import com.dat.bit.csmis.entity.All_Have_Lunch;
import com.dat.bit.csmis.entity.DoorLog_CSV;
import com.dat.bit.csmis.entity.DoorLog_EXCEL;

@Service
public class AllLunchListService_Imp implements AllLunchListService{
	
	@Autowired
	private AllLunchListDAO allLunchListDAO;

	@Override
	@Transactional
	public void saveTodayDoorTransactionsToDB(List<DoorLog_CSV> doorLog_CSVs) {
		allLunchListDAO.saveTodayDoorTransactionsToDB(doorLog_CSVs);
		
	}
	

	@Override
	@Transactional
	public void saveTodayDoorTransactionsToDBByExcelFile(List<DoorLog_EXCEL> doorLog_EXCELs) {
		allLunchListDAO.saveTodayDoorTransactionsToDBByExcelFile(doorLog_EXCELs);
		
	}

	@Override
	@Transactional
	public List<All_Have_Lunch> getTodayAllLunchTransactionListByOption(String option) {
		// TODO Auto-generated method stub
		return allLunchListDAO.getTodayAllLunchTransactionListByOption(option);
	}

	@Override
	@Transactional
	public List<All_Have_Lunch> searchTodayAllLunchTransactionList(String option, String searchValue, String filterBy) {
		// TODO Auto-generated method stub
		return allLunchListDAO.searchTodayAllLunchTransactionList(option, searchValue, filterBy);
	}

	@Override
	@Transactional
	public List<All_Have_Lunch> getAllLunchTransactionListByOptionAndDateRange(String option, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return allLunchListDAO.getAllLunchTransactionListByOptionAndDateRange(option, startDate, endDate);
	}

	@Override
	@Transactional
	public List<All_Have_Lunch> searchAllLunchTransactionListWithDateRange(String option, String searchValue,
			String filterBy, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return allLunchListDAO.searchAllLunchTransactionListWithDateRange(option, searchValue, filterBy, startDate, endDate);
	}


}
