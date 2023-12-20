package com.dat.bit.csmis.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.dto.Invoice_DTO;
import com.dat.bit.csmis.dto.PaymentVoucherDTO;
import com.dat.bit.csmis.dto.ReportRequestModel;
import com.dat.bit.csmis.entity.Invoice;
import com.dat.bit.csmis.entity.Restaurant;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.report.ApplicationData;
import com.dat.bit.csmis.service.PaymentVoucherService;
import com.dat.bit.csmis.service.RestaurantService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class PaymentVoucherController {

	@Autowired
	private StaffService staffService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private PaymentVoucherService paymentVoucherService;

	private String tempStartDate = "", tempEndDate = "", voucherCode = "";
	private List<PaymentVoucherDTO> tempdto = new ArrayList<>();
	private double totalAmount = 0;
	private boolean cashierStatus=false,approverStatus=false,status=false;

	public String paymentVoucherPage(String staffId, Model model) {
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
	
		// ----- Get all restaurant Service -------------------
		List<Restaurant> restaurants = restaurantService.getAllRestaurantList();

		// ======== get all Cashier and Approver ==========
		List<String> approver_Cashiers = paymentVoucherService.getAllApproverCashier();

		model.addAttribute("loginUserName", loginUsername);
		model.addAttribute("restaurants", restaurants);
		model.addAttribute("startDate", tempStartDate);
		model.addAttribute("endDate", tempEndDate);
		model.addAttribute("transactions", tempdto);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("voucherCode", voucherCode);
		model.addAttribute("cashierStatus",cashierStatus);
		model.addAttribute("approverStatus",approverStatus);
		model.addAttribute("dateRangeStatus",status);

		model.addAttribute("approver_Cashiers", approver_Cashiers);

		return "/invoice/paymentVoucher";
	}

	@GetMapping("/invoice_back_to_invoice_page")
	public String backtoInvoicePage() {
		clearTempVariables();

		return "redirect:/DAT/invoice";
	}

	@GetMapping("/invoice_payment_voucher")
	public String paymentVoucher(Authentication authentication, Model model) {
		String staffId = authentication.getName();

		return paymentVoucherPage(staffId, model);
	}

	@PostMapping("/invoice_payment_voucher_date_confirm")
	public String paymentVoucherDateConfirm(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {

		tempStartDate = startDate;
		tempEndDate = endDate;
		
		status = paymentVoucherService.checkVoucherDateRange(startDate, endDate);

		if(!status) {//no date are contain in all vouchers
			List<LocalDate> dates = getDateRange(startDate, endDate);

			try {
				tempdto = paymentVoucherService.getActualCount(dates);

			} catch (Exception e) {
				tempdto = new ArrayList<>();
			}

			if (!tempdto.isEmpty()) {
				voucherCode = generateVoucherCode();
			} else {
				voucherCode = "";
			}

			totalAmount = 0;
			for (PaymentVoucherDTO dto : tempdto) {
				totalAmount += (double) (dto.getAmount());
			}
		}else {//some date are contain in some vouchers
			tempdto = new ArrayList<>();
			voucherCode = "";
			totalAmount = 0;
		}

		return "redirect:/DAT/invoice_payment_voucher";
	}

	@PostMapping("/invoice_payment_voucher_add_cashier_approver")
	public String addNewCashier_Approver(@RequestParam("name") String name, Authentication authentication) {
		String staffId = authentication.getName();

		paymentVoucherService.addNewCashierApprover(name, staffId);

		return "redirect:/DAT/invoice_payment_voucher";
	}

	@PostMapping("/invoice_payment_voucher_update_cashier_approver")
	public String updateCashier_Approver(@RequestParam("id") int id, @RequestParam("name") String name,
			Authentication authentication) {
		String staffId = authentication.getName();

		paymentVoucherService.updateCashierApprover(id, name, staffId);

		return "redirect:/DAT/invoice_payment_voucher";
	}

	@PostMapping("/invoice_create_payment_voucher")
	public String createPaymentVoucher(@RequestParam("restaurantId") String restaurantId,
			@RequestParam("paymentDate") String paymentDate, @RequestParam("cashier") String cashier,
			@RequestParam("receiverInput") String receiver, @RequestParam("approver") String approver,
			@RequestParam("paymentMethod") String paymentMethod, @RequestParam("status") int status,
			Authentication authentication, Model model, HttpServletResponse response){

		String staffId = authentication.getName();
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		if(!tempdto.isEmpty()) {
			if((cashier.equals("") || cashier == null) || (approver.equals("") || approver == null)) {
				cashierStatus = true;
				approverStatus = true;
				
				return "redirect:/DAT/invoice_payment_voucher";
			}else {
				int actual_count = 0;
				int registerd_count = 0;
				double company_cost = 0;
				double staff_cost = 0;

				for (PaymentVoucherDTO dto : tempdto) {
					//System.out.println(dto);
					actual_count += dto.getActualCount();
					registerd_count += dto.getRegisteredCount();
					company_cost = dto.getCompany_cost();
					staff_cost = dto.getStaff_cost();
				}

				double totalCompanyCost = company_cost * actual_count;
				double totalStaffCost = staff_cost * actual_count;

				Restaurant restaurant = restaurantService.getRestaurantById(Integer.parseInt(restaurantId));
				String restaurantName = restaurant.getName();

				Invoice invoice = new Invoice();
				try {
					invoice.setVoucher_no(voucherCode);
					invoice.setRestaurantId(Integer.parseInt(restaurantId));
					invoice.setStart_date(java.sql.Date.valueOf(tempStartDate));
					invoice.setEnd_date(java.sql.Date.valueOf(tempEndDate));
					invoice.setPaymentDate(java.sql.Date.valueOf(paymentDate));
					invoice.setActual_count(actual_count);
					invoice.setRegistered_count(registerd_count);
					invoice.setCompanyCost(company_cost);
					invoice.setStaffCost(staff_cost);
					invoice.setTotalCompanyCost(totalCompanyCost);
					invoice.setTotalStaffCost(totalStaffCost);
					invoice.setTotalAmount(totalAmount);
					invoice.setCashier(cashier);
					invoice.setReceiver(receiver);
					invoice.setApprover(approver);
					invoice.setPaymentMethod(paymentMethod);
					invoice.setStatus(status);// <== dont forget to change
					invoice.setCreatedAt(new Timestamp(new Date().getTime()));
					invoice.setCreatedBy("(" + staffId + ") " + loginUsername);
				} catch (Exception e) {
					System.out.println("Info => Error occures in creating Incoive Object because "
							+ "I cleared temp variable after creating Report!It is not error.Don't worry!");
				}
				
				//System.out.println(invoice);

			//=============== Save Invoice to invoice Table from DB ========================
				paymentVoucherService.savePaymentVoucher(invoice);
				
			//================ Create Invoice List to create report =================================

				List<Invoice_DTO> invoice_DTOs = new ArrayList<>();
				for (PaymentVoucherDTO dto : tempdto) {
					Invoice_DTO thePaymentVoucher = new Invoice_DTO();
					thePaymentVoucher.setVoucher_no(voucherCode);
					thePaymentVoucher.setRestaurantName(restaurantName);
//					thePaymentVoucher.setStart_date(tempStartDate);
//					thePaymentVoucher.setEnd_date(tempEndDate);
					thePaymentVoucher.setStartEnd_date(tempStartDate+"~"+tempEndDate);
					thePaymentVoucher.setPaymentDate(paymentDate);
//				thePaymentVoucher.setDtos(tempdto);
					
					thePaymentVoucher.setDate(dto.getDate());
					thePaymentVoucher.setRegisteredCount(dto.getRegisteredCount());
					thePaymentVoucher.setActualCount(dto.getActualCount());
					thePaymentVoucher.setDifference(dto.getDifference());
					thePaymentVoucher.setPrice(dto.getPrice());
					thePaymentVoucher.setAmount(dto.getAmount());

					thePaymentVoucher.setTotalAmount(totalAmount);
					thePaymentVoucher.setCashier(cashier);
					thePaymentVoucher.setReceiver(receiver);
					thePaymentVoucher.setApprover(approver);
					thePaymentVoucher.setPaymentMethod(paymentMethod.equals("cash") ? "Cash" : "Bank");

					invoice_DTOs.add(thePaymentVoucher);
				}

				Map<String, Object> parameters = new HashMap<>();
				ReportRequestModel request = new ReportRequestModel("paymentVoucher", "Payment Voucher Report");
				request.setParameters(parameters);
				request.setMetaDataTitle("Payment Voucher Report");
				request.setDataSource(invoice_DTOs);
				request.setRedirectTo("/DAT/invoice_payment_voucher");
				
				model.addAttribute(ApplicationData.REPORT_REQUEST_KEY, request);
				
				clearTempVariables();

				
//				return ApplicationData.EXCEL_VIEW;
				return ApplicationData.PDF_VIEW;
			}
		}else {
			return "redirect:/DAT/invoice_payment_voucher";
		}
	}
	

//	@GetMapping("/report")
//	public PDFReportView exportDetailReport(Model model, HttpSession session) throws ParseException {
//
//		List<Employee> theEmployee =  employeeService.findAll();		
//		Map<String, Object> parameters = new HashMap<>();
//		ReportRequestModel request = new ReportRequestModel("DetailReport", "Detail Report");
//		request.setParameters(parameters);
//		request.setMetaDataTitle("Employees Report");
//		request.setDataSource(theEmployee);
//		model.addAttribute(ApplicationData.REPORT_REQUEST_KEY, request);
//
//		return new PDFReportView();
//	}

	// ============================ Other Functions// =========================================\\\

	public void clearTempVariables() {
		tempStartDate = "";
		tempEndDate = "";
		tempdto.clear();
		totalAmount = 0;
		voucherCode = "";
		cashierStatus = false;
		approverStatus = false;
		status = false;
	}

	public List<LocalDate> getDateRange(String startDateStr, String endDateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		List<LocalDate> datesInRange = new ArrayList<>();
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		for (int i = 0; i <= numOfDaysBetween; i++) {
			datesInRange.add(startDate.plusDays(i));
		}
		return datesInRange;
	}

	public String generateVoucherCode() {
		String PREFIX = "CS";
		String DATE_FORMAT = "yyyyMMdd";

		Random random = new Random();
		int randomNum = random.nextInt(9000) + 1000;
		String randomNumber = String.valueOf(randomNum);

		Date currentD = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String currentDate = dateFormat.format(currentD);

		String voucherCode = PREFIX + randomNumber + "-" + currentDate;

		return voucherCode;
	}

}
