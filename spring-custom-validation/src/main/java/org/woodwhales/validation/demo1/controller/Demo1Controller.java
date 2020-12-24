package org.woodwhales.validation.demo1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.validation.demo1.dto.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参考：https://github.com/danielolszewski/blog/tree/master/spring-custom-validation
 *
 */
@Slf4j
@RestController
public class Demo1Controller {

	@PostMapping("/demo1/")
	public Object demo1(@Valid @RequestBody  User user, BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<>();
		if(bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				log.debug("errorMessage -> {}", objectError.getDefaultMessage());
			}
			map.put("code", false);
			return map;
		}
		
		map.put("code", true);
		return map;
	}
}
