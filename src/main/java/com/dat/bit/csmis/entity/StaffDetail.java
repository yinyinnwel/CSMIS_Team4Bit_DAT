package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff_detail")
public class StaffDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "division")
	private String division;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "team")
	private String team;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "email_noti")
	private int email_noti;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "staff_id")
	private String staff_id;

	public StaffDetail() {
		super();
	}

	

	public StaffDetail(int id, String division, String department, String team, String email, int email_noti,
			Timestamp createdAt, String createdBy, Timestamp updatedAt, String updatedBy,
			String staff_id) {
		super();
		this.id = id;
		this.division = division;
		this.department = department;
		this.team = team;
		this.email = email;
		this.email_noti = email_noti;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.staff_id = staff_id;
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

	public int getEmail_noti() {
		return email_noti;
	}

	public void setEmail_noti(int email_noti) {
		this.email_noti = email_noti;
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

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}



	@Override
	public String toString() {
		return "StaffDetail [id=" + id + ", division=" + division + ", department=" + department + ", team=" + team
				+ ", email=" + email + ", email_noti=" + email_noti + ", createdAt=" + createdAt + ", createdBy="
				+ createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + ", staff_id=" + staff_id + "]";
	}


}
