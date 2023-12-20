package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cost")
public class Cost {
	
	@Id
	@Column(name = "cost_id")
	private String costId;
	
	@Column(name = "company_cost")
	private int companyCost;
	
	@Column(name = "staff_cost")
	private int staffCost;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;

	public Cost() {
		super();
	}

	public String getCostId() {
		return costId;
	}

	public void setCostId(String costId) {
		this.costId = costId;
	}

	public int getCompanyCost() {
		return companyCost;
	}

	public void setCompanyCost(int companyCost) {
		this.companyCost = companyCost;
	}

	public int getStaffCost() {
		return staffCost;
	}

	public void setStaffCost(int staffCost) {
		this.staffCost = staffCost;
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
		return "Cost [costId=" + costId + ", companyCost=" + companyCost + ", staffCost=" + staffCost + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	
	

}
