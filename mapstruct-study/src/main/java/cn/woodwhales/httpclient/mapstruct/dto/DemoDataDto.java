package cn.woodwhales.httpclient.mapstruct.dto;

import cn.woodwhales.httpclient.mapstruct.converter.Dto;
import lombok.Data;

import java.util.Date;

/**
 * @author woodwhales
 * @date 2020-12-24 20:51
 */
@Data
public class DemoDataDto implements Dto {

    private Integer id;

    private String name;

    private String nickname;

    private Date createTime;

    private Date updateTime;
}
