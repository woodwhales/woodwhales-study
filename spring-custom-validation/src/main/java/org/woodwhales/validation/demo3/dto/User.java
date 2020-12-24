package org.woodwhales.validation.demo3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodwhales.validation.EnumValidator;
import org.woodwhales.validation.demo3.enums.StatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@EnumValidator(target = StatusEnum.class, methodName = "getCode", message = "用户状态类型必须为：1-启用 或 2-未启用")
	private Integer status;
}
