package com.dat.bit.csmis.entity;

import com.opencsv.bean.CsvBindByName;

public class Holiday_CSV {

	@CsvBindByName(column = "Date")
	private String date;

	@CsvBindByName(column = "Holiday")
	private String holiday;

	public Holiday_CSV() {
		super();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

}
