package cn.woodwhales.httpclient.mapstruct.vo;

import cn.woodwhales.httpclient.mapstruct.converter.Vo;
import lombok.Data;

import java.util.Date;

/**
 * @author woodwhales
 * @date 2020-12-24 20:51
 */
@Data
public class DemoDataVo implements Vo {

    private Integer id;

    private String name;

    private String nickname;

    private Date createTime;

    private Date updateTime;

}
