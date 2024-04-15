package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* datarange主数据 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "前端开发绑定使用", path = "/dataBind")
@Api(tags = "前端开发绑定使用")
public class DatarangeController {

    @Autowired
    private IDatarangeService service;

}
