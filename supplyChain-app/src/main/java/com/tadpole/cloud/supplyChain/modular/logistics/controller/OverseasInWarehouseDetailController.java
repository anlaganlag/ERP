package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasInWarehouseDetailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  海外仓入库管理详情前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@RestController
@ApiResource(name = "海外仓入库管理详情前端控制器", path = "/overseasInWarehouseDetail")
@Api(tags = "海外仓入库管理详情前端控制器")
public class OverseasInWarehouseDetailController {

    @Autowired
    private IOverseasInWarehouseDetailService service;

}
