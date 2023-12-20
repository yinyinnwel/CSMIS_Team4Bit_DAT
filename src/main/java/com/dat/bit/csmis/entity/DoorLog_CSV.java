package com.dat.bit.csmis.entity;

import com.opencsv.bean.CsvBindByName;

public class DoorLog_CSV {

	@CsvBindByName(column = "No.")
	private int doorlogNo;
	
	@CsvBindByName(column = "Date/Time")
	private String date_time;

	public DoorLog_CSV() {
		super();
	}

	public int getDoorlogNo() {
		return doorlogNo;
	}

	public void setDoorlogNo(int doorlogNo) {
		this.doorlogNo = doorlogNo;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	@Override
	public String toString() {
		return "DoorLog_CSV [doorlogNo=" + doorlogNo + ", date_time=" + date_time + "]";
	}
	
}
