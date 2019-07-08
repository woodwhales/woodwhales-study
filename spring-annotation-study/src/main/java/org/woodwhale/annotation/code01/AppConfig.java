package org.woodwhale.annotation.code01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"org.woodwhale.king.code01"})
@Configuration
public class AppConfig {

	@Bean("person")
    public Person person01() {
        return new Person("king", 22);
    }
}
