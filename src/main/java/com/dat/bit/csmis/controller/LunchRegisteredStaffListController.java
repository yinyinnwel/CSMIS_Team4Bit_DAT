package com.dat.bit.csmis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.dto.Lunch_Register_Staff_DTO;
import com.dat.bit.csmis.entity.LunchRegister;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.entity.StaffDetail;
import com.dat.bit.csmis.service.LunchRegisterService;
import com.dat.bit.csmis.service.StaffDetailService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class LunchRegisteredStaffListController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private StaffDetailService staffDetailService;
	@Autowired
	private LunchRegisterService lunchRegisterService;
	
	private String monthValue = "";
	
	public String lunchRegisteredStaffListPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		List<LunchRegister> lunchRegisters = lunchRegisterService.getLunchRegistratoinListByMonth(monthValue);
	
		List<Lunch_Register_Staff_DTO> staff_list = createLunchStaffDTOList(lunchRegisters);
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("staff_list", staff_list);
		model.addAttribute("monthValue",monthValue);
		model.addAttribute("searchValue","");
		model.addAttribute("filterBy","staffId");
		
		monthValue = "";
		
		return "Lunch/lunchRegisteredStaffList";
	}
	
	
	@GetMapping("/lunch_registered_staff_list")
	public String lunchRegisteredStaffList(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return lunchRegisteredStaffListPage(staffId,model);
	}
	
	@PostMapping("/lunch_registered_staff_list_search_month")
	public String searchByMonth(@RequestParam("monthPicker")String monthPicker,Model model) {
		
		
		monthValue = monthPicker;
		
		return "redirect:/DAT/lunch_registered_staff_list";
	}
	
	@GetMapping("/lunch_registered_staff_list_search")
	public String searchById_Name(@RequestParam("searchValue")String searchValue,
								@RequestParam("filterBy")String filterBy,
								@RequestParam("tempMonthPicker")String tempMonthPicker,
								Authentication authentication,Model model) {
		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		List<LunchRegister> lunchRegisters = new ArrayList<LunchRegister>();
		
		if(filterBy.equals("staffId")) {//search By staff Id
			lunchRegisters = lunchRegisterService.searchLunchRegistrationListByStaffId(searchValue, tempMonthPicker);
		}else {//search By Username
			lunchRegisters = lunchRegisterService.searchLunchRegistrationListByStaffName(searchValue, tempMonthPicker);
		}
		
		List<Lunch_Register_Staff_DTO> staff_list = createLunchStaffDTOList(lunchRegisters);
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("staff_list", staff_list);
		model.addAttribute("monthValue",tempMonthPicker);
		model.addAttribute("searchValue",searchValue);
		model.addAttribute("filterBy",filterBy);
		
		return "Lunch/lunchRegisteredStaffList";
	}
	
	
	
	//===================== Other Functions ===========================================
	
	public List<Lunch_Register_Staff_DTO> createLunchStaffDTOList(List<LunchRegister> lunchRegisters){
		List<Lunch_Register_Staff_DTO> list = new ArrayList<Lunch_Register_Staff_DTO>();
		
		for(LunchRegister register : lunchRegisters) {
			Staff staff = staffService.getStaffByStaff_Id(register.getStaffId());
			
			StaffDetail staffDetail = staffDetailService.findByStaffId(register.getStaffId());
			
			Lunch_Register_Staff_DTO staff_DTO = new Lunch_Register_Staff_DTO();
			staff_DTO.setStaffId(staff.getStaffId());
			staff_DTO.setName(staff.getName());
			staff_DTO.setDepartment(staffDetail.getDepartment());
			staff_DTO.setTeam(staffDetail.getTeam());
			staff_DTO.setStatus(staff.getStatus());
			staff_DTO.setRole(staff.getRole());
			staff_DTO.setLunch_register_id(register.getId());
			staff_DTO.setRegistered_date(register.getRegistered_date()+"");
			staff_DTO.setRegister_for(register.getRegister_for()+"");
			staff_DTO.setUpdated_date(register.getUpdated_date()+"");
			staff_DTO.setRegister_status(register.getRegister_status());
			
			list.add(staff_DTO);
		}
		
		return list;
	}

}
