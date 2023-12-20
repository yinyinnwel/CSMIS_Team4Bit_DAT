package com.dat.bit.csmis.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.mail.service.MailService;
import com.dat.bit.csmis.otp.service.OTPGenerator;
import com.dat.bit.csmis.service.StaffDetailService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class ForgotPasswordController {
	
	@Autowired
	private OTPGenerator otpGenerator;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private StaffDetailService staffDetailService;
	
	private String tempOTP = "",tempEmail = "";
	
	@GetMapping("/forgot_password")
	public String forgotPasswordPage(Model model){
		
		model.addAttribute("invalidStaffId","");
		model.addAttribute("otp","There is  no otp!What are u looking for?");
		model.addAttribute("inputEmail","");
		model.addAttribute("countShow","no");
        
		return "forgotPassword/forgotPassword";
	}
	
	@GetMapping("/send_otp")
	public String sendOTP(@RequestParam("email")String email,Model model) throws NoSuchAlgorithmException, InvalidKeyException, MessagingException {
		//----- Generate OTP -----------
		
		String staffId = staffDetailService.checkOTPEmailIsContianedOrNot(email);
		
		if(!staffId.equals("")) {//If email exists
			String otp = otpGenerator.generateOTP();
	        tempOTP = otp;
	        System.out.println("OTP: " + otp);
			
	        //------- Send OTP Email --------------
	        System.out.println(email);
	        String recipientEmail = email;
	        tempEmail = email;
	        String subject = "OTP";
	        
	        try {
	        	mailService.sendOTPEmail(recipientEmail, subject, otp);
			} catch (Exception e) {
				System.out.println("This is OTP code send error!");
			}
	        
	        //--------------------------------------
	        
	        model.addAttribute("invalidStaffId","");
	        model.addAttribute("otp",Integer.parseInt(otp)+146905);//<=add + 146905 to otp(org),If u want to get otp(org) - 146905
	        model.addAttribute("inputEmail",email);
	        model.addAttribute("countShow","yes");
	        model.addAttribute("staffId",staffId);
	        model.addAttribute("invalidEmail","");
		}else {//if email doesn't exist
			model.addAttribute("invalidStaffId","");
	        model.addAttribute("otp",0);
	        model.addAttribute("inputEmail","");
	        model.addAttribute("countShow","no");
	        model.addAttribute("staffId",staffId);
	        model.addAttribute("invalidEmail","show");
		}
		
        
		
		return "forgotPassword/forgotPassword";
	}
	
	@PostMapping("/change_password")
	public String changePassword(@RequestParam("new_password")String new_password,
								@RequestParam("staffId")String staffId,Model model) {
		
		
		try {
			staffService.updateStaffPassword(staffId, new_password);
			
//			return "/login/login";
			return "redirect:/showMyLoginPage";
		} catch (Exception e) {
			//---If provided staffId is invalid or not exists
			model.addAttribute("invalidStaffId","Invalid Staff Id!");
			model.addAttribute("otp",Integer.parseInt(tempOTP)+146905);
	        model.addAttribute("inputEmail",tempEmail);
	        model.addAttribute("countShow","no");
	        
	        return "forgotPassword/forgotPassword";
		}
		
		
	}

}
