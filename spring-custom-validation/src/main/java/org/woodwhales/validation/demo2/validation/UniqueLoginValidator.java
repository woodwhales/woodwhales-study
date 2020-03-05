package org.woodwhales.validation.demo2.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.woodwhales.validation.demo2.repository.UserRepository;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

	private UserRepository userRepository;
	
	// 注意，在Spring4.3之后，构造器自动注入是不需要使用@Autowired注解。
	public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public boolean isValid(String login, ConstraintValidatorContext context) {
		return login != null && !userRepository.findByLogin(login).isPresent();
	}

}
