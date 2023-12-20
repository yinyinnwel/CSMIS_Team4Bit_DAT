package com.dat.bit.csmis.dao;

import java.util.List;

import com.dat.bit.csmis.entity.LunchRegister;

public interface LunchRegisterDAO {

	
	public List<LunchRegister> getAllLunchRegistrationForCurrentMonth();
	
	public LunchRegister getLunchRegistrationByStaffId(String staffId,String registerFor);
	
	public void saveLunchRegistration(int registeredId,String registerFor,String status,String staffId);
	
	public List<LunchRegister> getLunchRegistratoinListByMonth(String month);
	
	public List<LunchRegister> searchLunchRegistrationListByStaffId(String searchValue,String month);
	
	public List<LunchRegister> searchLunchRegistrationListByStaffName(String searchValue,String month);
}
