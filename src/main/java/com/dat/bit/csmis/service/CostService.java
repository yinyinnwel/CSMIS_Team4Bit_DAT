package com.dat.bit.csmis.service;

import com.dat.bit.csmis.entity.Cost;

public interface CostService {

	public Cost getCost();
	
	public void saveCost(Cost cost,String staffId);
}
