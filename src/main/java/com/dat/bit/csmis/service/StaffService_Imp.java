package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.StaffDAO;
import com.dat.bit.csmis.dto.Staff_DTO;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

@Service
public class StaffService_Imp implements StaffService {
	
	@Autowired
	private StaffDAO staffDAO;


	@Override
	@Transactional
	public int getTotalNumber_of_Staffs() {
		// TODO Auto-generated method stub
		return staffDAO.getTotalNumber_of_Staffs();
	}

	@Override
	@Transactional
	public List<Staff_DTO> findAll(int pageNumber) {
		return staffDAO.findAll(pageNumber);
	}
	


	@Override
	@Transactional
	public List<Staff_DTO> findBy(String searchValue, String filterBy, String filterStatus,int pageNumber) {
		// TODO Auto-generated method stub
		return staffDAO.findBy(searchValue, filterBy, filterStatus, pageNumber);
	}


	@Override
	@Transactional
	public void saveStaff(Staff_CSV staff_CSV) {
		staffDAO.saveStaff(staff_CSV);
		
	}
	
	@Override
	@Transactional
	public void saveStaffWithExcelFile(Staff_ECXCEL staff_ECXCEL) {
		staffDAO.saveStaffWithExcelFile(staff_ECXCEL);
		
	}

	@Override
	@Transactional
	public String updateStaff(Staff_DTO staff_DTO) {
		// TODO Auto-generated method stub
		return staffDAO.updateStaff(staff_DTO);
	}

	@Override
	@Transactional
	public Staff getStaffByStaff_Id(String staffId) {
		// TODO Auto-generated method stub
		return staffDAO.getStaffByStaff_Id(staffId);
	}

	@Override
	@Transactional
	public List<Staff_DTO> findAllAdmin() {
		// TODO Auto-generated method stub
		return staffDAO.findAllAdmin();
	}

	@Override
	@Transactional
	public void updateStaffPassword(String staffId, String newpassword) {
		// TODO Auto-generated method stub
		staffDAO.updateStaffPassword(staffId, newpassword);
	}

	@Override
	@Transactional
	public List<String> getAllStaffId() {
		// TODO Auto-generated method stub
		return staffDAO.getAllStaffId();
	}

	@Override
	@Transactional
	public void updateResignedStaff(String staffId) {
		// TODO Auto-generated method stub
		staffDAO.updateResignedStaff(staffId);
	}

	






	
}
