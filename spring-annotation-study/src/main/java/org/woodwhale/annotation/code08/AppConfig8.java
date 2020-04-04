package org.woodwhale.annotation.code08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.woodwhale.annotation.code08.controller.UserController;
import org.woodwhale.annotation.code08.entity.User;

@ComponentScan(basePackages = {"org.woodwhale.annotation.code08"})
@Configuration
public class AppConfig8 {
	
	@Bean
	public User user(@Autowired UserController userController) {
		System.out.println(userController.getClass());
		return new User();
	}
	
}
