package com.dat.bit.csmis.entity;

public class Department {
	
	private String name;
	
	private int male;
	
	private int female;
	
	private double malePercentage;
	
	private double femalePercentage;

	public Department() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public int getFemale() {
		return female;
	}

	public void setFemale(int female) {
		this.female = female;
	}

	
	public double getMalePercentage() {
		return malePercentage;
	}

	public void setMalePercentage(double malePercentage) {
		this.malePercentage = malePercentage;
	}

	public double getFemalePercentage() {
		return femalePercentage;
	}

	public void setFemalePercentage(double femalePercentage) {
		this.femalePercentage = femalePercentage;
	}

	@Override
	public String toString() {
		return "Department [name=" + name + ", male=" + male + ", female=" + female + ", malePercentage="
				+ malePercentage + ", femalePercentage=" + femalePercentage + "]";
	}

	
	

}
