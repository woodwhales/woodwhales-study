package org.woodwhale.annotation.code06;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("org.woodwhale.king.code06")
@Configuration
public class AppConfig6 {

	// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	@Bean(initMethod = "init", destroyMethod = "destory")
//	public Car car() {
//		return new Car();
//	}
//	
//	@Bean
//	public Tank tank() {
//		return new Tank();
//	}
	
	@Bean
	public Dog dog() {
		return new Dog();
	}
	
}
