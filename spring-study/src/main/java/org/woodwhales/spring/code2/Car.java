package org.woodwhales.spring.code2;

import lombok.ToString;

@ToString
public class Car {
	private String company;
	private String brand;

	private int maxSpeed;
	private float price;
	
	public Car(String company, String brand, float price) {
		super();
		this.company = company;
		this.brand = brand;
		this.price = price;
	}
	
	public Car(String brand, int maxSpeed) {
		this.brand = brand;
		this.maxSpeed = maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
}