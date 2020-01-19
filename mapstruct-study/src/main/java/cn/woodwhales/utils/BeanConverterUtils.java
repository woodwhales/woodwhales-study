package cn.woodwhales.utils;

import cn.woodwhales.dto.CarDTO;
import cn.woodwhales.entity.Car;

public class BeanConverterUtils {
	
	private BeanConverterUtils() {}

	public static CarDTO carToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        
        carDTO.setMake(car.getMake());
        carDTO.setSeatCount(car.getNumberOfSeats());
        carDTO.setType(car.getCarType().getType());
        
        return carDTO;
    } 
}
