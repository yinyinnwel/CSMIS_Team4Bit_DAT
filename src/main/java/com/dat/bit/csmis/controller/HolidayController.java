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

import com.dat.bit.csmis.config.HolidayExcelImport;
import com.dat.bit.csmis.entity.Holiday;
import com.dat.bit.csmis.entity.Holiday_CSV;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.HolidayService;
import com.dat.bit.csmis.service.StaffService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/DAT")
public class HolidayController {
	
	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	public HolidayController(HolidayService holidayService) {
		super();
		this.holidayService = holidayService;
	}
	
	private String noti_message="",updateMessage_class="notiMessageBox";
	
	public void addHoliday_To_DB(List<Holiday_CSV> days) {
		try {
			holidayService.addAllHolidayDataToDB(days);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@GetMapping("/holiday_upload")
	public String uploadCSVForm(Authentication authentication,Model model) {
		
		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		List<Holiday> holidays=holidayService.getAllHolidayDataFromDB();


		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("days",holidays);
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updateMessage_class = "notiMessageBox";
		
		
		return "/holiday/Holiday";
	}
	
	@PostMapping("/holiday_refresh")
	public String refreshStaffTable(Model model) {
		
		return "redirect:/DAT/holiday_upload";
	}
	
	@PostMapping("/holiday_upload-csv-file")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file,Model model) {
		
		//System.out.println(file.getContentType());
		//text/csv
		
		if(file.isEmpty()) {
			model.addAttribute("message","Please select CSV file to upload");
			model.addAttribute("status",false);
			
		}else {
			if(file.getContentType().equals("text/csv")) {//CSV file
				try(Reader reader=new BufferedReader(new InputStreamReader(file.getInputStream()))) {
					
					CsvToBean<Holiday_CSV> csvToBean=new CsvToBeanBuilder<Holiday_CSV>(reader).withType(Holiday_CSV.class)
							                          .withIgnoreLeadingWhiteSpace(true).build();
					List<Holiday_CSV> days=csvToBean.parse();
					
					holidayService.deleteAll();
					addHoliday_To_DB(days);
					
					//============ Show success noti dialog ===============
					
					updateMessage_class = "notiMessageBox success open";
					noti_message = "Holiday data are successfully imported.";
					
				} catch (Exception ex) {
					model.addAttribute("message","An error occur");
					model.addAttribute("status",false);
				}
			}else {//Excel File
				
		        try {
		        	InputStream inputStream=file.getInputStream();
			        inputStream.available();
					List<Holiday> holidays=HolidayExcelImport.readHoliday(inputStream);
					
					holidayService.deleteAll();
					holidayService.addAllHolidayDatatoDBWithExcelFile(holidays);
					
					//============ Show success noti dialog ===============
					
					updateMessage_class = "notiMessageBox success open";
					noti_message = "Holiday data are successfully imported.";
				} catch (Exception e) {
					
					System.out.println("Here holiday Excel File import => "+e.getMessage());
					model.addAttribute("message","An error occur");
					model.addAttribute("status",false);
				}

		        
			}
			
		}
		return "redirect:/DAT/holiday_upload";
	}

}
