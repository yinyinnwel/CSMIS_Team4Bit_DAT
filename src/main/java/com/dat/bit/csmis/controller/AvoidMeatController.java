package com.dat.bit.csmis.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.entity.AvoidMeatList;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.AvoidMeatListService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class AvoidMeatController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private AvoidMeatListService avoidMeatListService;
	
	private String noti_message="",updateMessage_class="notiMessageBox";
	
	public String avoidMeatPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
	
		List<AvoidMeatList> avoidMeatLists = avoidMeatListService.getAllAvoidMeatList();
		
		List<String> avoidMeatsNames = new ArrayList<>();
		
		for(AvoidMeatList avoidMeatList : avoidMeatLists) {
			avoidMeatsNames.add(avoidMeatList.getName());
		}
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("avoidMeatLists",avoidMeatLists);
		model.addAttribute("avoidMeatsNames",avoidMeatsNames);
		model.addAttribute("searchValue","");
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updateMessage_class = "notiMessageBox";
		
		return "avoidMeat/avoidMeat";
	}
	
	@GetMapping("/avoid_meat")
	public String avoidMeat(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return avoidMeatPage(staffId, model);
	}
	
	@PostMapping("/avoid_meat_add_update_meat")
	public String addNewAvoidMeat(@RequestParam("avoidMeatId")int avoidMeatId,
									@RequestParam("meatName")String meatName,
									@RequestParam("status")int status,
									Authentication authentication) {
		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		if(avoidMeatId == -1) {//Add New Avoid Meat
			AvoidMeatList avoidMeat = new AvoidMeatList();
			avoidMeat.setName(meatName);
			avoidMeat.setStatus(status);
			avoidMeat.setCreatedAt(new Timestamp(new Date().getTime()));
			avoidMeat.setCreatedBy("("+staffId+") "+loginUsername);
			
			avoidMeatListService.saveNewAvoidMeat(avoidMeat);
			
			//============ Show success noti dialog ===============
			
			updateMessage_class = "notiMessageBox success open";
			noti_message = "New avoid meat is added successfully.";
		}else {//update Avoid Meat
			AvoidMeatList avoidMeat = avoidMeatListService.getAvoidMeatById(avoidMeatId);
			avoidMeat.setName(meatName);
			avoidMeat.setStatus(status);
			avoidMeat.setUpdatedAt(new Timestamp(new Date().getTime()));
			avoidMeat.setUpdatedBy("("+staffId+") "+loginUsername);
			
			avoidMeatListService.updateAvoidMeat(avoidMeat);
			
			//============ Show success noti dialog ===============
			
			updateMessage_class = "notiMessageBox success open";
			noti_message = "You have successfully updated.";
		}
		
		
		return "redirect:/DAT/avoid_meat";
	}
	
	
	@GetMapping("/avoid_meat_search")
	public String searchAvoidMeat(@RequestParam("searchValue")String searchValue,
									Authentication authentication,Model model) {
		
		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		List<AvoidMeatList> avoidMeatLists = avoidMeatListService.searchAvoidMeatListByName(searchValue);
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("avoidMeatLists",avoidMeatLists);
		model.addAttribute("searchValue",searchValue);
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updateMessage_class = "notiMessageBox";
		
		return "avoidMeat/avoidMeat";
	}

}
