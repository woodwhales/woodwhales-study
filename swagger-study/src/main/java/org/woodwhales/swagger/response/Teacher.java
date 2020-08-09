package org.woodwhales.swagger.response;

import lombok.Data;

/**
 * @author woodwhales on 2020-08-07
 * @description
 */
@Data
public class Teacher extends CommonResult {

    private String teacherName;

    private Integer teacherAge;

    private String teacherAddress;

}
