package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierLogParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 供应商-日志 前端控制器
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@RestController
@Api(tags = "供应商-日志")
@ApiResource(name = "供应商-日志", path = "/supplierLog")
public class SupplierLogController {

    @Autowired
    private ISupplierLogService service;

    @PostResource(name = "供应商日志-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "供应商日志-列表查询")
    @BusinessLog(title = "供应商日志-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody SupplierLogParam param) {
        return ResponseData.success(service.listPage(param));
    }
}
