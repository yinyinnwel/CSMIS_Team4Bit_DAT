package com.dat.bit.csmis.entity;

import java.util.Date;

public class DoorLog_EXCEL {

	private int doorlogNo;
	
	private Date date_time;

	public DoorLog_EXCEL() {
		super();
	}

	public int getDoorlogNo() {
		return doorlogNo;
	}

	public void setDoorlogNo(int doorlogNo) {
		this.doorlogNo = doorlogNo;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	@Override
	public String toString() {
		return "DoorLog_EXCEL [doorlogNo=" + doorlogNo + ", date_time=" + date_time + "]";
	}
	
}
