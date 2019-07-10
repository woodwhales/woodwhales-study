package org.woodwhales.spring.code4;

import java.util.List;

import org.woodwhales.spring.code2.Car;

import lombok.Data;

@Data
public class Boss {

	private String name;
	private List<Car> cars;
	
}
