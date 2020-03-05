package org.woodwhales.validation.demo3.dto;

import org.woodwhales.validation.demo3.enums.StatusEnum;
import org.woodwhales.validation.demo3.validation.EnumValidator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@EnumValidator(target = StatusEnum.class, message = "用户状态类型必须为：1-启用 或 2-未启用")
	private Integer status;
}
