package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.AvoidMeatDAO;
import com.dat.bit.csmis.entity.AvoidMeat;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

@Service
public class AvoidMeatService_Imp implements AvoidMeatService{
	
	@Autowired
	private AvoidMeatDAO avoidMeatDAO;

	@Override
	@Transactional
	public void saveAvoidMeatData(Staff_CSV staff_CSV) {
		// TODO Auto-generated method stub
		avoidMeatDAO.saveAvoidMeatData(staff_CSV);
	}
	

	@Override
	@Transactional
	public void saveAvoidMeatData(Staff_ECXCEL staff_ECXCEL) {
		// TODO Auto-generated method stub
		avoidMeatDAO.saveAvoidMeatData(staff_ECXCEL);
	}

	@Override
	@Transactional
	public void updateAvoidMeatData(AvoidMeat avoidMeat) {
		avoidMeatDAO.updateAvoidMeatData(avoidMeat);
		
	}

	@Override
	@Transactional
	public AvoidMeat getAvoidMeatDataByStaffId(String staffId) {
		// TODO Auto-generated method stub
		return avoidMeatDAO.getAvoidMeatDataByStaffId(staffId);
	}

	@Override
	@Transactional
	public List<AvoidMeat> getAvoidMeatStaffData() {
		// TODO Auto-generated method stub
		return avoidMeatDAO.getAvoidMeatStaffData();
	}

	@Override
	@Transactional
	public List<AvoidMeat> getAvoidMeatListByStaffId(String searchValue) {
		// TODO Auto-generated method stub
		return avoidMeatDAO.getAvoidMeatListByStaffId(searchValue);
	}

	@Override
	@Transactional
	public List<AvoidMeat> getAvoidMeatListByStaffName(String searchValue) {
		// TODO Auto-generated method stub
		return avoidMeatDAO.getAvoidMeatListByStaffName(searchValue);
	}


}
