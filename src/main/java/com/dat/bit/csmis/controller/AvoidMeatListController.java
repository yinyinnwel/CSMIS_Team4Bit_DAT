package com.dat.bit.csmis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.dto.AvoidMeat_DTO;
import com.dat.bit.csmis.dto.Staff_DTO;
import com.dat.bit.csmis.entity.AvoidMeat;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.AvoidMeatListService;
import com.dat.bit.csmis.service.AvoidMeatService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class AvoidMeatListController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private AvoidMeatService avoidMeatService;
	@Autowired
	private AvoidMeatListService avoidMeatListService;
	
	

	
	@GetMapping("/lunch_avoidMeatList")
	public String avoidMeatList(Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		return avoidMeatListPage(staffId,"","staffId", model);
	}
	
	@GetMapping("/lunch_avoidMeatList_search")
	public String avoidMeatListSearch(@RequestParam("searchValue")String searchValue,
									@RequestParam("filterBy")String filterBy,
									Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		
		return avoidMeatListPage(staffId, searchValue, filterBy, model);
	}

	
//==================== Other functions ================================================================
	
	public String avoidMeatListPage(String staffId,String searchValue,String filterBy,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		List<AvoidMeat> avoidMeats = new ArrayList<AvoidMeat>();
		
		if(filterBy.equals("staffId")) {
			avoidMeats = avoidMeatService.getAvoidMeatListByStaffId(searchValue);
		}else {//filterBy username
			avoidMeats = avoidMeatService.getAvoidMeatListByStaffName(searchValue);
		}
		
		List<AvoidMeat_DTO> avoidMeat_DTOs = avoidMeatListService.getAvoidMeatNameAndTotal();
		
		List<Staff_DTO> staff_DTOs = createStaff_DTOList(avoidMeats);
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("staff_avList", staff_DTOs);
		model.addAttribute("avoidMeat_DTOs",avoidMeat_DTOs);
		model.addAttribute("searchValue",searchValue);
		model.addAttribute("filterBy",filterBy);
		
		
		return "Lunch/avoidMeatList";
	}
	
	public List<Staff_DTO> createStaff_DTOList(List<AvoidMeat> avoidMeats){
		
		List<Staff_DTO> staff_DTOs = new ArrayList<Staff_DTO>();
		
		for(AvoidMeat aMeat : avoidMeats) {
			Staff staff = staffService.getStaffByStaff_Id(aMeat.getStaff_id());
			if(staff.getStatus() != 0) {
				Staff_DTO dto = new Staff_DTO();
				dto.setStaffId(staff.getStaffId());
				dto.setName(staff.getName());
				dto.setAv_createdAt(aMeat.getCreatedAt());
				dto.setAv_updatedAt(aMeat.getUpdatedAt());
				dto.setAvoidMeats(aMeat.getDescription());
				dto.setStatus(staff.getStatus());
				
				staff_DTOs.add(dto);
			}

		}
		return staff_DTOs;
	}

}
