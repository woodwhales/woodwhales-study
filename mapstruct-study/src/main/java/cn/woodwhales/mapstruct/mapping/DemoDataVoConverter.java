package cn.woodwhales.mapstruct.mapping;

import cn.woodwhales.mapstruct.converter.MyConverter;
import cn.woodwhales.mapstruct.dto.DemoDataDto;
import cn.woodwhales.mapstruct.vo.DemoDataVo;
import org.mapstruct.Mapper;

/**
 * @author woodwhales
 * @date 2020-12-24 20:53
 */
@Mapper(componentModel = "spring")
public interface DemoDataVoConverter extends MyConverter<DemoDataDto, DemoDataVo> {
}
