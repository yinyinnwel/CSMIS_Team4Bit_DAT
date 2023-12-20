package com.dat.bit.csmis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dat.bit.csmis.dto.Staff_DTO;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class AdminController {
	
	@Autowired
	private StaffService staffService;
	
	public String adminListPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		List<Staff_DTO> admin_staff_DTO_List = staffService.findAllAdmin();
		
		model.addAttribute("staffs",admin_staff_DTO_List);
		model.addAttribute("loginUserName",loginUsername);
		
		return "adminList/admin";
	}
	
	@GetMapping("/admin_list")
	public String adminList(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return adminListPage(staffId,model);
	}

}
