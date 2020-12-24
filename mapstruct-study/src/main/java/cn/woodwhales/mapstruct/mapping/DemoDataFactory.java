package cn.woodwhales.mapstruct.mapping;

import cn.woodwhales.mapstruct.converter.DtoConverter;
import cn.woodwhales.mapstruct.converter.EntityConverter;
import cn.woodwhales.mapstruct.converter.MyConverter;
import cn.woodwhales.mapstruct.converter.ParamConverter;
import cn.woodwhales.mapstruct.dto.DemoDataDto;
import cn.woodwhales.mapstruct.entity.DemoDataEntity;
import cn.woodwhales.mapstruct.param.DemoDataParam;
import cn.woodwhales.mapstruct.vo.DemoDataSimpleVo;
import cn.woodwhales.mapstruct.vo.DemoDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author woodwhales
 * @date 2020-12-24 21:02
 */
@Component
public class DemoDataFactory implements EntityConverter<DemoDataEntity, DemoDataDto>,
        DtoConverter<DemoDataDto, DemoDataVo, DemoDataSimpleVo>,
        ParamConverter<DemoDataParam, DemoDataEntity> {

    @Autowired
    private MyConverter<DemoDataEntity, DemoDataDto> converter1;

    @Autowired
    private MyConverter<DemoDataDto, DemoDataVo> converter2;

    @Autowired
    private MyConverter<DemoDataParam, DemoDataEntity> converter3;

    @Autowired
    private MyConverter<DemoDataDto, DemoDataSimpleVo> converter4;

    @Override
    public DemoDataDto convert(DemoDataEntity demoDataEntity) {
        return converter1.convert(demoDataEntity);
    }

    @Override
    public DemoDataVo convert(DemoDataDto demoDataDto) {
        return converter2.convert(demoDataDto);
    }

    @Override
    public DemoDataSimpleVo convertToSimpleVo(DemoDataDto demoDataDto) {
        return converter4.convert(demoDataDto);
    }

    @Override
    public DemoDataEntity convert(DemoDataParam param) {
        return converter3.convert(param);
    }
}
