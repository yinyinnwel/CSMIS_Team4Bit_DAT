package com.dat.bit.csmis.dto;

import java.sql.Timestamp;

public class Staff_DTO {

	private int id;
	
	private String role;
	
	private String division;
	
	private String staffId;
	
	private String name;
	
	private String doorLogNo;
	
	private String department;
	
	private int status;
	
	private String team;
	
	private String email;
	
	private int email_noti;
	
	private String password;
	
	private Timestamp createdAt;
	
	private String createdBy;
	
	private Timestamp updatedAt;
	
	private String updatedBy;
	
	private String avoidMeats;
	
	private Timestamp av_createdAt;
	
	private Timestamp av_updatedAt;
	
	
	private int total;

	public Staff_DTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
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

	public String getDoorLogNo() {
		return doorLogNo;
	}

	public void setDoorLogNo(String doorLogNo) {
		this.doorLogNo = doorLogNo;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEmail_noti() {
		return email_noti;
	}

	public void setEmail_noti(int email_noti) {
		this.email_noti = email_noti;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	

	public String getAvoidMeats() {
		return avoidMeats;
	}

	public void setAvoidMeats(String avoidMeats) {
		this.avoidMeats = avoidMeats;
	}

	public Timestamp getAv_createdAt() {
		return av_createdAt;
	}

	public void setAv_createdAt(Timestamp av_createdAt) {
		this.av_createdAt = av_createdAt;
	}

	public Timestamp getAv_updatedAt() {
		return av_updatedAt;
	}

	public void setAv_updatedAt(Timestamp av_updatedAt) {
		this.av_updatedAt = av_updatedAt;
	}

	@Override
	public String toString() {
		return "Staff_DTO [id=" + id + ", role=" + role + ", division=" + division + ", staffId=" + staffId + ", name="
				+ name + ", doorLogNo=" + doorLogNo + ", department=" + department + ", status=" + status + ", team="
				+ team + ", email=" + email + ", email_noti=" + email_noti + ", password=" + password + ", createdAt="
				+ createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy
				+ ", avoidMeats=" + avoidMeats + ", av_createdAt=" + av_createdAt + ", av_updatedAt=" + av_updatedAt
				+ ", total=" + total + "]";
	}


	
	

	
	
}
