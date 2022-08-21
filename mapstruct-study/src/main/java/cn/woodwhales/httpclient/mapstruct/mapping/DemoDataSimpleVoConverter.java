package cn.woodwhales.httpclient.mapstruct.mapping;

import cn.woodwhales.httpclient.mapstruct.converter.MyConverter;
import cn.woodwhales.httpclient.mapstruct.vo.DemoDataSimpleVo;
import cn.woodwhales.httpclient.mapstruct.dto.DemoDataDto;
import org.mapstruct.Mapper;

/**
 * @author woodwhales
 * @date 2020-12-24 20:53
 */
@Mapper(componentModel = "spring")
public interface DemoDataSimpleVoConverter extends MyConverter<DemoDataDto, DemoDataSimpleVo> {
}
