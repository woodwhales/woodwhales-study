package cn.woodwhales.entity.demo1;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

	// 生产商
	private String make;

	// 座位数量
	private int numberOfSeats;

	// 汽车类型
	private CarType carType;

	private Date gmtCreated;

	private Date gmtModified;

}