package com.dat.bit.csmis.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dat.bit.csmis.config.DoorlogExceIImport;
import com.dat.bit.csmis.entity.All_Have_Lunch;
import com.dat.bit.csmis.entity.DoorLog_CSV;
import com.dat.bit.csmis.entity.DoorLog_EXCEL;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.AllLunchListService;
import com.dat.bit.csmis.service.StaffService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/DAT")
public class LunchTodayController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private AllLunchListService allLunchListService;
	
	private String option = "all",noti_message="",updateMessage_class="notiMessageBox";
	
	
	@GetMapping("/lunch_list_today")
	public String LunchToday(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return LunchTodayPage(staffId, model);
	}
	
	@PostMapping("/upload-doorLog-csv")
	public String uploadDoorLogCSV(@RequestParam("file") MultipartFile file,Authentication authentication, Model model) {
//		String staffId = authentication.getName();
//		Staff staffData = staffService.getStaffByStaff_Id(staffId);
//		String loginUsername = staffData.getName();
		
		//System.out.println(file);
		
		if(file.getContentType().equals("text/csv")) {//CSV File
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

				// create csv bean reader
				CsvToBean<DoorLog_CSV> csvToBean = new CsvToBeanBuilder<DoorLog_CSV>(reader).withType(DoorLog_CSV.class)
													.withIgnoreLeadingWhiteSpace(true).build();

				// convert `CsvToBean` object to list of staffs
				List<DoorLog_CSV> doorLogs = csvToBean.parse();

				allLunchListService.saveTodayDoorTransactionsToDB(doorLogs);
				
				//---- show success noti dialog ---
		        updateMessage_class = "notiMessageBox success open";
				noti_message = "Successfully imported data.";

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error csv import from lunch today controller =>" + e.getMessage());
			}
		}else {//Excel File
			try {
				InputStream inputStream=file.getInputStream();
		        inputStream.available();
		        List<DoorLog_EXCEL> doorLogs = DoorlogExceIImport.readDoorlogExcel(inputStream);

		        allLunchListService.saveTodayDoorTransactionsToDBByExcelFile(doorLogs);
		        
		        //---- show success noti dialog ---
		        updateMessage_class = "notiMessageBox success open";
				noti_message = "Successfully imported data.";
				
				
			} catch (Exception e) {
				System.out.println("Error excel import from lunch today controller =>" + e.getMessage());
			}
			
		}
		
		return "redirect:/DAT/lunch_list_today";
	}
	
	
	
	@GetMapping("/lunch_list_today_all")
	public String ListTodayAll() {
		option = "all";
		
		return "redirect:/DAT/lunch_list_today";
	}
	
	
	@GetMapping("/lunch_list_today_total_eat")
	public String ListTodayTotalEat() {
		option = "total_eat";
		
		return "redirect:/DAT/lunch_list_today";
	}
	
	@GetMapping("/lunch_list_today_total_not_eat")
	public String ListTodayTotalNotEat() {
		option = "total_not_eat";
		
		return "redirect:/DAT/lunch_list_today";
	}
	
	
	@GetMapping("/lunch_list_today_total_eat_noreg")
	public String ListTodayTotalEatNotRegister() {
		option = "total_eat_noreg";
		
		return "redirect:/DAT/lunch_list_today";
	}
	
	@GetMapping("/lunch_list_today_search")
	public String LunchListTodaySearch(@RequestParam("searchValue")String searchValue,
									@RequestParam("filterBy")String filterBy,
									Authentication authentication,Model model) {
		
		String staffId = authentication.getName();
		
		
		return LunchTodayPageSearch(staffId, searchValue, filterBy, model);
	}
	
	
	
	//========================= Other Functions ===========================================
	

	public String LunchTodayPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		int totalEat = 0;
		int totalNotEat = 0;
		int totalEatNotReg = 0;
		int totalCost = 0;
		int companyCost = 0;
		int staffCost = 0;
		
		List<All_Have_Lunch> all_Have_Lunchs = allLunchListService.getTodayAllLunchTransactionListByOption(option);
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
		model.addAttribute("transactions",all_Have_Lunchs);
		model.addAttribute("totalEat",totalEat);
		model.addAttribute("totalNotEat",totalNotEat);
		model.addAttribute("totalEatNotReg",totalEatNotReg);
		model.addAttribute("totalCost",totalCost);
		model.addAttribute("option", option);
		model.addAttribute("searchValue","");
		model.addAttribute("filterBy","staffId");
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updateMessage_class = "notiMessageBox";
		
		return "Lunch/lunchList_Today";
	}
	
	public String LunchTodayPageSearch(String staffId,String searchValue,String filterBy,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		int totalEat = 0;
		int totalNotEat = 0;
		int totalEatNotReg = 0;
		int totalCost = 0;
		int companyCost = 0;
		int staffCost = 0;
	
		
		List<All_Have_Lunch> all_Have_Lunchs = allLunchListService.searchTodayAllLunchTransactionList(option, searchValue, filterBy);
		
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
		model.addAttribute("transactions",all_Have_Lunchs);
		model.addAttribute("totalEat",totalEat);
		model.addAttribute("totalNotEat",totalNotEat);
		model.addAttribute("totalEatNotReg",totalEatNotReg);
		model.addAttribute("totalCost",totalCost);
		model.addAttribute("option", option);
		model.addAttribute("searchValue",searchValue);
		model.addAttribute("filterBy",filterBy);
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updateMessage_class = "notiMessageBox";
		
		return "Lunch/lunchList_Today";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
