package com.dat.bit.csmis.dao;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.dat.bit.csmis.dto.Staff_DTO;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.entity.StaffDetail;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;
import com.dat.bit.csmis.service.AvoidMeatService;
import com.dat.bit.csmis.service.StaffDetailService;

@Repository
public class StaffDAO_Imp implements StaffDAO {


	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public StaffDAO_Imp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Autowired
	private StaffDetailService staffDetailService;

	@Autowired 
	private AvoidMeatService avoidMeatService;


	@Override
	public int getTotalNumber_of_Staffs() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Staff> theQuery = currentSession.createQuery("from Staff",Staff.class);
		
		int total= theQuery.getResultList().size();
		
		
		
		return total;
	}

	@Override
	public List<Staff_DTO> findAll(int pageNumber) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<Staff_DTO> staff_DTO_list = new ArrayList<Staff_DTO>();
		
		int pageSize = 50;
		
		// create a query
		Query<Staff> theQuery = currentSession.createQuery("from Staff order by name", Staff.class);
		theQuery.setFirstResult((pageNumber - 1) * pageSize);
		theQuery.setMaxResults(pageSize);
		// execute query and get result list
		
		List<Staff> staffs = theQuery.getResultList();
		
		for(Staff staff : staffs) {
			StaffDetail staffDetail = staffDetailService.findByStaffId(staff.getStaffId());
			
			staff_DTO_list.add(createStaff_DTO_Object(staff,staffDetail));
		}
		
		return staff_DTO_list;
	}
	

	@Override
	public List<Staff_DTO> findBy(String searchValue, String filterBy, String filterStatus,int pageNumber) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<Staff_DTO> staff_DTO_list = new ArrayList<Staff_DTO>();
		
		int pageSize = 50;
		
		Query<Staff> theQuery = null;
		Query<StaffDetail> theQuery1 = null;
		
		switch (filterBy) {
		case "staffId":{
			theQuery = currentSession.createQuery("from Staff where staffId like '%"+searchValue+"%' order by name", Staff.class);
			staff_DTO_list = searchFrom_StaffTable(theQuery,pageNumber,pageSize);
			
		}break;
		case "username":{
			theQuery = currentSession.createQuery("from Staff where name like '%"+searchValue+"%' order by name", Staff.class);
			staff_DTO_list = searchFrom_StaffTable(theQuery,pageNumber,pageSize);
			
		}break;
		case "status":{
			theQuery = currentSession.createQuery("from Staff where status = "+(filterStatus.equals("active") ? 1 : 0)+" order by name", Staff.class);
			staff_DTO_list = searchFrom_StaffTable(theQuery,pageNumber,pageSize);
			
		}break;
		case "department":{
			theQuery1 = currentSession.createQuery("from StaffDetail where department like '%"+searchValue+"%' order by department", StaffDetail.class);
			staff_DTO_list = searchFrom_StaffDetailTable(theQuery1,pageNumber,pageSize);
			
		}break;
		case "team":{
			theQuery1 = currentSession.createQuery("from StaffDetail where team like '%"+searchValue+"%' order by team", StaffDetail.class);
			staff_DTO_list = searchFrom_StaffDetailTable(theQuery1,pageNumber,pageSize);
			
		}break;
		default:
			break;
		}
		
		
		return staff_DTO_list;
	}

	@Override
	public void saveStaff(Staff_CSV staff_CSV) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Staff> staff_query = currentSession.createQuery("from Staff where staffId = '"+staff_CSV.getStaffId()+"'",Staff.class);
		
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//"$2a$10$hSbL0Kmw7q9kXaNX7lhos.q9m9YQm/HQ8p5zGYyrJrS2FGtiwtczK"
		
		if(staff_query.getResultList().isEmpty()) {//<= if new staff
			Staff staff = new Staff();
			staff.setStaffId(staff_CSV.getStaffId());
			staff.setName(staff_CSV.getName());
			staff.setDoorLogNo(staff_CSV.getDoorLogNo() + "");
			staff.setStatus(1);
			//staff.setPassword(encoder.encode(staff_CSV.getStaffId()));
			staff.setPassword("$2a$10$hSbL0Kmw7q9kXaNX7lhos.q9m9YQm/HQ8p5zGYyrJrS2FGtiwtczK");//123
			staff.setRole("ROLE_EMPLOYEE");

			// ------- Save Staff to staff table one by one ------//
			currentSession.saveOrUpdate(staff);
			
			staffDetailService.saveStaffDetail(staff_CSV);
			avoidMeatService.saveAvoidMeatData(staff_CSV);
		}
		else {//<== if staff exists inside DB
			Staff original_staff = staff_query.getSingleResult();
			original_staff.setName(staff_CSV.getName());
			original_staff.setDoorLogNo(staff_CSV.getDoorLogNo()+"");
			original_staff.setStatus(1);
			
			//-------- Update Staff to staff Table -------
			currentSession.update(original_staff);
			
			//-------- Update Staff Detail to staff_detail table ----
			StaffDetail staffDetail = staffDetailService.findByStaffId(staff_CSV.getStaffId());
			staffDetail.setDivision(staff_CSV.getDivision());
			staffDetail.setDepartment(staff_CSV.getDepartment());
			staffDetail.setTeam(staff_CSV.getTeam());
			staffDetail.setEmail(staff_CSV.getEmail());//<== don't forget to change with staff_CSV.getEmail();
			
			staffDetailService.updateStaffDetail(staffDetail);
		}


	}
	
	@Override
	public void saveStaffWithExcelFile(Staff_ECXCEL staff_ECXCEL) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Staff> staff_query = currentSession.createQuery("from Staff where staffId = '"+staff_ECXCEL.getStaffId()+"'",Staff.class);
		
		if(staff_query.getResultList().isEmpty()) {//<= if new staff
			Staff staff = new Staff();
			staff.setStaffId(staff_ECXCEL.getStaffId());
			staff.setName(staff_ECXCEL.getName());
			staff.setDoorLogNo(staff_ECXCEL.getDoorLogNo() + "");
			staff.setStatus(1);
			//staff.setPassword(encoder.encode(staff_CSV.getStaffId()));
			staff.setPassword("$2a$10$hSbL0Kmw7q9kXaNX7lhos.q9m9YQm/HQ8p5zGYyrJrS2FGtiwtczK");//123
			staff.setRole("ROLE_EMPLOYEE");

			// ------- Save Staff to staff table one by one ------//
			currentSession.saveOrUpdate(staff);
			
			staffDetailService.saveStaffDetailWithExcel(staff_ECXCEL);
			avoidMeatService.saveAvoidMeatData(staff_ECXCEL);
			
		}else {//<== if staff exists inside DB
			Staff original_staff = staff_query.getSingleResult();
			original_staff.setName(staff_ECXCEL.getName());
			original_staff.setDoorLogNo(staff_ECXCEL.getDoorLogNo()+"");
			original_staff.setStatus(1);
			
			//-------- Update Staff to staff Table -------
			currentSession.update(original_staff);
			
			//-------- Update Staff Detail to staff_detail table ----
			StaffDetail staffDetail = staffDetailService.findByStaffId(staff_ECXCEL.getStaffId());
			staffDetail.setDivision(staff_ECXCEL.getDivision());
			staffDetail.setDepartment(staff_ECXCEL.getDepartment());
			staffDetail.setTeam(staff_ECXCEL.getTeam());
			staffDetail.setEmail(staff_ECXCEL.getEmail());//<== don't forget to change with staff_CSV.getEmail();
			
			staffDetailService.updateStaffDetail(staffDetail);
		}
		
	}

	
	
	//Staff_DTO [id=0, role=ADMIN, division=DIV002, staffId=26-00241, name=null,
	//doorLogNo=30240, department=Offshore Development Dept-1, status=1, team=Off-Dept1,
	//email=-----, email_noti=0, password=null, createdAt=null, createdBy=null, updatedAt=null, updatedBy=null, total=0]
	@Override
	public String updateStaff(Staff_DTO staff_DTO) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		String message = "";
		
		Query<Staff> staff_query = currentSession.createQuery("from Staff where staffId = '"+staff_DTO.getStaffId()+"'",Staff.class);
		Staff staff = staff_query.getSingleResult();
		StaffDetail staffDetail = staffDetailService.findByStaffId(staff_DTO.getStaffId());
//		
//		if(staff_DTO.getRole().equals("ROLE_ADMIN")) {
//			Query<Staff> query = currentSession.createQuery("from Staff where role = 'ROLE_ADMIN'",Staff.class);
//			if(query.getResultList().size() == 3) {//<= Admin Role reach limit(3)
//				message = "The specifid number of admins has been reached!";
//			}else {//Otherwise
//				message = "Update Successfully!";
//				staff.setRole(staff_DTO.getRole());
//				staff.setStatus(staff_DTO.getStatus());
//				
//				
//				staffDetail.setDivision(staff_DTO.getDivision());
//				staffDetail.setDepartment(staff_DTO.getDepartment());
//				staffDetail.setTeam(staff_DTO.getTeam());
//				staffDetail.setUpdatedAt(new Timestamp(new Date().getTime()));
//				staffDetail.setUpdatedBy(staff_DTO.getUpdatedBy());
//				
//				
//				currentSession.update(staff);//<= update staff table
//				staffDetailService.updateStaffDetail(staffDetail);//<= update staff_detail table
//			}
//		}else{
			message = "Update Successfully!";
			staff.setRole(staff_DTO.getRole());
			staff.setStatus(staff_DTO.getStatus());
			
			
			staffDetail.setDivision(staff_DTO.getDivision());
			staffDetail.setDepartment(staff_DTO.getDepartment());
			staffDetail.setTeam(staff_DTO.getTeam());
			staffDetail.setUpdatedAt(new Timestamp(new Date().getTime()));
			staffDetail.setUpdatedBy(staff_DTO.getUpdatedBy());
			
			
			currentSession.update(staff);//<= update staff table
			staffDetailService.updateStaffDetail(staffDetail);//<= update staff_detail table
		//}
		
		
		return message;
		
	}
	
	@Override
	public Staff getStaffByStaff_Id(String staffId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Staff> staff_query = currentSession.createQuery("from Staff where staffId = '"+staffId+"'",Staff.class);
		Staff staff = staff_query.getSingleResult();
		
		return staff;
	}

	

	@Override
	public List<Staff_DTO> findAllAdmin() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<Staff_DTO> staff_DTO_list = new ArrayList<Staff_DTO>();
		
		Query<Staff> staff_query = currentSession.createQuery("from Staff where role = 'ROLE_ADMIN' order by name",Staff.class);
		
		List<Staff> staff_admin_List = staff_query.getResultList();
		
		for(Staff staff : staff_admin_List) {
			StaffDetail staffDetail = staffDetailService.findByStaffId(staff.getStaffId());
			
			staff_DTO_list.add(createStaff_DTO_Object(staff,staffDetail));
		}
		
		return staff_DTO_list;
	}
	

	@Override
	public void updateStaffPassword(String staffId, String newpassword) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Staff> staff_query = currentSession.createQuery("from Staff where staffId = '"+staffId+"'",Staff.class);
		Staff staff = staff_query.getSingleResult();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(newpassword);
		
		staff.setPassword(encodedPassword);
		
		currentSession.update(staff);
	}
	
	
	@Override
	public List<String> getAllStaffId() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<String> staffIdList = new ArrayList<>();
		
		try {
			Query<String> staff_query = currentSession.createQuery("SELECT s.staffId from Staff s",String.class);
			staffIdList = staff_query.getResultList();
		} catch (Exception e) {
			System.out.println("Error from getAllStaffId of StaffDAO_IMP!");
			System.out.println(e.getMessage());
		}
		
		return staffIdList;
	}



	@Override
	public void updateResignedStaff(String staffId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		try {
			Query<Staff> staff_query = currentSession.createQuery("from Staff where staffId = '"+staffId+"'",Staff.class);
			Staff staff = staff_query.getSingleResult();
			
			//=== update status to 0(Resigned)====
			staff.setStatus(0);
			
			currentSession.update(staff);
			
		} catch (Exception e) {
			System.out.println("Error from updateResignedStaff of StaffDAO_IMP!");
			System.out.println(e.getMessage());
		}
	}
	
	
//************************* Other Service Method ***************************************************************
	
	private List<Staff_DTO> searchFrom_StaffTable(Query<Staff> theQuery,int pageNumber,int pageSize) {
		List<Staff_DTO> staff_DTO_list = new ArrayList<Staff_DTO>();
		
		int total= theQuery.getResultList().size();
		theQuery.setFirstResult((pageNumber - 1) * pageSize);
		theQuery.setMaxResults(pageSize);
		
		List<Staff> staffs = theQuery.getResultList();
		for(Staff staff : staffs) {
			StaffDetail staffDetail = staffDetailService.findByStaffId(staff.getStaffId());
			
			Staff_DTO staff_DTO = createStaff_DTO_Object(staff,staffDetail);
			staff_DTO.setTotal(total);
			
			staff_DTO_list.add(staff_DTO);
		}
		
		return staff_DTO_list;
	}
	
	private List<Staff_DTO> searchFrom_StaffDetailTable(Query<StaffDetail> theQuery,int pageNumber,int pageSize) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		List<Staff_DTO> staff_DTO_list = new ArrayList<Staff_DTO>();
		
		int total= theQuery.getResultList().size();
		theQuery.setFirstResult((pageNumber - 1) * pageSize);
		theQuery.setMaxResults(pageSize);
		
		List<StaffDetail> staffDetails = theQuery.getResultList();
		for(StaffDetail staffDetail : staffDetails) {
			Staff staff = currentSession.get(Staff.class, staffDetail.getStaff_id());
			
			Staff_DTO staff_DTO = createStaff_DTO_Object(staff,staffDetail);
			staff_DTO.setTotal(total);
			
			staff_DTO_list.add(staff_DTO);
		}
		
		return staff_DTO_list;
	}

	
	private Staff_DTO createStaff_DTO_Object(Staff staff, StaffDetail staffDetail) {
		
		Staff_DTO staff_DTO = new Staff_DTO();
		staff_DTO.setRole(staff.getRole());
		staff_DTO.setDivision(staffDetail.getDivision());
		staff_DTO.setStaffId(staff.getStaffId());
		staff_DTO.setName(staff.getName());
		staff_DTO.setPassword(staff.getPassword());
		staff_DTO.setDoorLogNo(staff.getDoorLogNo());
		staff_DTO.setDepartment(staffDetail.getDepartment());
		staff_DTO.setStatus(staff.getStatus());
		staff_DTO.setTeam(staffDetail.getTeam());
		staff_DTO.setEmail(staffDetail.getEmail());
		staff_DTO.setEmail_noti(staffDetail.getEmail_noti());
		staff_DTO.setCreatedAt(staffDetail.getCreatedAt());
		staff_DTO.setCreatedBy(staffDetail.getCreatedBy());
		staff_DTO.setUpdatedAt(staffDetail.getUpdatedAt());
		staff_DTO.setUpdatedBy(staffDetail.getUpdatedBy());
		
		return staff_DTO;
	}









}
