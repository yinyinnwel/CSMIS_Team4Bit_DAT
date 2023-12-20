package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.StaffDetailDAO;
import com.dat.bit.csmis.entity.StaffDetail;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

@Service
public class StaffDetailService_Imp implements StaffDetailService {
	
	@Autowired
	private StaffDetailDAO staffDetailDAO;

	@Override
	@Transactional
	public StaffDetail findByStaffId(String id) {
		
		return staffDetailDAO.findByStaffId(id);
	}

	@Override
	@Transactional
	public void saveStaffDetail(Staff_CSV staff_CSV) {
		// TODO Auto-generated method stub
		staffDetailDAO.saveStaffDetail(staff_CSV);
	}

	@Override
	@Transactional
	public void updateStaffDetail(StaffDetail staffDetail) {
		staffDetailDAO.updateStaffDetail(staffDetail);
		
	}

	@Override
	@Transactional
	public void saveStaffDetailWithExcel(Staff_ECXCEL staff_ECXCEL) {
		staffDetailDAO.saveStaffDetailWithExcel(staff_ECXCEL);
		
	}

	@Override
	@Transactional
	public String checkOTPEmailIsContianedOrNot(String email) {
		// TODO Auto-generated method stub
		return staffDetailDAO.checkOTPEmailIsContianedOrNot(email);
	}

	@Override
	@Transactional
	public List<String> getAllDepartmentName() {
		// TODO Auto-generated method stub
		return staffDetailDAO.getAllDepartmentName();
	}

	@Override
	@Transactional
	public List<String> getAllTeamName() {
		// TODO Auto-generated method stub
		return staffDetailDAO.getAllTeamName();
	}

	@Override
	@Transactional
	public String getEmailByStaffId(String staffId) {
		// TODO Auto-generated method stub
		return staffDetailDAO.getEmailByStaffId(staffId);
	}


	

}
