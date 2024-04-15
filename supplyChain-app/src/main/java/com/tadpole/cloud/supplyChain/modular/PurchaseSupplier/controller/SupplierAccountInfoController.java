package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierAccountInfoParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierAccountInfoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 供应商-供应商信息 前端控制器
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@RestController
@Api(tags = "供应商-账号信息")
@ApiResource(name = "供应商-账号信息", path = "/supplierAccountInfo")
public class SupplierAccountInfoController {

    @Autowired
    private ISupplierAccountInfoService service;

    @PostResource(name = "供应商账号信息-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "供应商账号信息-列表查询")
    @BusinessLog(title = "供应商账号信息-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody SupplierAccountInfoParam param) {
        return ResponseData.success(service.listPage(param));
    }

    @PostResource(name = "供应商账号信息-变更", path = "/update")
    @ApiOperation(value = "供应商账号信息-变更")
    @BusinessLog(title = "供应商账号信息-变更",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(@RequestBody SupplierAccountInfoParam param) {
        service.edit(param);
        return ResponseData.success();
    }
}
