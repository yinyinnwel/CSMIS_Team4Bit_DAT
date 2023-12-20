package com.dat.bit.csmis.entity;

import com.opencsv.bean.CsvBindByName;

public class Staff_CSV {
	
	@CsvBindByName(column = "Sr.")
	private int id;
	
	@CsvBindByName(column = "Division")
	private String division;
	
	@CsvBindByName(column = "Staff ID")
	private String staffId;
	
	@CsvBindByName(column = "Name")
	private String name;
	
	@CsvBindByName(column = "DoorLogNo.")
	private int doorLogNo;
	
	@CsvBindByName(column = "Dept")
	private String department;
	
	@CsvBindByName(column = "Team")
	private String team;
	
	@CsvBindByName(column = "Email")
	private String email;
	
	
	private String createdBy;
	
	

	public Staff_CSV() {
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


	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	@Override
	public String toString() {
		return "Staff_CSV [id=" + id + ", division=" + division + ", staffId=" + staffId + ", name=" + name
				+ ", doorLogNo=" + doorLogNo + ", department=" + department + ", team=" + team + ", email=" + email
				 + ", createdBy=" + createdBy + "]";
	}




	
	

}
