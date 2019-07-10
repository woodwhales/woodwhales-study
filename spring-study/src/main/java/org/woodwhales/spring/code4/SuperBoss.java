package org.woodwhales.spring.code4;

import java.util.Map;

import org.woodwhales.spring.code2.Car;

import lombok.Data;

@Data
public class SuperBoss {
	private String name;
	private Map<String, Car> cars;
}
