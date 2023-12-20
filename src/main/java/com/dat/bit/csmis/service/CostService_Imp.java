package com.dat.bit.csmis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.CostDAO;
import com.dat.bit.csmis.entity.Cost;

@Service
public class CostService_Imp implements CostService{

	@Autowired
	private CostDAO costDAO;
	
	@Override
	@Transactional
	public Cost getCost() {
		// TODO Auto-generated method stub
		return costDAO.getCost();
	}

	@Override
	@Transactional
	public void saveCost(Cost cost, String staffId) {
		// TODO Auto-generated method stub
		costDAO.saveCost(cost, staffId);
	}

}
