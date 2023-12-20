package com.dat.bit.csmis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dat.bit.csmis.dao.AvoidMeatListDAO;
import com.dat.bit.csmis.dto.AvoidMeat_DTO;
import com.dat.bit.csmis.entity.AvoidMeatList;

@Service
public class AvoidMeatListService_Imp implements AvoidMeatListService {
	
	@Autowired
	private AvoidMeatListDAO avoidMeatListDAO;

	@Override
	@Transactional
	public List<AvoidMeatList> getAllAvoidMeatList() {
		// TODO Auto-generated method stub
		return avoidMeatListDAO.getAllAvoidMeatList();
	}

	@Override
	@Transactional
	public void saveNewAvoidMeat(AvoidMeatList avoidMeat) {
		// TODO Auto-generated method stub
		avoidMeatListDAO.saveNewAvoidMeat(avoidMeat);
	}

	@Override
	@Transactional
	public void updateAvoidMeat(AvoidMeatList avoidMeat) {
		// TODO Auto-generated method stub
		avoidMeatListDAO.updateAvoidMeat(avoidMeat);
	}

	@Override
	@Transactional
	public List<AvoidMeatList> searchAvoidMeatListByName(String searchValue) {
		// TODO Auto-generated method stub
		return avoidMeatListDAO.searchAvoidMeatListByName(searchValue);
	}

	@Override
	@Transactional
	public List<String> getAllAvoidMeatName() {
		// TODO Auto-generated method stub
		return avoidMeatListDAO.getAllAvoidMeatName();
	}

	@Override
	@Transactional
	public AvoidMeatList getAvoidMeatById(int id) {
		// TODO Auto-generated method stub
		return avoidMeatListDAO.getAvoidMeatById(id);
	}

	@Override
	@Transactional
	public List<AvoidMeat_DTO> getAvoidMeatNameAndTotal() {
		// TODO Auto-generated method stub
		return avoidMeatListDAO.getAvoidMeatNameAndTotal();
	}

}
