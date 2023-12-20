package com.dat.bit.csmis.dto;

public class AvoidMeat_DTO {
	
	private String name;
	
	private int total;

	public AvoidMeat_DTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "AvoidMeat_DTO [name=" + name + ", total=" + total + "]";
	}
	
	

}
