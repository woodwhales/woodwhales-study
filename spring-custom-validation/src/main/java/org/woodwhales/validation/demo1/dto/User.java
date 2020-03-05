package org.woodwhales.validation.demo1.dto;

import org.woodwhales.validation.demo1.validation.PasswordEqual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordEqual(message = "两次密码不相同")
public class User {

    private String password;

    private String confirmedPassword;
}