package com.dat.bit.csmis.entity;

public class Staff_ECXCEL {

	private int id;
	
	private String division;
	
	private String staffId;
	
	private String name;
	
	private int doorLogNo;
	
	private String department;
	
	private String team;
	
	private String email;
	
	private String status;
	
	private String createdBy;

	public Staff_ECXCEL() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getDoorLogNo() {
		return doorLogNo;
	}

	public void setDoorLogNo(int doorLogNo) {
		this.doorLogNo = doorLogNo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Staff_ECXCEL [id=" + id + ", division=" + division + ", staffId=" + staffId + ", name=" + name
				+ ", doorLogNo=" + doorLogNo + ", department=" + department + ", team=" + team + ", email=" + email
				+ ", status=" + status + ", createdBy=" + createdBy + "]";
	}
	
	
}
