package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "avoid_meat_list")
public class AvoidMeatList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name  = "id")
	private int id;
	
	@Column(name  = "name")
	private String name;
	
	@Column(name  = "status")
	private int status;
	
	@Column(name  = "created_at")
	private Timestamp createdAt;
	
	@Column(name  = "created_by")
	private String createdBy;
	
	@Column(name  = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name  = "updated_by")
	private String updatedBy;

	public AvoidMeatList() {
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
		return "AvoidMeatList [id=" + id + ", name=" + name + ", status=" + status + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	
	

}
