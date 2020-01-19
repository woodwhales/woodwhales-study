package cn.woodwhales.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.woodwhales.dto.CarDTO;
import cn.woodwhales.entity.Car;
import cn.woodwhales.entity.CarType;
import cn.woodwhales.mapper.CarMapping;

@RestController("/test")
public class CarController {
	
	@Autowired
	private CarMapping carMapping;

	@ResponseBody
	@GetMapping("/car")
	public Object car() {
		String make = "BMW";
		int numberOfSeats = 6;
		CarType carType = new CarType("simple");
		
		Date gmtCreated = new Date();
		Date gmtModified = new Date();
		
		Car car = new Car(make, numberOfSeats, carType, gmtCreated, gmtModified);
		
		CarDTO carDTO = carMapping.carToCarDTO(car);
		
		return carDTO;
	}
}
