package com.dat.bit.csmis.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dat.bit.csmis.dao.DashboardDAO;
import com.dat.bit.csmis.dto.Registered_Staff_DTO;
import com.dat.bit.csmis.entity.Department;

@Service
public class DashboardService_Imp implements DashboardService {
	
	@Autowired
	private DashboardDAO dashboardDAO;

	@Override
	@Transactional
	public List<Registered_Staff_DTO> getTodayLunchRegisteredStaffList() {
		// TODO Auto-generated method stub
		return dashboardDAO.getTodayLunchRegisteredStaffList();
	}

	@Override
	@Transactional
	public long getTotalDepartmentOfCompany() {
		// TODO Auto-generated method stub
		return dashboardDAO.getTotalDepartmentOfCompany();
	}

	@Override
	@Transactional
	public long getTotalTeamOfCompany() {
		// TODO Auto-generated method stub
		return dashboardDAO.getTotalTeamOfCompany();
	}

	@Override
	@Transactional
	public long getTotalDivisionOfCompany() {
		// TODO Auto-generated method stub
		return dashboardDAO.getTotalDivisionOfCompany();
	}

	@Override
	@Transactional
	public List<Department> getDepartmentDetailForGraph() {
		// TODO Auto-generated method stub
		return dashboardDAO.getDepartmentDetailForGraph();
	}

	@Override
	@Transactional
	public List<String> getEmailsFromStaffsWhoNotiOn() {
		// TODO Auto-generated method stub
		return dashboardDAO.getEmailsFromStaffsWhoNotiOn();
	}

}
