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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dat.bit.csmis.config.StaffExcelImport;
import com.dat.bit.csmis.dto.Staff_DTO;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.entity.Staff_CSV;
import com.dat.bit.csmis.entity.Staff_ECXCEL;
import com.dat.bit.csmis.service.StaffDetailService;
import com.dat.bit.csmis.service.StaffService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/DAT")
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private StaffDetailService detailService;
	
	private String tempSearchValue="", tempFilterBy="", tempFilterStatus="";
	private int tempPageNumber = 1;
	
	
	public void getLoginStaffName(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		model.addAttribute("loginUserName",loginUsername);
	}
	
	public String staffListPage(String staffId,Model model,int pageNumber) {
		
		
		List<Staff_DTO> staff_DTO_list = staffService.findAll(pageNumber);
		
		double staffTotal = (double)staffService.getTotalNumber_of_Staffs();
		int totalPage = (int) Math.ceil((staffTotal / 50));
		
		//========== Get all Department Name for search ===============
		List<String> departments = detailService.getAllDepartmentName();
		//========== Get all Team Name for search ===============
		List<String> teams = detailService.getAllTeamName();

		//Add model attribute,Don't forget
		model.addAttribute("staffs" , staff_DTO_list);
		model.addAttribute("status", true);//for upload csv.file error checking(true = no error)
		model.addAttribute("pageNumber", pageNumber);//current PageNumber
		model.addAttribute("maxPage",totalPage);
		model.addAttribute("found", (int)staffTotal);
		model.addAttribute("staff_DTO",new Staff_DTO());
		model.addAttribute("departments",departments);
		model.addAttribute("teams",teams);
		getLoginStaffName(staffId, model);
		
		return "/staffList/staff";
	}
	
	public String staffListPageBySearch(String staffId,String searchValue,String filterBy, String filterStatus,int pageNumber,Model model) {
		int totalPage = 1;
		double staffTotal = 0;

		List<Staff_DTO> staff_DTO_list = staffService.findBy(searchValue, filterBy, filterStatus, pageNumber);
		
		
		if(staff_DTO_list.size() > 0) {
			staffTotal = (double)staff_DTO_list.get(0).getTotal();
			totalPage = (int) Math.ceil((staffTotal / 50));
		}else {
			pageNumber = 1;
		}
		
		//========== Get all Department Name for search ===============
		List<String> departments = detailService.getAllDepartmentName();
		//========== Get all Team Name for search ===============
		List<String> teams = detailService.getAllTeamName();
		
		model.addAttribute("staffs" , staff_DTO_list);
		model.addAttribute("status", true);
	//***** set last search filter by and search value to staff.html after action********
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("selectedFilterBy",filterBy);
		model.addAttribute("selectedStatus",filterStatus);
		model.addAttribute("pageNumber", pageNumber);//set page number
		model.addAttribute("maxPage",totalPage);
		model.addAttribute("found", (int)staffTotal);
		model.addAttribute("staff_DTO",new Staff_DTO());
		model.addAttribute("departments",departments);
		model.addAttribute("teams",teams);
		getLoginStaffName(staffId, model);
		
		return "/staffList/staff_search";
	}


	@GetMapping("/staff_list")
	public String staffList(Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		model.addAttribute("updateMessage_class","notiMessageBox");
		return staffListPage(staffId,model,1);
	}
	
	@GetMapping("/staff_refresh")
	public String refreshStaffTable(Model model) {
		
		return "redirect:/DAT/staff_list";
	}
	
	@PostMapping("/upload-staff-csv")
	public String uploadStaff_CSVFile(@RequestParam("file") MultipartFile file,Authentication authentication, Model model) {
//		System.out.println(file.getContentType());
		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
				// validate file
				if (file.isEmpty()) {
					model.addAttribute("message", "Please select a CSV file to upload.");
					model.addAttribute("status", false);
					model.addAttribute("pageNumber", 1);//current PageNumber
					model.addAttribute("maxPage",1);
					model.addAttribute("found", 0);
				} else {

					if(file.getContentType().equals("text/csv")) {
						// parse CSV file to create a list of `Staff` objects
						try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

							// create csv bean reader
							CsvToBean<Staff_CSV> csvToBean = new CsvToBeanBuilder<Staff_CSV>(reader).withType(Staff_CSV.class)
															.withIgnoreLeadingWhiteSpace(true).build();

							// convert `CsvToBean` object to list of staffs
							List<Staff_CSV> staffs = csvToBean.parse();
							

							List<String> staffIdList = staffService.getAllStaffId();
							// TODO: save staffs into DB?
							try {
								for (Staff_CSV staff_CSV : staffs) {
							// -----check difference between staff id from import file and staff id from Database
									if (staffIdList.contains(staff_CSV.getStaffId())) {
										staffIdList.remove(staff_CSV.getStaffId());
									}

									// Save staff data from CSV file to DB
									staff_CSV.setCreatedBy("(" + staffId + ")" + loginUsername);
									staffService.saveStaff(staff_CSV);
								}
								
							//----- if some staff id are remained inside List<String> staffIdList,they are resigned
					        	if(!staffIdList.isEmpty()) {
					        		for(String staff_Id : staffIdList) {
					        			if(!staff_Id.equals("00-00001")) {//no need to update default admin
					        				staffService.updateResignedStaff(staff_Id);
					        			}
					        		}
					        	}
							} catch (Exception e) {
								System.out.println("Error while saving staff form excel =>"+e.getMessage().toString());
							}
							

						} catch (Exception ex) {
							model.addAttribute("message", "An error occurred while processing the CSV file.");
							model.addAttribute("status", false);//for upload csv.file error checking(false = has error)
							model.addAttribute("pageNumber", 1);//current PageNumber
							model.addAttribute("maxPage",1);
							model.addAttribute("found", 0);
						}
					}else {//This is EXCEL file import
						
						try {
							InputStream inputStream=file.getInputStream();
					        inputStream.available();
					        List<Staff_ECXCEL> staffs = StaffExcelImport.readStaffExcel(inputStream);
					        
					        List<String> staffIdList = staffService.getAllStaffId();
					     // TODO: save staffs into DB?
					        try {
					        	for(Staff_ECXCEL staff_ECXCEL : staffs) {
					        //-----check difference between staff id from import file and staff id from Database
					        		if(staffIdList.contains(staff_ECXCEL.getStaffId())) {
					        			staffIdList.remove(staff_ECXCEL.getStaffId());
					        		}
					        		
						        	staff_ECXCEL.setCreatedBy("("+staffId+")"+loginUsername);
						        	staffService.saveStaffWithExcelFile(staff_ECXCEL);
						        }
					        	
					        //----- if some staff id are remained inside List<String> staffIdList,they are resigned
					        	if(!staffIdList.isEmpty()) {
					        		for(String staff_Id : staffIdList) {
					        			if(!staff_Id.equals("00-00001")) {//no need to update default admin
					        				staffService.updateResignedStaff(staff_Id);
					        			}
					        			
					        		}
					        	}
					        	
							} catch (Exception e) {
								System.out.println("Error while saving staff form excel =>"+e.getMessage().toString());
							}
					        
						} catch (Exception e) {
							model.addAttribute("message", "An error occurred while processing the CSV file.");
							model.addAttribute("status", false);//for upload csv.file error checking(false = has error)
							model.addAttribute("pageNumber", 1);//current PageNumber
							model.addAttribute("maxPage",1);
							model.addAttribute("found", 0);
						}
						
					}
					
					List<Staff_DTO> staff_DTO_list = staffService.findAll(1);
					
					double staffTotal = (double)staffService.getTotalNumber_of_Staffs();
					int totalPage = (int) Math.ceil((staffTotal / 50));
					
					//========== Get all Department Name for search ===============
					List<String> departments = detailService.getAllDepartmentName();
					//========== Get all Team Name for search ===============
					List<String> teams = detailService.getAllTeamName();
					
					model.addAttribute("departments",departments);
					model.addAttribute("teams",teams);
					model.addAttribute("staffs" , staff_DTO_list);
					model.addAttribute("status", true);//for upload csv.file error checking(true = no error)
					model.addAttribute("pageNumber", 1);//current PageNumber
					model.addAttribute("maxPage",totalPage);
					model.addAttribute("found", (int)staffTotal);
					model.addAttribute("staff_DTO",new Staff_DTO());
				}
				
		getLoginStaffName(staffId, model);
		model.addAttribute("updateMessage_class","notiMessageBox success open");
		model.addAttribute("updateMessage","All Staff data are successfully imported.");
		
		
		return "/staffList/staff";
	}
	
	
	@GetMapping("/staff_search")
	public String searchStaffs(@RequestParam("searchValue") String searchValue,
								@RequestParam("filterStatus") String filterStatus,
								@RequestParam("filterBy") String filterBy,
								@RequestParam("pageNumber") int pageNumber,
								Authentication authentication,
								Model model) {
		
		String staffId = authentication.getName();
		
		pageNumber = 1;//reset page number to start
		
		tempSearchValue = searchValue;
		tempFilterStatus = filterStatus;
		tempFilterBy = filterBy;
		tempPageNumber = pageNumber;
		
		model.addAttribute("updateMessage_class","notiMessageBox");

		return staffListPageBySearch(staffId,searchValue,filterBy,filterStatus,pageNumber,model);
	}
	
	@GetMapping("/pagination")
	public String staffPagination(@RequestParam("pageNumber") int pageNumber,Authentication authentication,Model model) {
		String staffId = authentication.getName();
		tempPageNumber = pageNumber;
		
		model.addAttribute("updateMessage_class","notiMessageBox");
		return staffListPage(staffId,model,pageNumber);
	}
	
	@GetMapping("/pagination_search")
	public String staffPagination_Search(@RequestParam("pageNumber") int pageNumber,Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		tempPageNumber = pageNumber;
		
		model.addAttribute("updateMessage_class","notiMessageBox");
		
		return staffListPageBySearch(staffId,tempSearchValue,tempFilterBy,tempFilterStatus,pageNumber,model);
	}
	
	@PostMapping("/staff_edit")
	public String edit_Staff(@ModelAttribute("staff_DTO")Staff_DTO staff_DTO,Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		staff_DTO.setUpdatedBy("("+staffId+")"+loginUsername);
		
		String update_message = staffService.updateStaff(staff_DTO);
		
//		if(update_message.equals("The specifid number of admins has been reached!")) {
//			model.addAttribute("updateMessage_class","notiMessageBox warning open");
//		}else {
//			model.addAttribute("updateMessage_class","notiMessageBox success open");
//		}
		
		model.addAttribute("updateMessage_class","notiMessageBox success open");
		
		model.addAttribute("updateMessage",update_message);
		
		
		return staffListPage(staffId,model,tempPageNumber);
	}
	
	@PostMapping("/staff_search_edit")
	public String edit_Staff_Search(@ModelAttribute("staff_DTO")Staff_DTO staff_DTO,Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		staff_DTO.setUpdatedBy("("+staffId+")"+loginUsername);
		
		String update_message = staffService.updateStaff(staff_DTO);
		
//		if(update_message.equals("The specifid number of admins has been reached!")) {
//			model.addAttribute("updateMessage_class","notiMessageBox warning open");
//		}else {
//			model.addAttribute("updateMessage_class","notiMessageBox success open");
//		}
		
		model.addAttribute("updateMessage_class","notiMessageBox success open");
		model.addAttribute("updateMessage",update_message);
		
		return staffListPageBySearch(staffId,tempSearchValue,tempFilterBy,tempFilterStatus,tempPageNumber,model);
	}

}
