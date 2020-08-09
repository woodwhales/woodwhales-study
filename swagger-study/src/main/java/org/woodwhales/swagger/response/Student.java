package org.woodwhales.swagger.response;

import lombok.Data;

/**
 * @author woodwhales on 2020-08-07
 * @description
 */
@Data
public class Student extends CommonResult {

    private String stuName;

    private Integer stuAge;

    private String stuAddress;

}
