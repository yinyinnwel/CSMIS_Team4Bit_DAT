package com.dat.bit.csmis.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice {
	
	@Id
	@Column(name = "voucher_no")
	private String voucher_no;
	
	@Column(name = "restaurant_id")
	private int restaurantId;
	
	@Column(name = "start_date")
	private Date start_date;
	
	@Column(name = "end_date")
	private Date end_date;
	
	@Column(name = "payment_date")
	private Date paymentDate;
	
	@Column(name = "actual_count")
	private int actual_count;
	
	@Column(name = "registered_count")
	private int registered_count;
	
	@Column(name = "company_cost")
	private double companyCost;
	
	@Column(name = "staff_cost")
	private double staffCost;
	
	@Column(name = "total_company_cost")
	private double totalCompanyCost;
	
	@Column(name = "total_staff_cost")
	private double totalStaffCost;
	
	@Column(name = "total_amount")
	private double totalAmount;
	
	@Column(name = "cashier")
	private String cashier;
	
	@Column(name = "receiver")
	private String receiver;
	
	@Column(name = "approver")
	private String approver;
	
	@Column(name = "payment_method")
	private String paymentMethod;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;

	public Invoice() {
		super();
	}

	public String getVoucher_no() {
		return voucher_no;
	}

	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getActual_count() {
		return actual_count;
	}

	public void setActual_count(int actual_count) {
		this.actual_count = actual_count;
	}

	public int getRegistered_count() {
		return registered_count;
	}

	public void setRegistered_count(int registered_count) {
		this.registered_count = registered_count;
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

	public double getTotalCompanyCost() {
		return totalCompanyCost;
	}

	public void setTotalCompanyCost(double totalCompanyCost) {
		this.totalCompanyCost = totalCompanyCost;
	}

	public double getTotalStaffCost() {
		return totalStaffCost;
	}

	public void setTotalStaffCost(double totalStaffCost) {
		this.totalStaffCost = totalStaffCost;
	}
	
	

	public double getTotalAmount() {
		return totalAmount;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
		return "Invoice [voucher_no=" + voucher_no + ", restaurantId=" + restaurantId + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", paymentDate=" + paymentDate + ", actual_count=" + actual_count
				+ ", registered_count=" + registered_count + ", companyCost=" + companyCost + ", staffCost=" + staffCost
				+ ", totalCompanyCost=" + totalCompanyCost + ", totalStaffCost=" + totalStaffCost + ", totalAmount="
				+ totalAmount + ", cashier=" + cashier + ", receiver=" + receiver + ", approver=" + approver
				+ ", paymentMethod=" + paymentMethod + ", status=" + status + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}


	

}
