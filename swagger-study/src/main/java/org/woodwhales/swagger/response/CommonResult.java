package org.woodwhales.swagger.response;

import lombok.Data;

import java.util.Date;

/**
 * @author woodwhales on 2020-08-07
 * @description
 */
@Data
public class CommonResult {

    private String id;

    private Date createTime;

    private Date updateTime;

}
