package org.woodwhale.annotation.code07;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:/application.properties"})
@Configuration
public class AppConfig7 {

	@Bean
	public Person person() {
		return new Person();
	}
}
