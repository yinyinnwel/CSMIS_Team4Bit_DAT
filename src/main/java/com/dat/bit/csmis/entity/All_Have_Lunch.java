package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "all_have_lunch")
public class All_Have_Lunch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="staff_id")
	private String staffId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="door_log_no")
	private int doorLogNo;
	
	@Column(name="date_time")
	private Timestamp date_time;
	
	@Column(name="company_cost")
	private int company_cost;
	
	@Column(name="staff_cost")
	private int staff_cost;
	
	@Column(name="restaurant_id")
	private int restaurnat_id;
	
	@Column(name="register")
	private int register;
	
	@Column(name="status")
	private int status;
	
	@Column(name = "created_at")
	private Timestamp createdAt;

	
	public All_Have_Lunch() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDoorLogNo() {
		return doorLogNo;
	}

	public void setDoorLogNo(int doorLogNo) {
		this.doorLogNo = doorLogNo;
	}

	public Timestamp getDate_time() {
		return date_time;
	}

	public void setDate_time(Timestamp date_time) {
		this.date_time = date_time;
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

	public int getRestaurnat_id() {
		return restaurnat_id;
	}

	public void setRestaurnat_id(int restaurnat_id) {
		this.restaurnat_id = restaurnat_id;
	}

	public int getRegister() {
		return register;
	}

	public void setRegister(int register) {
		this.register = register;
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

	@Override
	public String toString() {
		return "All_Have_Lunch [id=" + id + ", staffId=" + staffId + ", name=" + name + ", doorLogNo=" + doorLogNo
				+ ", date_time=" + date_time + ", company_cost=" + company_cost + ", staff_cost=" + staff_cost
				+ ", restaurnat_id=" + restaurnat_id + ", register=" + register + ", status=" + status + ", createdAt="
				+ createdAt + "]";
	}




	
	

}
