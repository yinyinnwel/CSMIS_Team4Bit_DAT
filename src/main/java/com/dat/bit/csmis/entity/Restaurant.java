package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_no")
	private String phone_no;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "receiver")
	private String receiver;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;

	public Restaurant() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", phone_no=" + phone_no
				+ ", email=" + email + ", receiver=" + receiver + ", status=" + status + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}

	
	

}
