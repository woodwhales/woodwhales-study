package cn.woodwhales.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.woodwhales.dto.demo1.CarDTO;
import cn.woodwhales.entity.demo1.Car;
import cn.woodwhales.entity.demo1.CarType;
import cn.woodwhales.mapping.demo1.CarMapping;

@SpringBootTest
class Demo1Tests {
	
	@Autowired
	private CarMapping carMapping;
	
	@Test
	void testCarMapping() {
		
		String make = "BMW";
		int numberOfSeats = 6;
		CarType carType = new CarType("simple");
		
		Date gmtCreated = new Date();
		Date gmtModified = new Date();
		
		Car car = new Car(make, numberOfSeats, carType, gmtCreated, gmtModified);
		
		CarDTO carDTO = carMapping.carToCarDTO(car);
		
		assertEquals(car.getMake(), carDTO.getMake());
		assertEquals(car.getNumberOfSeats(), carDTO.getSeatCount());
		assertEquals(car.getCarType().getType(), carDTO.getType());
		assertEquals(car.getGmtCreated(), carDTO.getGmtCreated());
		assertEquals(car.getGmtModified(), carDTO.getGmtModified());
		
	}

}
