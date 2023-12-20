package com.dat.bit.csmis.dto;
//
//import java.util.List;
//
//import com.dat.bit.csmis.dto.PaymentVoucherDTO;

public class Invoice_DTO {

	private String voucher_no;

	private String restaurantName;

	private String start_date;

	private String end_date;

	private String paymentDate;
	
	private String startEnd_date;
	
//	private List<PaymentVoucherDTO> dtos;

//-----------------------------
	private String date;
	
	private int registeredCount;
	
	private int actualCount;
	
	private int difference;
	
	private int company_cost;
	
	private int staff_cost;
	
	private int price;
	
	private double amount;
//-------------------------------	
	private double totalAmount;

	private String cashier;

	private String receiver;

	private String approver;

	private String paymentMethod;

	public Invoice_DTO() {
		super();
	}

	public String getVoucher_no() {
		return voucher_no;
	}

	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getStartEnd_date() {
		return startEnd_date;
	}

	public void setStartEnd_date(String startEnd_date) {
		this.startEnd_date = startEnd_date;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

//	public List<PaymentVoucherDTO> getDtos() {
//		return dtos;
//	}
//
//	public void setDtos(List<PaymentVoucherDTO> dtos) {
//		this.dtos = dtos;
//	}
	
	
	
	public double getTotalAmount() {
		return totalAmount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getRegisteredCount() {
		return registeredCount;
	}

	public void setRegisteredCount(int registeredCount) {
		this.registeredCount = registeredCount;
	}

	public int getActualCount() {
		return actualCount;
	}

	public void setActualCount(int actualCount) {
		this.actualCount = actualCount;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	public int getCompany_cost() {
		return company_cost;
	}

	public void setCompany_cost(int company_cost) {
		this.company_cost = company_cost;
	}

	public int getStaff_cost() {
		return staff_cost;
	}

	public void setStaff_cost(int staff_cost) {
		this.staff_cost = staff_cost;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	

}
