package org.woodwhale.annotation.code07;

import org.springframework.beans.factory.annotation.Value;

import lombok.ToString;

@ToString
public class Teacher {
	@Value("${teacher.name}")
	private String name;
	
	@Value("${teacher.age}")
	private String age;
}
