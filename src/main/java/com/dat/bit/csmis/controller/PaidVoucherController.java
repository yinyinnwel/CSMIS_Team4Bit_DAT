package com.dat.bit.csmis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.dto.PaidVoucher_DTO;
import com.dat.bit.csmis.dto.ReportRequestModel;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.report.ApplicationData;
import com.dat.bit.csmis.service.VoucherService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class PaidVoucherController {
	
	@Autowired
	private StaffService staffService;
	@Autowired
	private VoucherService paidVoucherService;
	
	
	private String tempstartDate="",tempendDate = "";
	private double totalAmount = 0;
	private List<PaidVoucher_DTO> tempPaidVoucher_DTOs = new ArrayList<>();
	
	public String paidVoucherPage(String staffId,Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		//--clear tempDTOs when you click page refresh button from browser
		if(tempstartDate.equals("") && tempendDate.equals("")) {
			tempPaidVoucher_DTOs.clear();
		}

		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("startDate",tempstartDate);
		model.addAttribute("endDate",tempendDate);
		model.addAttribute("paidVouchers",tempPaidVoucher_DTOs);
		model.addAttribute("totalAmount",totalAmount);
		
		return "invoice/paidVoucher";
	}
	
	@GetMapping("/invoice_paid_back_to_invoice_page")
	public String backtoInvoicePage() {
		clearAllTampVariables();

		return "redirect:/DAT/invoice";
	}
	
	@GetMapping("/invoice_paid_voucher")
	public String paidVoucher(Authentication authentication, Model model) {
		String staffId = authentication.getName();
		
		return paidVoucherPage(staffId, model);
	}
	
	@PostMapping("/invoice_paid_voucher_date_confirm")
	public String paidVoucherDateConfirm(@RequestParam("startDate")String startDate,
										@RequestParam("endDate")String endDate) {
		
		tempstartDate = startDate;
		tempendDate = endDate;
		
		try {
			//System.out.println(startDate+":"+endDate);
			tempPaidVoucher_DTOs = paidVoucherService.getPaidVoucherByDateRange(startDate, endDate);
			
			totalAmount = 0;
			for(PaidVoucher_DTO dto : tempPaidVoucher_DTOs) {
				totalAmount += dto.getTotalAmount();
			}
			
		} catch (Exception e) {
			System.out.println("Error from paid voucher date confirm");
			System.out.println(e.getMessage());
		}
		
		return "redirect:/DAT/invoice_paid_voucher";
	}

	//============create paid voucher invoice================= 
	
	@PostMapping("/invoice_paid_voucherexport")
	public String createPaidVoucher(Model model) {
	//	System.out.println("hello");
		
		if(!tempPaidVoucher_DTOs.isEmpty()) {
			List<PaidVoucher_DTO> paidVoucher_DTOs = new ArrayList<>();
			for (PaidVoucher_DTO dto : tempPaidVoucher_DTOs) {
				PaidVoucher_DTO thePaidVoucher = new PaidVoucher_DTO();
				
				thePaidVoucher.setStart_date(tempstartDate);
				thePaidVoucher.setEnd_date(tempendDate);
				thePaidVoucher.setDescription(dto.getDescription());
				thePaidVoucher.setCateringService(dto.getCateringService());
				thePaidVoucher.setCashier(dto.getCashier());
				thePaidVoucher.setReceiver(dto.getReceiver());
				thePaidVoucher.setApprover(dto.getApprover());
				thePaidVoucher.setActualCount(dto.getActualCount());
				thePaidVoucher.setTotalCost(dto.getTotalCost());
				thePaidVoucher.setTotalAmount(dto.getTotalAmount());
				thePaidVoucher.setPaymentDate(dto.getPaymentDate());
				thePaidVoucher.setPaymentMethod(dto.getPaymentMethod());
				thePaidVoucher.setTotalPaidAmount(totalAmount);

				paidVoucher_DTOs.add(thePaidVoucher);
			}

			System.out.println("=>"+tempstartDate);
			Map<String, Object> parameters = new HashMap<>();
			ReportRequestModel request = new ReportRequestModel("paidVoucher", "Paid Voucher Report");
			request.setParameters(parameters);
			request.setMetaDataTitle("Paid Voucher Report");
			request.setDataSource(paidVoucher_DTOs);
			
			model.addAttribute(ApplicationData.REPORT_REQUEST_KEY, request);
			
			clearAllTampVariables();
			
			//return ApplicationData.EXCEL_VIEW;
			return ApplicationData.PDF_VIEW;
		}else {
			return "redirect:/DAT/invoice_paid_voucher";
		}

		
		
	}
	
	
	//============================== Other functions ====================================
	
	public void clearAllTampVariables() {
		tempstartDate = "";
		tempendDate = "";
		totalAmount = 0;
		tempPaidVoucher_DTOs.clear();
	}

}
