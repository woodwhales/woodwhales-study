package cn.woodwhales.dto.demo1;

import java.util.Date;

import lombok.Data;

@Data
public class CarDTO {
	
	// 生产商
	private String make;
	
	// 座位数量
	private int seatCount;
	
	// 类型
	private String type;
	
	private Date gmtCreated;
	
	private Date gmtModified;
	
}