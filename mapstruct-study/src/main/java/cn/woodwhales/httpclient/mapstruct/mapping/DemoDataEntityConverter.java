package cn.woodwhales.httpclient.mapstruct.mapping;

import cn.woodwhales.httpclient.mapstruct.converter.MyConverter;
import cn.woodwhales.httpclient.mapstruct.entity.DemoDataEntity;
import cn.woodwhales.httpclient.mapstruct.param.DemoDataParam;
import org.mapstruct.Mapper;

/**
 * @author woodwhales
 * @date 2020-12-24 21:48
 */
@Mapper(componentModel = "spring")
public interface DemoDataEntityConverter extends MyConverter<DemoDataParam, DemoDataEntity> {
}
