package com.dat.bit.csmis.service;

import java.util.List;

import com.dat.bit.csmis.dto.Registered_Staff_DTO;
import com.dat.bit.csmis.entity.Department;

public interface DashboardService {

	public List<Registered_Staff_DTO> getTodayLunchRegisteredStaffList();
	
	public long getTotalDepartmentOfCompany();
	
	public long getTotalTeamOfCompany();
	
	public long getTotalDivisionOfCompany();
	
	public List<Department> getDepartmentDetailForGraph();
	
	public List<String> getEmailsFromStaffsWhoNotiOn();
}
