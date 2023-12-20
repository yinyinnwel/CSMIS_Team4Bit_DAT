package com.dat.bit.csmis.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.entity.Department;
import com.dat.bit.csmis.entity.Restaurant;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.mail.service.MailService;
import com.dat.bit.csmis.pdf.service.PdfService;
import com.dat.bit.csmis.service.DashboardService;
import com.dat.bit.csmis.service.RestaurantCommentService;
import com.dat.bit.csmis.service.RestaurantService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class DashboardController {
	
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	@Autowired
	private PdfService pdfService;
	@Autowired
	private MailService mailService;
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private RestaurantCommentService restaurantCommentService;
	@Autowired
	private DashboardService dashboardService;
	
	public String dashboardPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		//===== Get total of today lunch registered staff ==================
		
		int todayLunchRegisteredstaffs = dashboardService.getTodayLunchRegisteredStaffList().size();
		
		int totalNumberof_Staff = staffService.getTotalNumber_of_Staffs();
		//==== ================================== ==============================
		long totalDepartment = dashboardService.getTotalDepartmentOfCompany();
		long totalTeam = dashboardService.getTotalTeamOfCompany();
		long totalDivision = dashboardService.getTotalDivisionOfCompany();

		//============= Get quantity of male and female of each department ==============
		List<Department> departments = dashboardService.getDepartmentDetailForGraph();	
		
		//---- Get Current Active Restaurant ---
		Restaurant activeRestaurant = restaurantService.getActiveRestaurant();
		
		try {
			String encodedPdf = null;
			String encodedPdf1 = null;
			if ("local".equals(activeProfile)) {
			    // Running in local development environment
				encodedPdf = pdfService.getPdfAsByteString_fromLocalDevelopment("thisweek.pdf");
				
				encodedPdf1 = pdfService.getPdfAsByteString_fromLocalDevelopment("nextweek.pdf");
			} else {
			    // Running in a Tomcat server development environment
				encodedPdf = pdfService.getPdfAsByteString_fromTomcatServer("thisweek.pdf");
				
				encodedPdf1 = pdfService.getPdfAsByteString_fromTomcatServer("nextweek.pdf");
			}

			model.addAttribute("thispdf", encodedPdf);
			model.addAttribute("nextpdf", encodedPdf1);
			
		}catch(Exception e) {
			System.out.println("Error from dashboardPage > get Lunch Menu : "+e.getMessage());
		}
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("total_staff",totalNumberof_Staff);
		model.addAttribute("todayLunchRegisteredStaffs",todayLunchRegisteredstaffs);
		model.addAttribute("activeRestaurant",activeRestaurant);
		
		model.addAttribute("departments",departments);
		
		model.addAttribute("totalDepartment",totalDepartment);
		model.addAttribute("totalTeam",totalTeam);
		model.addAttribute("totalDivision",totalDivision);
		
		return "dashboard/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String Dashboard(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return dashboardPage(staffId,model);
	}
	
	@PostMapping("/dashboard_restaurant_comment")
	public String dashboardRestauratAddComment(@RequestParam("comment_description")String comment_description,
											Authentication authentication, Model model) {
		
		String staffId = authentication.getName();
		
		//---- Get Current Active Restaurant ---
		Restaurant activeRestaurant = restaurantService.getActiveRestaurant();
		
		restaurantCommentService.saveComment_ByRestaurantId(activeRestaurant.getId(), staffId, comment_description);
		
		return "redirect:/DAT/dashboard";
	}
	
	@PostMapping("/dashboard_mail_send")
	public String mailSend(@RequestParam("mail_subject")String mail_subject,
							@RequestParam("mail_description")String mail_description,
							Authentication authentication) {
		
		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		List<String> recipientEmailList = dashboardService.getEmailsFromStaffsWhoNotiOn();
		
		//============ Send Mail ==========================================
		
		try {
			mailService.sendAnnoucementMailToNotiOnStaffs(recipientEmailList, mail_subject, mail_description, recipientEmailList.size(),loginUsername);
		} catch (MessagingException e) {
			System.out.println("Error from Mail sending to staff who on noti from DashboardController.java!");
			e.printStackTrace();
		}
		
		
		return "redirect:/DAT/dashboard";
	}
	

}
