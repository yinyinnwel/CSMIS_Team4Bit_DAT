package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lunch_register")
public class LunchRegister {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "registered_date")
	private Timestamp registered_date;
	
	@Column(name = "register_for")
	private Timestamp register_for;
	
	@Column(name = "updated_date")
	private Timestamp updated_date;
	
	@Column(name = "register_status")
	private String register_status;
	
	@Column(name = "staff_id")
	private String staffId;

	public LunchRegister() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getRegistered_date() {
		return registered_date;
	}

	public void setRegistered_date(Timestamp registered_date) {
		this.registered_date = registered_date;
	}
	

	public Timestamp getRegister_for() {
		return register_for;
	}

	public void setRegister_for(Timestamp register_for) {
		this.register_for = register_for;
	}

	public Timestamp getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Timestamp updated_date) {
		this.updated_date = updated_date;
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

	@Override
	public String toString() {
		return "LunchRegister [id=" + id + ", registered_date=" + registered_date + ", register_for=" + register_for
				+ ", updated_date=" + updated_date + ", register_status=" + register_status + ", staffId=" + staffId
				+ "]";
	}

	
	

}
