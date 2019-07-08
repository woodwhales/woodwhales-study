package org.woodwhale.annotation.code08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.woodwhale.annotation.code08.service.UserService;

import lombok.ToString;

@ToString
@Controller
public class UserController {
	
	@Autowired
	private UserService userServiceImpl1;

}
