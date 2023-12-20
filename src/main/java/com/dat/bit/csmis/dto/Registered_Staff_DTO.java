package com.dat.bit.csmis.dto;

public class Registered_Staff_DTO {
	
	private String register_status;
	
	private String staffId;
	
	private String name;
	
	private int doorLogNo;

	public Registered_Staff_DTO() {
		super();
	}

	public String getRegister_status() {
		return register_status;
	}

	public void setRegister_status(String register_status) {
		this.register_status = register_status;
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

	@Override
	public String toString() {
		return "Registered_Staff_DTO [register_status=" + register_status + ", staffId=" + staffId + ", name=" + name
				+ ", doorLogNo=" + doorLogNo + "]";
	}


	
	

}
