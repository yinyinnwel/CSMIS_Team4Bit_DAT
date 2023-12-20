package com.dat.bit.csmis.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.dto.AvoidMeat_DTO;
import com.dat.bit.csmis.entity.AvoidMeat;
import com.dat.bit.csmis.entity.AvoidMeatList;

@Repository
public class AvoidMeatListDAO_Imp implements AvoidMeatListDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public AvoidMeatListDAO_Imp(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public List<AvoidMeatList> getAllAvoidMeatList() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<AvoidMeatList> avoidMeatLists = new ArrayList<AvoidMeatList>();
		
		try {
			Query<AvoidMeatList> query = currentSession.createQuery("from AvoidMeatList order by name",AvoidMeatList.class);
			
			avoidMeatLists = query.getResultList();
		} catch (Exception e) {
			System.out.println("Error from getAllAvoidMeatList!");
			System.out.println(e.getMessage());
		}
		
		return avoidMeatLists;
	}

	@Override
	public void saveNewAvoidMeat(AvoidMeatList avoidMeat) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.save(avoidMeat);
	}

	@Override
	public void updateAvoidMeat(AvoidMeatList avoidMeat) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.update(avoidMeat);
		
		if(avoidMeat.getStatus() == 0) {//Remove inactive avoid meat from staff's avoid meat list
			Query<AvoidMeat> theQuery = currentSession.createQuery("from AvoidMeat where description is not null and description != ''", AvoidMeat.class);
			List<AvoidMeat> avoidMeats = theQuery.getResultList();
			
			for(AvoidMeat avoid_meat : avoidMeats) {
				List<String> avoidMeatList = new ArrayList<>(Arrays.asList(avoid_meat.getDescription().split(",")));
				//==== remove inactive meat ======
				avoidMeatList.remove(avoidMeat.getName());
				//===== create new avoid meat list string ====
				String updatedAvoidMeatsListString = "";
				for(String meat : avoidMeatList) {
					updatedAvoidMeatsListString += meat+",";
				}
				
				updatedAvoidMeatsListString = updatedAvoidMeatsListString.substring(0,updatedAvoidMeatsListString.length()-1);
				
				avoid_meat.setDescription(updatedAvoidMeatsListString);
				
				currentSession.update(avoid_meat);
			}
			
			
		}
		
		
	}

	@Override
	public List<AvoidMeatList> searchAvoidMeatListByName(String searchValue) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<AvoidMeatList> avoidMeatLists = new ArrayList<AvoidMeatList>();
		
		try {
			Query<AvoidMeatList> query = currentSession.createQuery("from AvoidMeatList "
					+"where name like '%"+searchValue+"%'"
					+ "order by name",AvoidMeatList.class);
			
			avoidMeatLists = query.getResultList();
		} catch (Exception e) {
			System.out.println("Error from searchAvoidMeatListByName!");
			System.out.println(e.getMessage());
		}
		
		return avoidMeatLists;
	}

	@Override
	public List<String> getAllAvoidMeatName() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<String> avoidMeatNamelist = new ArrayList<>();
		
		try {
			Query<String> query = currentSession.createQuery("select av.name from AvoidMeatList av where av.status = 1 order by name",String.class);
			
			avoidMeatNamelist = query.getResultList();
		} catch (Exception e) {
			System.out.println("Error from getAllAvoidMeatName!");
			System.out.println(e.getMessage());
		}
		
		return avoidMeatNamelist;
	}

	@Override
	public AvoidMeatList getAvoidMeatById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		AvoidMeatList avoidMeat = new AvoidMeatList();
		try {
			Query<AvoidMeatList> query = currentSession.createQuery("from AvoidMeatList where id = "+id,AvoidMeatList.class);
			avoidMeat = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return avoidMeat;
	}

	@Override
	public List<AvoidMeat_DTO> getAvoidMeatNameAndTotal() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<AvoidMeat_DTO> avoidMeat_DTOs = new ArrayList<>();
		
		try {
			List<String> avoidMeatNamelist = new ArrayList<>();
			
			Query<String> query = currentSession.createQuery("select av.name from AvoidMeatList av where av.status = 1 order by name",String.class);
			avoidMeatNamelist = query.getResultList();
			
			
			for(String meat : avoidMeatNamelist) {
				
				Query<Long> query1 = currentSession.createQuery("select count(description) from AvoidMeat "
						+ "where description like '%"+meat+"%'",Long.class);
				
				AvoidMeat_DTO avoidMeat_DTO = new AvoidMeat_DTO();
				avoidMeat_DTO.setName(meat);
				avoidMeat_DTO.setTotal(Integer.parseInt(query1.getSingleResult()+""));
				
				avoidMeat_DTOs.add(avoidMeat_DTO);
			}
			
		} catch (Exception e) {
			System.out.println("Error from getAvoidMeatNameAndTotal!");
			System.out.println(e.getMessage());
		}
		return avoidMeat_DTOs;
	}

}
