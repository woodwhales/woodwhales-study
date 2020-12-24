package cn.woodwhales.mapstruct.param;

import cn.woodwhales.mapstruct.converter.Param;
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
