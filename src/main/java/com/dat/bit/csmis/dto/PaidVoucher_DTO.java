package com.dat.bit.csmis.dto;

import java.sql.Timestamp;

public class PaidVoucher_DTO {

	private String start_date;

	private String end_date;

	private String description;

	private String cateringService;

	private String cashier;

	private String receiver;

	private String approver;

	private int actualCount;

	private double companyCost;

	private double staffCost;

	private double totalCost;

	private double companyAmount;

	private double staffAmount;

	private double totalAmount;
	
	private double totalPaidAmount;

	private String paymentDate;

	private int status;

	private String paymentMethod;

	private Timestamp createdAt;

	private String createdBy;

	private Timestamp updatedAt;

	private String updatedBy;

	public PaidVoucher_DTO() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCateringService() {
		return cateringService;
	}

	public void setCateringService(String cateringService) {
		this.cateringService = cateringService;
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

	public int getActualCount() {
		return actualCount;
	}

	public void setActualCount(int actualCount) {
		this.actualCount = actualCount;
	}

	public double getCompanyCost() {
		return companyCost;
	}

	public void setCompanyCost(double companyCost) {
		this.companyCost = companyCost;
	}

	public double getStaffCost() {
		return staffCost;
	}

	public void setStaffCost(double staffCost) {
		this.staffCost = staffCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(double totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public double getCompanyAmount() {
		return companyAmount;
	}

	public void setCompanyAmount(double companyAmount) {
		this.companyAmount = companyAmount;
	}

	public double getStaffAmount() {
		return staffAmount;
	}

	public void setStaffAmount(double staffAmount) {
		this.staffAmount = staffAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "PaidVoucher_DTO [description=" + description + ", cateringService=" + cateringService + ", cashier="
				+ cashier + ", receiver=" + receiver + ", approver=" + approver + ", actualCount=" + actualCount
				+ ", companyCost=" + companyCost + ", staffCost=" + staffCost + ", totalCost=" + totalCost
				+ ", companyAmount=" + companyAmount + ", staffAmount=" + staffAmount + ", totalAmount=" + totalAmount
				+ ", paymentDate=" + paymentDate + ", status=" + status + ", paymentMethod=" + paymentMethod
				+ ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + "]";
	}

}
