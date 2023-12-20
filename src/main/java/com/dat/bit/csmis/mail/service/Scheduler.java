package com.dat.bit.csmis.mail.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import com.dat.bit.csmis.dto.Registered_Staff_DTO;
import com.dat.bit.csmis.entity.StaffDetail;
import com.dat.bit.csmis.service.DashboardService;
import com.dat.bit.csmis.service.StaffDetailService;

@Component
public class Scheduler {
	
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private StaffDetailService detailService;


    @Scheduled(cron = "00 30 10 ? * MON-FRI") // every Monday to Friday at 10:30 AM
    public void notiTo_TodayLunchRegisteredStaffs() {

		List<String> recipientEmailList = new ArrayList<>();
		List<String> recipientName = new ArrayList<>();
    	
    	List<Registered_Staff_DTO> todayLunchRegisteredstaffs = dashboardService.getTodayLunchRegisteredStaffList();
    	for(Registered_Staff_DTO registered_Staff_DTO : todayLunchRegisteredstaffs) {
    		String staff_Id = registered_Staff_DTO.getStaffId();
    		
    		StaffDetail detail = detailService.findByStaffId(staff_Id);
    		if(detail.getEmail_noti() == 1) {
    			String email = detail.getEmail();
    			String name = registered_Staff_DTO.getName();
    			
    			//If email is valid----
    			if (isValidEmail(email)) {
    				recipientEmailList.add(email);
        			recipientName.add(name);
    			}
    			
    		}
    	}
    	
    	//======= Send Mail ====================
    	try {
			mailService.sendLunchRemindMailToNotiOnStaffs(recipientEmailList, recipientName, "Lunch Reminder", "Today is your lunch registered day,don't forget to eat!", recipientEmailList.size(), "Admin");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println("Error => From notiTo_TodayLunchRegisteredStaffs() from Schediler.java!");
			System.out.println(e.getMessage());
		}
    	
    	
    }
    
    @Scheduled(cron = "00 00 14 ? * FRI")
    public void changeLunchMenu() {
    	
    	try {
    		if ("local".equals(activeProfile)) {
    		    // Running in local development environment
    			checkTodayIsFri_hourIs2PM_changeLunchMenu_forLocalDevelopment();
    		} else {
    		    // Running in a Tomcat server development environment
    			checkTodayIsFri_hourIs2PM_changeLunchMenu_forTomcatServer();
    		}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error from changeLunchMenu() : "+e.getMessage());
		}
    	
    }
    
    
//============================  Other functions  ===================================================================
    
    public static boolean isValidEmail(String email) {
        // Regular expression pattern for email validation
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////This code is for tomcat Server ///////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
    
	public void checkTodayIsFri_hourIs2PM_changeLunchMenu_forTomcatServer() throws IOException, FileNotFoundException {

		String fileName = "thisweek.pdf";
		String fileName1 = "nextweek.pdf";

		// Get the webapps directory of the Tomcat server
		String tomcatDirectory = System.getProperty("catalina.home") + File.separator + "webapps";

		// Create a Path object for the PDF file
		Path pdfPath = Paths.get(tomcatDirectory, "pdfs", fileName);

		Path pdfPath1 = Paths.get(tomcatDirectory, "pdfs", fileName1);

		// Delete the PDF file
		if (Files.exists(pdfPath) && Files.exists(pdfPath1)) {
			// do something if the file exists
			Files.delete(pdfPath);
		}

		// Change the name of nextweek.padf to thisweek.pdf

		String oldFileName = "nextweek.pdf";
		String newFileName = "thisweek.pdf";

		// Get the webapps directory of the Tomcat server
		String tomcatDirectory1 = System.getProperty("catalina.home") + File.separator + "webapps";

		// Create a Path object for the old PDF file
		Path oldPdfPath = Paths.get(tomcatDirectory1, "pdfs", oldFileName);

		// Create a Path object for the new PDF file
		Path newPdfPath = Paths.get(tomcatDirectory1, "pdfs", newFileName);

		if (Files.exists(oldPdfPath)) {
			// Rename the PDF file
			Files.move(oldPdfPath, newPdfPath);
		}

	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////	
//////////////////////////  This code is for local storage develpment //////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void checkTodayIsFri_hourIs2PM_changeLunchMenu_forLocalDevelopment() throws IOException {

		String fileName = "thisweek.pdf";
		String staticFolderPath = "src/main/resources/pdfs/";
		File file = new File(staticFolderPath + fileName);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("File " + fileName + " deleted successfully.");
			} else {
				System.out.println("Failed to delete file " + fileName);
			}
		}

		// *******Change the name of nextweek.padf to thisweek.pdf
		String currentName = "nextweek.pdf";
		String newName = "thisweek.pdf";
		String staticFolderPath1 = "src/main/resources/pdfs/";
		File currentFile = new File(staticFolderPath1 + currentName);
		File newFile = new File(staticFolderPath1 + newName);
		if (currentFile.exists()) {
			if (currentFile.renameTo(newFile)) {
				System.out.println("File " + currentName + " renamed to " + newName + " successfully.");
			} else {
				System.out.println("Failed to rename file " + currentName + " to " + newName);
			}
		}
	}

}
