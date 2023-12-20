package com.dat.bit.csmis.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="holidays")
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="date")
	private String date;
	
	@Column(name="holiday")
	private String holiday;

	public Holiday() {
		super();
	}

	public Holiday(int id, String date, String holiday) {
		super();
		this.id = id;
		this.date = date;
		this.holiday = holiday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Holiday [id=" + id + ", date=" + date + ", holiday=" + holiday + "]";
	}

	

	
	

}
