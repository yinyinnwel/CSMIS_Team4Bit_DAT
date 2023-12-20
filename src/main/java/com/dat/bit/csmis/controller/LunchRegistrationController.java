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

import com.dat.bit.csmis.entity.AvoidMeat;
import com.dat.bit.csmis.entity.Holiday;
import com.dat.bit.csmis.entity.LunchRegister;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.AvoidMeatListService;
import com.dat.bit.csmis.service.AvoidMeatService;
import com.dat.bit.csmis.service.HolidayService;
import com.dat.bit.csmis.service.LunchRegisterService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class LunchRegistrationController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private HolidayService holidayService;
	@Autowired
	private LunchRegisterService lunchRegisterService;
	@Autowired
	private AvoidMeatService avoidMeatService;
	@Autowired
	private AvoidMeatListService avoidMeatListService;
	
	private String current_next_option="current",noti_message="",updateMessage_class="notiMessageBox";
	
	
	public String lunchRegistrationPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		//========== Get Holidays
		List<Holiday> holidays=holidayService.getAllHolidayDataFromDB();
		List<String> holidaysArrayHidden = new ArrayList<String>();
		
		for(Holiday h : holidays) {
			holidaysArrayHidden.add(h.getDate());
		}
		//========= Get avoid meat list
		List<String> avoidMeatNameList = avoidMeatListService.getAllAvoidMeatName();
		
		AvoidMeat avoidMeat = avoidMeatService.getAvoidMeatDataByStaffId(staffId);
		//=========== Get lunch registered status
		LunchRegister lunchRegister = lunchRegisterService.getLunchRegistrationByStaffId(staffId,current_next_option);

		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("lunchRegister",lunchRegister);
		model.addAttribute("holidaysArray", holidaysArrayHidden);
		model.addAttribute("avoidMeatNameList",avoidMeatNameList);
		model.addAttribute("avoidMeatList",avoidMeat.getDescription());
		model.addAttribute("current_next_option",current_next_option);
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		current_next_option = "current";
		updateMessage_class = "notiMessageBox";
		
		return "Lunch/lunchRegistration";
	}
	
	@GetMapping("/lunch_registration")
	public String lunchRegistration(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return lunchRegistrationPage(staffId, model);
	}
	
	@PostMapping("/lunch_registration_submit")
	public String lunchRegistrationSubmit(@RequestParam("lunchRegisteredId")int lunchRegisteredId
										,@RequestParam("rawString")String rawString,
										@RequestParam("avoidMeatList")String avoidMeatList,
										@RequestParam("registerFor")String registerFor,
										Authentication authentication) {
		String staffId = authentication.getName();
		
		current_next_option = registerFor;
		
		//System.out.println("Register for : "+registerFor);
		
		//------------ update Lunch Registration -----------
		
		lunchRegisterService.saveLunchRegistration(lunchRegisteredId,registerFor, rawString, staffId);
		
		

		// ------------ Update AvoidMeatList ------------
		AvoidMeat avoidMeat = new AvoidMeat();
		avoidMeat.setStaff_id(staffId);
		avoidMeat.setDescription(avoidMeatList);

		avoidMeatService.updateAvoidMeatData(avoidMeat);
		
		//============ Show success noti dialog ===============
		
		updateMessage_class = "notiMessageBox success open";
		noti_message = "You have successfully registered.";


		return "redirect:/DAT/lunch_registration";
	}
	
	@PostMapping("/lunch_registration_change")
	public String current_next_CalendarChange(@RequestParam("current_next_option")String c_n_option,
											Model model) {
		
		
		if(c_n_option.equals("current")) {
			current_next_option = "next";
		}else {
			current_next_option = "current";
		}
		
		//System.out.println(current_next_option);
		
		return "redirect:/DAT/lunch_registration";
	}

}
