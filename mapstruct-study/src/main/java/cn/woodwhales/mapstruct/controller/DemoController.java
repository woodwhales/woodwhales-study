package cn.woodwhales.mapstruct.controller;

import cn.woodwhales.mapstruct.dto.DemoDataDto;
import cn.woodwhales.mapstruct.mapping.DemoDataFactory;
import cn.woodwhales.mapstruct.param.DemoDataParam;
import cn.woodwhales.mapstruct.service.DemoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author woodwhales
 * @date 2020-12-24 20:47
 */
@RestController
public class DemoController {

    @Autowired
    private DemoDataService demoDataService;

    @Autowired
    private DemoDataFactory demoDataFactory;

    @GetMapping("/demo/get")
    public Object get() {
        DemoDataDto demoDataDto = demoDataService.getData();
        return demoDataFactory.convert(demoDataDto);
    }

    @PostMapping("/demo/save")
    public Object save(@RequestBody DemoDataParam param) {
        return demoDataService.saveData(param);
    }
}
