package com.dat.bit.csmis.service;


import java.util.List;

import com.dat.bit.csmis.entity.AvoidMeat;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

public interface AvoidMeatService {

	public void saveAvoidMeatData(Staff_CSV staff_CSV);
	
	public void saveAvoidMeatData(Staff_ECXCEL staff_ECXCEL);
	
	public void updateAvoidMeatData(AvoidMeat avoidMeat);
	
	public AvoidMeat getAvoidMeatDataByStaffId(String staffId);
	
	public List<AvoidMeat> getAvoidMeatStaffData();

	public List<AvoidMeat> getAvoidMeatListByStaffId(String searchValue);
	
	public List<AvoidMeat> getAvoidMeatListByStaffName(String searchValue);
}
