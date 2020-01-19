package cn.woodwhales;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.woodwhales.dto.CarDTO;
import cn.woodwhales.entity.Car;
import cn.woodwhales.entity.CarType;
import cn.woodwhales.mapper.CarMapping;

@SpringBootTest
class MapstructStudyApplicationTests {
	
	@Autowired
	private CarMapping carMapping;
	
	@Test
	void testCarMapper() {
		
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
