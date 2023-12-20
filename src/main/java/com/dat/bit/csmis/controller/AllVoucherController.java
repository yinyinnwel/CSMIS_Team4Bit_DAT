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

import com.dat.bit.csmis.dto.PaidVoucher_DTO;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.StaffService;
import com.dat.bit.csmis.service.VoucherService;

@Controller
@RequestMapping("/DAT")
public class AllVoucherController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private VoucherService voucherService;
	
	private String tampstartDate="",tampendDate = "",filter="all";
	private List<PaidVoucher_DTO> tampPaidVoucher_DTOs = new ArrayList<>();
	private String noti_message="",updateMessage_class="notiMessageBox";
	
	public String allVoucherPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		if(!tampstartDate.equals("") && !tampendDate.equals("")) {
			try {
				tampPaidVoucher_DTOs = voucherService.getAllVoucherByDateRangeAndFilterBy(tampstartDate, tampendDate, filter);
			} catch (Exception e) {
				System.out.println("Error form all voucher !Line no between 30 and 40!");
				System.out.println(e.getMessage());
			}
		}else {
			tampPaidVoucher_DTOs = new ArrayList<>();
		}

		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("startDate",tampstartDate);
		model.addAttribute("endDate",tampendDate);
		model.addAttribute("paidVouchers",tampPaidVoucher_DTOs);
		model.addAttribute("filter",filter);
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updateMessage_class = "notiMessageBox";
		
		return "invoice/allVoucher";
	}
	
	@GetMapping("/invoice_all_back_to_invoice_page")
	public String backtoInvoicePage() {
		clearAllTampVariables();

		return "redirect:/DAT/invoice";
	}
	
	@GetMapping("/invoice_all_voucher")
	public String allVoucher(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return allVoucherPage(staffId, model);
	}
//====================== Date range and filter ==============================
	@PostMapping("/invoice_all_voucher_date_confirm")
	public String dateConfirm(@RequestParam("startDate")String startDate,
								@RequestParam("endDate")String endDate) {
		
		tampstartDate = startDate;
		tampendDate = endDate;
		
		return "redirect:/DAT/invoice_all_voucher";
	}
//===================== Edit voucher Status =================================
	@GetMapping("/invoice_all_voucher_status_edit")
	public String editStatusVoucher(@RequestParam("descriptionHidden")String descriptionHidden,
									@RequestParam("voucherStatus")int voucherStatus,
									Authentication authentication) {
		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		String updatedBy = "(" + staffId + ") " + loginUsername;
		
		voucherService.updateVoucherStatusByVoucherNo(descriptionHidden, voucherStatus, updatedBy);
		
		//============ Show success noti dialog ===============
		
		updateMessage_class = "notiMessageBox success open";
		noti_message = "You have successfully updated.";
		
		return "redirect:/DAT/invoice_all_voucher";
	}
	
//================== Filter mapping =========================================
	
	@GetMapping("/invoice_all_voucher_filter_all")
	public String allVoucherFilterByAll() {
		
		filter = "all";
		
		return "redirect:/DAT/invoice_all_voucher";
	}
	
	@GetMapping("/invoice_all_voucher_filter_paid")
	public String allVoucherFilterByPaid() {
		
		filter = "paid";
		
		return "redirect:/DAT/invoice_all_voucher";
	}
	
	@GetMapping("/invoice_all_voucher_filter_unpaid")
	public String allVoucherFilterByUnpaid() {
		
		filter = "unpaid";
		
		return "redirect:/DAT/invoice_all_voucher";
	}
	
	
	//============================== Other functions ====================================
	
		public void clearAllTampVariables() {
			tampstartDate = "";
			tampendDate = "";
			filter = "all";
			tampPaidVoucher_DTOs.clear();
		}

}
