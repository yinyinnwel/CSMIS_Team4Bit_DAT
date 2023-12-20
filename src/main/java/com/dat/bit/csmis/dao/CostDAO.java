package com.dat.bit.csmis.dao;

import com.dat.bit.csmis.entity.Cost;

public interface CostDAO {
	
	public Cost getCost();
	
	public void saveCost(Cost cost,String staffId);

}
