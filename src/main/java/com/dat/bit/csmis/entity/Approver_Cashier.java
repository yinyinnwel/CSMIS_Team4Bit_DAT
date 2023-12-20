package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "approver_cashier")
public class Approver_Cashier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	public Approver_Cashier() {
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
		return "Approver_Cashier [id=" + id + ", name=" + name + ", createdAt=" + createdAt + ", createdBy=" + createdBy
				+ ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy +  "]";
	}


	
}
