package cn.woodwhales.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Person {

	private Long id;
	
	private String code;
	
	private String name;
	
	private Byte status;
	
	private Date gmtCreated;
	
	private Date gmtModified;
}
