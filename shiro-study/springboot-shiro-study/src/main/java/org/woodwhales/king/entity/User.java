package org.woodwhales.king.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class User implements Serializable {

	private static final long serialVersionUID = 8267662789640525038L;

	private Long id;
	private String userName;
	private String password;
	private String roles;

	/**
	 * 重写了 toString() 方法，目的就是为了能够 thymeleaf-shiro 插件正确显示唯一标识
	 * @return
	 */
	@Override
	public String toString() {
		return userName;
	}
}
