package com.dat.bit.csmis.service;

import java.util.List;

import com.dat.bit.csmis.dto.Staff_DTO;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;

public interface StaffService {
	
	public int getTotalNumber_of_Staffs();
	
	public List<Staff_DTO> findAll(int pageNumber);
	
	public List<Staff_DTO> findBy(String searchValue,String filterBy,String filterStatus,int pageNumber);
	
	public void saveStaff(Staff_CSV staff_CSV);
	
	public void saveStaffWithExcelFile(Staff_ECXCEL staff_ECXCEL);
	
	public String updateStaff(Staff_DTO staff_DTO);
	
	public Staff getStaffByStaff_Id(String staffId);
	
	public List<Staff_DTO> findAllAdmin();
	
	public void updateStaffPassword(String staffId,String newpassword);
	
	public List<String> getAllStaffId();
	
	public void updateResignedStaff(String staffId);
	
}
