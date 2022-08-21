package cn.woodwhales.httpclient.mapstruct.param;

import cn.woodwhales.httpclient.mapstruct.converter.Param;
import lombok.Data;

/**
 * @author woodwhales
 * @date 2020-12-24 21:45
 */
@Data
public class DemoDataParam implements Param {

    private String name;

    private String nickname;

}
