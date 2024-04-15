package com.tadpole.cloud.product.modular.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.product.service.IRdManageUpRecordService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author S20210221
 * @since 2023-12-08
 */
@RestController
@Api(tags = "")
@ApiResource(name = "", path = "/rdManageUpRecord")
public class RdManageUpRecordController {

    @Autowired
    private IRdManageUpRecordService service;

}
