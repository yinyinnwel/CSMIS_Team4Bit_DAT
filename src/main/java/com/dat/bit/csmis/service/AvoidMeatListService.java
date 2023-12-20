package com.dat.bit.csmis.service;

import java.util.List;

import com.dat.bit.csmis.dto.AvoidMeat_DTO;
import com.dat.bit.csmis.entity.AvoidMeatList;

public interface AvoidMeatListService {

	public List<AvoidMeatList> getAllAvoidMeatList();
	
	public void saveNewAvoidMeat(AvoidMeatList avoidMeat);
	
	public void updateAvoidMeat(AvoidMeatList avoidMeat);
	
	public List<AvoidMeatList> searchAvoidMeatListByName(String searchValue);
	
	public List<String> getAllAvoidMeatName();
	
	public AvoidMeatList getAvoidMeatById(int id);
	
	public List<AvoidMeat_DTO> getAvoidMeatNameAndTotal();
}
