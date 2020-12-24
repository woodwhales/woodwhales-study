package cn.woodwhales.mapstruct.mapping;

import cn.woodwhales.mapstruct.converter.MyConverter;
import cn.woodwhales.mapstruct.entity.DemoDataEntity;
import cn.woodwhales.mapstruct.param.DemoDataParam;
import org.mapstruct.Mapper;

/**
 * @author woodwhales
 * @date 2020-12-24 21:48
 */
@Mapper(componentModel = "spring")
public interface DemoDataEntityConverter extends MyConverter<DemoDataParam, DemoDataEntity> {
}
