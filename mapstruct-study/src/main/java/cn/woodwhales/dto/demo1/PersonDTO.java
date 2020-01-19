package cn.woodwhales.dto.demo1;

import java.util.Date;

import lombok.Data;

@Data
public class PersonDTO {
	
	private String code;
	
	private String name;
	
	private Byte status;
	
	private Date gmtCreated;
	
	private Date gmtModified;
}
