package com.dat.bit.csmis.dao;

import java.util.List;

import com.dat.bit.csmis.entity.StaffDetail;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

public interface StaffDetailDAO {

	public StaffDetail findByStaffId(String id);

	public void saveStaffDetail(Staff_CSV staff_CSV);
	
	public void saveStaffDetailWithExcel(Staff_ECXCEL staff_ECXCEL);
	
	public void updateStaffDetail(StaffDetail staffDetail);
	
	public String checkOTPEmailIsContianedOrNot(String email);
	
	public List<String> getAllDepartmentName();
	
	public List<String> getAllTeamName();
	
	public String getEmailByStaffId(String staffId);
}
