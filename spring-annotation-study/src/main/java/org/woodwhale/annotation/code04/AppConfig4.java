package org.woodwhale.annotation.code04;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig4 {

	@Conditional(WindowsCondition.class)
	@Bean("bill")
	public Boss boss1(){
		return new Boss("Bill Gates", 62);
	}
	
	@Conditional(LinuxCondition.class)
	@Bean("linus")
	public Boss boss2(){
		return new Boss("linus", 48);
	}
}
