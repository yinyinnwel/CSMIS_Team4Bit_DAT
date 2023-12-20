package com.dat.bit.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class AboutUsController {
	
	@Autowired
	private StaffService staffService;
	
	
	@GetMapping("/about")
	public String AboutUsPage(Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		model.addAttribute("loginUserName",loginUsername);
		
		return "about/about";
	}


}
