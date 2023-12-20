package com.dat.bit.csmis.dto;

public class PaymentVoucherDTO {
	
	private String date;
	
	private int registeredCount;
	
	private int actualCount;
	
	private int difference;
	
	private int company_cost;
	
	private int staff_cost;
	
	private int price;
	
	private double amount;

	public PaymentVoucherDTO() {
		super();
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


	@Override
	public String toString() {
		return "PaymentVoucherDTO [date=" + date + ", registeredCount=" + registeredCount + ", actualCount="
				+ actualCount + ", difference=" + difference + ", company_cost=" + company_cost + ", staff_cost="
				+ staff_cost + ", price=" + price + ", amount=" + amount + "]";
	}


	

}
