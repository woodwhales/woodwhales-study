package org.woodwhale.annotation.code07;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Person {
	// 基本数值
	@Value("zhangsan")
	private String name;
	
	// 可以写SpEL； #{}
	@Value("#{20+1}")
	private String age;
	
	// 可以写${} 取出配置文件(properties)中的值（在运行环境变量里面的值）
	@Value("${person.nickName}")
	private String nickName;
}
