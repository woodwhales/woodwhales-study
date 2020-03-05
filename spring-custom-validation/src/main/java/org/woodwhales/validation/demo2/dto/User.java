package org.woodwhales.validation.demo2.dto;

import org.woodwhales.validation.demo2.validation.UniqueLogin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@UniqueLogin(message = "用户账号已存在")
	private String login;
	
	private String password;
	
}
