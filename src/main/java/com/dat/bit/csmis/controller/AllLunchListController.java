package com.dat.bit.csmis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.entity.All_Have_Lunch;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.AllLunchListService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class AllLunchListController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private AllLunchListService allLunchListService;
	
	private String option = "all",tempStartDate = "",tempEndDate="";
	
	
	@GetMapping("/lunch_back_to_lunch_menu")
	public String BacktoLunchMainPage() {
		option = "all";
		tempStartDate = "";
		tempEndDate="";
		
		return "redirect:/DAT/lunch";
	}
	
	@GetMapping("/lunch_list_all")
	public String AllLunchList(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return AllLunchListPage(staffId, model);
	}


	@GetMapping("/lunch_list_all_all")
	public String ListTodayAll(@RequestParam("startDate")String startDate,
								@RequestParam("endDate")String endDate) {
		option = "all";
		tempStartDate = startDate;
		tempEndDate = endDate;
		
		System.out.println(startDate + " : "+ endDate);
		
		return "redirect:/DAT/lunch_list_all";
	}
	
	
	@GetMapping("/lunch_list_all_total_eat")
	public String ListTodayTotalEat(@RequestParam("startDate")String startDate,
									@RequestParam("endDate")String endDate) {
		option = "total_eat";
		tempStartDate = startDate;
		tempEndDate = endDate;
		
		System.out.println(startDate + " : "+ endDate);
		
		return "redirect:/DAT/lunch_list_all";
	}
	
	@GetMapping("/lunch_list_all_total_not_eat")
	public String ListTodayTotalNotEat(@RequestParam("startDate")String startDate,
										@RequestParam("endDate")String endDate) {
		option = "total_not_eat";
		tempStartDate = startDate;
		tempEndDate = endDate;
		
		System.out.println(startDate + " : "+ endDate);
		
		return "redirect:/DAT/lunch_list_all";
	}
	
	
	@GetMapping("/lunch_list_all_total_eat_noreg")
	public String ListTodayTotalEatNotRegister(@RequestParam("startDate")String startDate,
												@RequestParam("endDate")String endDate) {
		option = "total_eat_noreg";
		tempStartDate = startDate;
		tempEndDate = endDate;
		
		System.out.println(startDate + " : "+ endDate);
		
		return "redirect:/DAT/lunch_list_all";
	}
	
	@PostMapping("/lunch_list_all_date-confirm")
	public String LunchListAllDateConfirm(@RequestParam("startDate")String startDate,
											@RequestParam("endDate")String endDate) {
		
		tempStartDate = startDate;
		tempEndDate = endDate;
		
		return "redirect:/DAT/lunch_list_all";
	}
	
	@GetMapping("/lunch_list_all_search")
	public String LunchListAllSearch(@RequestParam("searchValue")String searchValue,
									@RequestParam("filterBy")String filterBy,
									@RequestParam("startDate")String startDate,
									@RequestParam("endDate")String endDate,
									Authentication authentication,Model model) {
		
		String staffId = authentication.getName();
		
		tempStartDate = startDate;
		tempEndDate = endDate;
		
		return AllLunchListPageSearch(staffId, searchValue, filterBy, model);
	}
	
	//=================================== Other Functions ==================================
	
	public String AllLunchListPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		int totalEat = 0;
		int totalNotEat = 0;
		int totalEatNotReg = 0;
		int totalCost = 0;
		int companyCost = 0;
		int staffCost = 0;
		
		List<All_Have_Lunch> all_Have_Lunchs = allLunchListService.getAllLunchTransactionListByOptionAndDateRange(option, tempStartDate, tempEndDate);
		for(All_Have_Lunch lunch : all_Have_Lunchs) {
			companyCost += lunch.getCompany_cost();
			staffCost += lunch.getStaff_cost();
			if(lunch.getRegister()==1 && lunch.getStatus()==1) {
				totalEat++;
			}else if(lunch.getRegister()==1 && lunch.getStatus()==0) {
				totalNotEat++;
			}else {
				totalEatNotReg++;
			}
		}
		
		totalCost = (companyCost+staffCost);
		
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("startDate",tempStartDate);
		model.addAttribute("endDate",tempEndDate);
		model.addAttribute("transactions",all_Have_Lunchs);
		model.addAttribute("totalEat",totalEat);
		model.addAttribute("totalNotEat",totalNotEat);
		model.addAttribute("totalEatNotReg",totalEatNotReg);
		model.addAttribute("totalCost",totalCost);
		model.addAttribute("option", option);
		model.addAttribute("searchValue","");
		model.addAttribute("filterBy","staffId");
		
		return "/Lunch/allLunchList";
	}
	public String AllLunchListPageSearch(String staffId,String searchValue,String filterBy,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		int totalEat = 0;
		int totalNotEat = 0;
		int totalEatNotReg = 0;
		int totalCost = 0;
		int companyCost = 0;
		int staffCost = 0;
		
		List<All_Have_Lunch> all_Have_Lunchs = allLunchListService.searchAllLunchTransactionListWithDateRange(option, searchValue, filterBy, tempStartDate, tempEndDate);
		for(All_Have_Lunch lunch : all_Have_Lunchs) {
			companyCost += lunch.getCompany_cost();
			staffCost += lunch.getStaff_cost();
			if(lunch.getRegister()==1 && lunch.getStatus()==1) {
				totalEat++;
			}else if(lunch.getRegister()==1 && lunch.getStatus()==0) {
				totalNotEat++;
			}else {
				totalEatNotReg++;
			}
		}
		
		totalCost = (companyCost+staffCost);
		
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("startDate",tempStartDate);
		model.addAttribute("endDate",tempEndDate);
		model.addAttribute("transactions",all_Have_Lunchs);
		model.addAttribute("totalEat",totalEat);
		model.addAttribute("totalNotEat",totalNotEat);
		model.addAttribute("totalEatNotReg",totalEatNotReg);
		model.addAttribute("totalCost",totalCost);
		model.addAttribute("option", option);
		model.addAttribute("searchValue",searchValue);
		model.addAttribute("filterBy",filterBy);
		
		return "/Lunch/allLunchList";
	}
}
