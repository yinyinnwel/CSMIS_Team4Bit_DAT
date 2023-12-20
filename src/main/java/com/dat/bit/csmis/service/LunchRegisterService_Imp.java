package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.LunchRegisterDAO;
import com.dat.bit.csmis.entity.LunchRegister;

@Service
public class LunchRegisterService_Imp implements LunchRegisterService{
	
	@Autowired
	private LunchRegisterDAO lunchRegisterDAO;

	@Override
	@Transactional
	public List<LunchRegister> getAllLunchRegistrationForCurrentMonth() {
		// TODO Auto-generated method stub
		return lunchRegisterDAO.getAllLunchRegistrationForCurrentMonth();
	}

	@Override
	@Transactional
	public LunchRegister getLunchRegistrationByStaffId(String staffId,String registerFor) {
		// TODO Auto-generated method stub
		return lunchRegisterDAO.getLunchRegistrationByStaffId(staffId,registerFor);
	}


	@Override
	@Transactional
	public void saveLunchRegistration(int registeredId,String registerFor, String status, String staffId) {
		lunchRegisterDAO.saveLunchRegistration(registeredId,registerFor, status, staffId);
		
	}

	@Override
	@Transactional
	public List<LunchRegister> getLunchRegistratoinListByMonth(String month) {
		// TODO Auto-generated method stub
		return lunchRegisterDAO.getLunchRegistratoinListByMonth(month);
	}

	@Override
	@Transactional
	public List<LunchRegister> searchLunchRegistrationListByStaffId(String searchValue, String month) {
		// TODO Auto-generated method stub
		return lunchRegisterDAO.searchLunchRegistrationListByStaffId(searchValue, month);
	}

	@Override
	@Transactional
	public List<LunchRegister> searchLunchRegistrationListByStaffName( String searchValue,String month) {
		// TODO Auto-generated method stub
		return lunchRegisterDAO.searchLunchRegistrationListByStaffName(searchValue, month);
	}

}
