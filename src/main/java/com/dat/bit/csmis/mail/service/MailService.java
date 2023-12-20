package com.dat.bit.csmis.mail.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dat.bit.csmis.config.MailConfig;


@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private MailConfig mailConfig;

	
	public void sendOTPEmail(String to, String subject,String otp) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(to);
        helper.setSubject(subject);

        // Add HTML card to mail body
        String htmlCard = OTPHtmlCard(otp);
        
        helper.setText(htmlCard , true);

        javaMailSender = mailConfig.javaMailSender();
        javaMailSender.send(message);
    }
	
	
	
	public String htmlCard(String description) {
		 String htmlCard1 = "<div style=\"background-color: #333f50; border-radius: 10px; padding: 20px; color: white;\">"
					+ "<h2 style=\"margin-top: 0;color: white;\">HTML Card</h2>"
					+ "<p style=\"color: white;\">This is an example HTML card.</p>"
					+ "<ul><li><strong style=\"list-style: none; padding: 0; font-size: 16px; word-break: break-all;\">"
					+ description
					+ "</strong></li></ul>"
					+"</div>";
		 return htmlCard1;
	}
	
	public void sendAnnoucementMailToNotiOnStaffs(List<String> to, String subject, String description,int batch_Size,String sendfrom) throws MessagingException{
        int batchSize = batch_Size; // Set the batch size(Total of notification on staffs)
        
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < to.size(); i += batchSize) {
            batches.add(to.subList(i, Math.min(i + batchSize, to.size())));
        }
        for (List<String> batch : batches) {
            new Thread(() -> {
                try {
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setTo(batch.toArray(new String[0]));
                    helper.setSubject(subject);
               
                    String htmlCard = mailCardHTML("DAT members",description, sendfrom);
                    
                    helper.setText(htmlCard , true);
                    
                    
                    javaMailSender = mailConfig.javaMailSender();
                    javaMailSender.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
	
	public void sendLunchRemindMailToNotiOnStaffs(List<String> to,List<String> names, String subject, String description,int batch_Size,String sendfrom) throws MessagingException{
        int batchSize = batch_Size; // Set the batch size(Total of notification on staffs)
        
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < to.size(); i += batchSize) {
            batches.add(to.subList(i, Math.min(i + batchSize, to.size())));
        }
        for (List<String> batch : batches) {
        	
            new Thread(() -> {
                try {
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setTo(batch.toArray(new String[0]));
                    helper.setSubject(subject);
                    
                   
                    String htmlCard = mailCardHTML("",description, sendfrom);
                    
                    helper.setText(htmlCard , true);
                    
                    
                    javaMailSender = mailConfig.javaMailSender();
                    javaMailSender.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
	
	
	public String mailCardHTML(String dear,String description,String sendfrom) {
		String html = "<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "    <meta charset=\"UTF-8\">"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "    <title>Mail</title>"
				+ "    <style>"
				+ "        .ag-courses_item {"
				+ "            position: relative;"
				+ "            width: 40%;"
				+ "            min-width: 500px;"
				+ "            overflow: hidden;"
				+ "            border-radius: 20px;"
				+ "            font-family: 'Roboto', sans-serif;"
				+ "        }"
				+ "        .ag-courses-item_link {"
				+ "            display: block;"
				+ "            padding: 30px 20px;"
				+ "            text-decoration: none;"
				+ "            background-color: #333f50;"
				+ "            overflow: hidden;"
				+ "            position: relative;"
				+ "        }"
				+ "        .ag-courses-item_title {"
				+ "            min-height: 87px;"
				+ "            margin: 0 0 25px;"
				+ "            overflow: hidden;"
				+ "            font-weight: bold;"
				+ "            font-size: 20px;"
				+ "            line-height: 1.5em;"
				+ "            color: #FFF;"
				+ "            z-index: 2;"
				+ "            position: relative;"
				+ "        }"
				+ "        .ag-courses-item_title li{"
				+ "            font-weight: 500;"
				+ "            font-size: 17px;"
				+ "        }"
				+ "        .ag-courses-item_date-box {"
				+ "            font-size: 18px;"
				+ "            text-align: end;"
				+ "            color: #FFF;"
				+ "            z-index: 2;"
				+ "            position: relative;"
				+ "        }"
				+ "        .ag-courses-item_date {"
				+ "            font-weight: bold;"
				+ "            color: #f9b234;"
				+ "            -webkit-transition: color .5s ease;"
				+ "            -o-transition: color .5s ease;"
				+ "            transition: color .5s ease;"
				+ "        }"
				+ "        @media (max-width: 500px) {"
				+ "            .ag-courses_item {"
				+ "                width: 350px;"
				+ "                min-width: 350px;"
				+ "            }"
				+ "        }"
				+ "    </style>"
				+ "</head>"
				+ "<body>"
				+ "    <div class=\"ag-courses_item\">"
				+ "        <div class=\"ag-courses-item_link\">"
				+ "            <div class=\"ag-courses-item_title\">"
				+ "                Dear "+dear+","
				+ "                <br><br>"
				+ "                <li>"+ description+"</li>"
				+ "            </div>"
				+ "            <div class=\"ag-courses-item_date-box\">"
				+ "                From :"
				+ "                <span class=\"ag-courses-item_date\">"+sendfrom+"</span>"
				+ "            </div>"
				+ "        </div>"
				+ "    </div>"
				+ "</body>"
				+ "</html>";
		
		return html;
	}
	
	public String OTPHtmlCard(String otp) {
		String htmlCard = "<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "	<title>OTP Message Card</title>"
				+ "	<style>"
				+ "		body {"
				+ "			background-color: #f5f5f5;"
				+ "			font-family: Arial, sans-serif;"
				+ "			padding: 20px;"
				+ "			box-sizing: border-box;"
				+ "		}"
				+ "		.card {"
				+ "			background-color: #333f50;"
				+ "			border-radius: 10px;"
				+ "			padding: 20px;"
				+ "			box-sizing: border-box;"
				+ "			width: 350px;"
				+ "			margin: 0 auto;"
				+ "			text-align: center;"
				+ "		}"
				+ "		.card h1 {"
				+ "			font-size: 24px;"
				+ "			margin: 0;"
				+ "			color: #fff;"
				+ "		}"
				+ "		.card p {"
				+ "			font-size: 15px;"
				+ "			margin: 10px 0 20px 0;"
				+ "			color: #fff;"
				+ "		}"
				+ "		.card code {"
				+ "			display: block;"
				+ "			background-color: #eee;"
				+ "			padding: 10px;"
				+ "			border-radius: 5px;"
				+ "			font-size: 20px;"
				+ "			color: #333f50;"
				+ "		}"
				+ "	</style>"
				+ "</head>"
				+ "<body>"
				+ "	<div class=\"card\">"
				+ "		<h1>One-Time Password</h1>"
				+ "		<p>Your one-time password for resetting your password is shown in below. Please use this code to create a new password.</p>\r\n"
				+ "		<code>"+otp+"</code>"
				+ "	</div>"
				+ "</body>"
				+ "</html>";
		
		return htmlCard;
	}

}
