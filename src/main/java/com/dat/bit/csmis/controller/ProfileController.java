package com.dat.bit.csmis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.dto.Staff_DTO;
import com.dat.bit.csmis.entity.AvoidMeat;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.entity.StaffDetail;
import com.dat.bit.csmis.service.AvoidMeatListService;
import com.dat.bit.csmis.service.AvoidMeatService;
import com.dat.bit.csmis.service.StaffDetailService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class ProfileController {
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private StaffDetailService staffDetailService;
	
	@Autowired
	private AvoidMeatService avoidMeatService;
	
	@Autowired
	private AvoidMeatListService avoidMeatListService;
	
	private String noti_message="",updateMessage_class="notiMessageBox";
	
	public String profilePage(String staffId,String wrongPass_Message,String pop_className,
							String old_password,String cover_className,Model model) {
		
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		StaffDetail staffDetail = staffDetailService.findByStaffId(staffId);
		
		List<String> avoidMeatNameList = avoidMeatListService.getAllAvoidMeatName();
		
		
		AvoidMeat avoidMeat = avoidMeatService.getAvoidMeatDataByStaffId(staffId);
		
	//-------- Create Staff_DTO object that contains staff and staff_detail table data of current login user ----
		Staff_DTO staff_DTO = new Staff_DTO();
		staff_DTO.setStaffId(staffId);
		staff_DTO.setName(loginUsername);
		staff_DTO.setEmail(staffDetail.getEmail());
		staff_DTO.setEmail_noti(staffDetail.getEmail_noti());
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("staff",staff_DTO);
		model.addAttribute("avoidMeatNameList",avoidMeatNameList);
		model.addAttribute("avoidMeatList",avoidMeat.getDescription());
		model.addAttribute("popup_className",pop_className);
		model.addAttribute("old_password",old_password);
		model.addAttribute("worngPass_Message",wrongPass_Message);
		model.addAttribute("cover_className",cover_className);
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updateMessage_class = "notiMessageBox";
		
		return "profile/profile";
	}

	
	@GetMapping("/profile")
	public String profile(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return profilePage(staffId,"","info_popup_container","","cover",model);
	}
	
	@PostMapping("/profile_save")
	public String profileSave(@ModelAttribute("staff")Staff_DTO staff_DTO,
							@RequestParam("avoidMeatList")String avoidMeatList,
							Authentication authentication, Model model) {
		
		String staffId = authentication.getName();
		
		
	//============= Update Staff Detail =============
		StaffDetail staffDetail = staffDetailService.findByStaffId(staffId);
		staffDetail.setEmail_noti(staff_DTO.getEmail_noti());
		staffDetail.setEmail(staff_DTO.getEmail());
		
		staffDetailService.updateStaffDetail(staffDetail);
	//------------  Updae AvoidMeatList  ------------
		
		
		AvoidMeat avoidMeat = new AvoidMeat();
		avoidMeat.setStaff_id(staffId);
		avoidMeat.setDescription(avoidMeatList.equals("") ? null : avoidMeatList);
		
		avoidMeatService.updateAvoidMeatData(avoidMeat);
	//====================================================================	
		//============ Show success noti dialog ===============
		
		updateMessage_class = "notiMessageBox success open";
		noti_message = "Your profile is successfully updated.";
		
		return "redirect:/DAT/profile";
	}
	
	@PostMapping("/profile_confirm_old_password")
	public String confirm_old_password(@RequestParam("old_password")String old_password,
										Authentication authentication,Model model) {
		
		String staffId = authentication.getName();
		String wrongPasswordMessage = "";
		String popup_className = "info_popup_container open";
		String cover_className = "cover";
		
		//---- Create Staff object to get staff password ----------
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(passwordEncoder.matches(old_password, staffData.getPassword())) {//<= if match
			cover_className = "cover close";
		}else {//<= if doesn't match
			wrongPasswordMessage = "Wrong Password!Try Again!";
		}
		//-----------------------------------------------------------
		
		return profilePage(staffId,wrongPasswordMessage, popup_className,old_password,cover_className,model);
	}
	
	@PostMapping("/profile_change_new_password")
	public String change_new_password(@RequestParam("new_password")String new_password,Authentication authentication,Model model) {
		
		String staffId = authentication.getName();
		
		staffService.updateStaffPassword(staffId, new_password);
		
		//============ Show success noti dialog ===============
		
		updateMessage_class = "notiMessageBox success open";
		noti_message = "Your password is successfully updated.";

		return "redirect:/DAT/profile";
	}
}
