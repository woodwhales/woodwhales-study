package cn.woodwhales.mapstruct.entity;

import cn.woodwhales.mapstruct.converter.Entity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author woodwhales
 * @date 2020-12-24 20:49
 */
@Data
@Builder
public class DemoDataEntity implements Entity {

    private Integer id;

    private String name;

    private String nickname;

    private Date createTime;

    private Date updateTime;
}
