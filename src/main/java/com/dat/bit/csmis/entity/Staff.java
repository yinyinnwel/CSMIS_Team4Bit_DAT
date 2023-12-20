package com.dat.bit.csmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff {
	
	@Id
	@Column(name="staff_id")
	private String staffId;
	
	@Column(name="username")
	private String name;
	
	@Column(name="door_log_no")
	private String doorLogNo;
	
	@Column(name="enabled")
	private int status;
	
	@Column(name = "password")
	private String password;
	
	@Column(name="role")
	private String role;


	public Staff() {
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


	public String getDoorLogNo() {
		return doorLogNo;
	}


	public void setDoorLogNo(String doorLogNo) {
		this.doorLogNo = doorLogNo;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


}
