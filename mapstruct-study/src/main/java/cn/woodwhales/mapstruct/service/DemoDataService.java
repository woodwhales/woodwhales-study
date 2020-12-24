package cn.woodwhales.mapstruct.service;

import cn.woodwhales.mapstruct.dto.DemoDataDto;
import cn.woodwhales.mapstruct.entity.DemoDataEntity;
import cn.woodwhales.mapstruct.mapping.DemoDataFactory;
import cn.woodwhales.mapstruct.param.DemoDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author woodwhales
 * @date 2020-12-24 20:58
 */
@Service
public class DemoDataService {

    @Autowired
    private DemoDataFactory demoDataFactory;

    public DemoDataDto getData() {
        DemoDataEntity demoDataEntity = DemoDataEntity.builder()
                .id(1)
                .name("woodwhales")
                .nickname("木鲸鱼")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        return demoDataFactory.convert(demoDataEntity);
    }

    public DemoDataDto saveData(DemoDataParam param) {
        DemoDataEntity demoDataEntity = demoDataFactory.convert(param);
        return demoDataFactory.convert(demoDataEntity);
    }

}
