package org.woodwhale.annotation.code03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@ComponentScan(basePackages= {"org.woodwhale.king.code03"})
@Configuration
public class AppConfig3 {

	@Lazy
	@Bean
	public Student student() {
		System.out.println("drop student into Spring IOC ...");
        return new Student();
	}
}
