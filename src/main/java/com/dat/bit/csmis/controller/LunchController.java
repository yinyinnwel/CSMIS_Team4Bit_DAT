package com.dat.bit.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dat.bit.csmis.entity.Cost;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.CostService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class LunchController {
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private CostService costService;
	
	
	@GetMapping("/lunch")
	public String Lunch(Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		try {
			Cost cost = costService.getCost();
			
			model.addAttribute("loginUserName",loginUsername);
			model.addAttribute("cost",cost);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("From Get Cost : "+e.getMessage());
		}
		
		return "Lunch/lunch";
	}
	
	@PostMapping("/lunch_edit_cost")
	public String LunchEditCost(@ModelAttribute("cost")Cost cost,Authentication authentication) {
		String staffId = authentication.getName();
		
		try {
			costService.saveCost(cost, staffId);
		} catch (Exception e) {
			System.out.println("From Update Cost : "+e.getMessage());
		}
		
		return "redirect:/DAT/lunch";
	}

}
