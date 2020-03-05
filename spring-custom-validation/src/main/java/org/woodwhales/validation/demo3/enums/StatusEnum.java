package org.woodwhales.validation.demo3.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    NO(1, "启用"),
    OFF(2, "未启用");
 
    private Integer code;
    private String msg;
 
}