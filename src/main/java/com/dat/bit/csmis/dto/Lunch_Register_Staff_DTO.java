package com.dat.bit.csmis.dto;


public class Lunch_Register_Staff_DTO {
	
	private String staffId;
	
	private String name;
	
	private String department;
	
	private String team;
	
	private int status;
	
	private String role;
	
	private int lunch_register_id;
	
	private String registered_date;
	
	private String register_for;
	
	private String updated_date;
	
	private String register_status;

	public Lunch_Register_Staff_DTO() {
		super();
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getLunch_register_id() {
		return lunch_register_id;
	}

	public void setLunch_register_id(int lunch_register_id) {
		this.lunch_register_id = lunch_register_id;
	}

	public String getRegistered_date() {
		return registered_date;
	}

	public void setRegistered_date(String registered_date) {
		this.registered_date = registered_date;
	}

	public String getRegister_for() {
		return register_for;
	}

	public void setRegister_for(String register_for) {
		this.register_for = register_for;
	}

	public String getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	public String getRegister_status() {
		return register_status;
	}

	public void setRegister_status(String register_status) {
		this.register_status = register_status;
	}

	@Override
	public String toString() {
		return "Lunch_Register_Staff_DTO [staffId=" + staffId + ", name=" + name + ", department=" + department
				+ ", team=" + team + ", status=" + status + ", role=" + role + ", lunch_register_id="
				+ lunch_register_id + ", registered_date=" + registered_date + ", register_for=" + register_for
				+ ", updated_date=" + updated_date + ", register_status=" + register_status + "]";
	}
	
	

}
