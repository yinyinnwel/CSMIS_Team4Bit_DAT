package com.dat.bit.csmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant_comment")
public class Restaurant_Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "comment_at")
	private Timestamp commentAt;
	
	@Column(name = "restaurant_id")
	private int restaurant_id;
	
	@Column(name = "staff_id")
	private String staff_id;

	public Restaurant_Comment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCommentAt() {
		return commentAt;
	}

	public void setCommentAt(Timestamp commentAt) {
		this.commentAt = commentAt;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	@Override
	public String toString() {
		return "Restaurant_Comment [id=" + id + ", comment=" + comment + ", commentAt=" + commentAt + ", restaurant_id="
				+ restaurant_id + ", staff_id=" + staff_id + "]";
	}
	
	

}
